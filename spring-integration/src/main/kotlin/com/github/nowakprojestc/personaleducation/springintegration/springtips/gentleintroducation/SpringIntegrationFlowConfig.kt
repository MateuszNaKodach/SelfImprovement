package com.github.nowakprojestc.personaleducation.springintegration.springtips.gentleintroducation

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ImageBanner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
import java.io.File

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
            @Value(value = "\${input-directory}:\${HOME}/Desktop/in") fileDirectory: File,
            environment: Environment,
            ftpSessionFactory: DefaultFtpSessionFactory
    ): IntegrationFlow {

        val fileStringGenericTransformer: GenericTransformer<File,String> = GenericTransformer<File, String> {source ->
            val imageBanner = ImageBanner(FileSystemResource(source))
            imageBanner.printBanner(environment, javaClass, )
        }

        return IntegrationFlows.from(
                Files.inboundAdapter(fileDirectory)
                        .autoCreateDirectory(true)
                        .preventDuplicates(true))
                .transform(File::class.java,fileStringGenericTransformer)
                .handle(
                        Ftp.outboundAdapter(ftpSessionFactory)
                                .fileNameGenerator { message -> (message.headers[FileHeaders.FILENAME] as String).split("\\.")[0] + ".txt" }
                ).get()
    }

}
