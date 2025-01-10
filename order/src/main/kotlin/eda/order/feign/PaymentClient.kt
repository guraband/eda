package eda.order.feign

import eda.common.dto.PaymentMethodResponse
import eda.common.dto.PaymentRequest
import eda.common.dto.PaymentResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "paymentClient", url = "http://payment-service/v1/payment")
interface PaymentClient {
    @GetMapping("/user/{userId}/first-method")
    fun getPaymentMethod(
        @PathVariable userId: Long,
    ): PaymentMethodResponse

    @PostMapping("/process")
    fun processPayment(
        @RequestBody request: PaymentRequest,
    ) : PaymentResponse

    @GetMapping("/{paymentId}")
    fun getPayment(
        @PathVariable paymentId: Long,
    ): PaymentResponse
}