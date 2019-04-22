package com.github.nowakprojestc.personaleducation.springintegration.springtips.gentleintroducation

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ImageBanner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.core.io.FileSystemResource
import org.springframework.integration.amqp.dsl.Amqp
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.MessageChannels
import org.springframework.integration.file.FileHeaders
import org.springframework.integration.file.FileNameGenerator
import org.springframework.integration.file.dsl.Files
import org.springframework.integration.ftp.dsl.Ftp
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory
import org.springframework.integration.transformer.GenericTransformer
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.util.concurrent.TimeUnit

@Profile("gentle-introduction")
@Configuration
internal class SpringIntegrationFlowConfig {

    @Configuration
    class FtpConfig {

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
        fun ftp(ftpSessionFactory: DefaultFtpSessionFactory, asciiProcessors: PublishSubscribeChannel) = IntegrationFlows.from(asciiProcessors)
                .handle(
                        Ftp.outboundAdapter(ftpSessionFactory)
                                .remoteDirectory("")
                                .fileNameGenerator { message -> (message.headers[FileHeaders.FILENAME] as String).split(".")[0] + ".txt" }
                ).get()

    }

    @Configuration
    class AmqpConfig {

        private val ASCII = "ascii"

        @Bean
        fun exchange(): Exchange = ExchangeBuilder.directExchange(ASCII).durable(true).build()

        @Bean
        fun queue(): Queue = QueueBuilder.durable(ASCII).build()

        @Bean
        fun binding(): Binding = BindingBuilder.bind(queue())
                .to(exchange())
                .with(ASCII)
                .noargs()

        @Bean
        fun amqp(amqpTemplate: AmqpTemplate, asciiProcessors: PublishSubscribeChannel): IntegrationFlow = IntegrationFlows.from(asciiProcessors)
                .handle(
                        Amqp.outboundAdapter(amqpTemplate)
                                .exchangeName(ASCII)
                                .routingKey(ASCII)
                ).get()
    }


    @Bean
    fun files(
            @Value(value = "\${gentle-introduction.file-directory}") fileDirectory: File,
            environment: Environment
    ): IntegrationFlow {

        val fileStringGenericTransformer: GenericTransformer<File, Message<String>> = GenericTransformer<File, Message<String>> { source ->
            ByteArrayOutputStream().use { outputStream ->
                PrintStream(outputStream).use { printStream ->
                    val imageBanner = ImageBanner(FileSystemResource(source))
                    imageBanner.printBanner(environment, javaClass, printStream)
                    MessageBuilder.withPayload(String(outputStream.toByteArray()))
                            .setHeader(FileHeaders.FILENAME, source.absoluteFile.name)
                            .build()
                }
            }
        }

        return IntegrationFlows.from(
                Files.inboundAdapter(fileDirectory)
                        .autoCreateDirectory(true)
                        .preventDuplicates(true)
                        .patternFilter("*.jpg")) { poller -> poller.poller { metadata -> metadata.fixedRate(1, TimeUnit.SECONDS) } }
                .transform(File::class.java, fileStringGenericTransformer)
                .channel(this.asciiProcessors())
                .get()
    }

    @Bean
    fun asciiProcessors(): PublishSubscribeChannel = MessageChannels.publishSubscribe().get();

}
