package br.com.kofood.avaliacoes.amqp

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReviewAQMPConfiguration {

    @Bean
    fun messageConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(
        cf: ConnectionFactory,
        mc: Jackson2JsonMessageConverter
    ): RabbitTemplate {

        val rabbitTemplate = RabbitTemplate(cf)
        rabbitTemplate.messageConverter = mc

        return rabbitTemplate

    }

    @Bean
    fun reviewDetailsQueue(): Queue = QueueBuilder
        .nonDurable(PAYMENTS_REVIEW_DETAILS_QUEUE)
        .deadLetterExchange(PAYMENTS_DLX)
        .build()

    @Bean
    fun reviewsDetailsDQL(): Queue = QueueBuilder
        .nonDurable(PAYMENTS_REVIEW_DETAILS_DQL)
        .build()


    @Bean
    fun fanoutExchange(): FanoutExchange = ExchangeBuilder
        .fanoutExchange(PAYMENTS_EXCHANGE)
        .build()

    @Bean
    fun deadLetterExchange(): FanoutExchange = ExchangeBuilder
        .fanoutExchange(PAYMENTS_DLX)
        .build()

    @Bean
    fun bindPaymentsOrder(): Binding = BindingBuilder
        .bind(reviewDetailsQueue())
        .to(fanoutExchange())


    @Bean
    fun bindDLXPaymentsOrder(): Binding = BindingBuilder
        .bind(reviewsDetailsDQL())
        .to(deadLetterExchange())


    @Bean
    fun rabbitAdmin(cf: ConnectionFactory) = RabbitAdmin(cf)

    @Bean
    fun startRabbitAdmin(rabbitAdmin: RabbitAdmin) =
        ApplicationListener<ApplicationReadyEvent> { rabbitAdmin.initialize() }

    companion object {
        const val PAYMENTS_REVIEW_DETAILS_QUEUE = "payments.review-details"
        const val PAYMENTS_REVIEW_DETAILS_DQL = "payments.review-details-dql"

        const val PAYMENTS_EXCHANGE = "payments.exchange"
        const val PAYMENTS_DLX = "payments.dlx"
    }
}