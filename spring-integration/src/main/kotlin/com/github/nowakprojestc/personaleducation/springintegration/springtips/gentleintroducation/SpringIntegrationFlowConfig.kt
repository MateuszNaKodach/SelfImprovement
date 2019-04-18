package com.github.nowakprojestc.personaleducation.springintegration.springtips.gentleintroducation

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ImageBanner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.core.io.FileSystemResource
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.file.FileHeaders
import org.springframework.integration.file.FileNameGenerator
import org.springframework.integration.file.dsl.Files
import org.springframework.integration.ftp.dsl.Ftp
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory
import org.springframework.integration.transformer.GenericTransformer
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

@Profile("gentle-introduction")
@Configuration
internal class SpringIntegrationFlowConfig {

    @Bean
    fun ftpFileSessionFactory(
            @Value("\${ftp.port:21}") port: Int,
            @Value("\${ftp.username:springboot}") username: String,
            @Value("\${ftp.password:springboot}") password: String
    ): DefaultFtpSessionFactory = DefaultFtpSessionFactory()
            .apply {
                setPort(port)
                setUsername(username)
                setPassword(password)
            }

    @Bean
    fun files(
            @Value(value = "gentle-introduction.file-directory") fileDirectory: File,
            environment: Environment,
            ftpSessionFactory: DefaultFtpSessionFactory
    ): IntegrationFlow {

        val fileStringGenericTransformer: GenericTransformer<File, Message<String>> = GenericTransformer<File, Message<String>> { source ->
            ByteArrayOutputStream().use { outputStream ->
                PrintStream(outputStream).use { printStream ->
                    val imageBanner = ImageBanner(FileSystemResource(source))
                    imageBanner.printBanner(environment, javaClass, printStream)
                    MessageBuilder.withPayload(outputStream.toByteArray().toString())
                            .setHeader(FileHeaders.FILENAME, source.absoluteFile.name)
                            .build()
                }
            }
        }

        return IntegrationFlows.from(
                Files.inboundAdapter(fileDirectory)
                        .autoCreateDirectory(true)
                        .preventDuplicates(true)
                        .patternFilter("*.jpg")
                )
                .transform(File::class.java, fileStringGenericTransformer)
                .handle(
                        Ftp.outboundAdapter(ftpSessionFactory)
                                .fileNameGenerator { message -> (message.headers[FileHeaders.FILENAME] as String).split("\\.")[0] + ".txt" }
                ).get()
    }

}
