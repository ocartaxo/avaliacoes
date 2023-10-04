package br.com.kofood.avaliacoes.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class PaymentsResponse(
    val id: Long,
    @JsonProperty("valor")
    val value: BigDecimal,
    @JsonProperty("nome")
    val name: String,
    @JsonProperty("numero")
    val number: String,
    @JsonProperty("data_expiracao")
    val expirationDate: String,
    @JsonProperty("codigo")
    val code: String,
    @JsonProperty("pedido_id")
    val orderId: Long,
    @JsonProperty("metodo_pagamento_id")
    val paymentsMethodId: Long,
    val status: PaymentStatus
)
