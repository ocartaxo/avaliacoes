package br.com.kofood.avaliacoes.amqp

import br.com.kofood.avaliacoes.amqp.ReviewAQMPConfiguration.Companion.PAYMENTS_REVIEW_DETAILS_QUEUE
import br.com.kofood.avaliacoes.dto.PaymentsResponse
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ReviewListener {

    @RabbitListener(queues = [PAYMENTS_REVIEW_DETAILS_QUEUE])
    fun getMessage(@Payload payment: PaymentsResponse){
        val message = """
            Necessário criar registro de avaliação para o pedido: ${payment.orderId}
            Id do pagamento: ${payment.id}
            Nome do cliente: ${payment.name}
            Valor: R$${payment.value}
            Status: ${payment.status}
        """.trimIndent()

        print(message)
    }
}