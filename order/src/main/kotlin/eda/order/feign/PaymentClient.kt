package eda.order.feign

import eda.order.dto.PaymentMethodResponse
import eda.order.dto.PaymentRequest
import eda.order.dto.PaymentResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "paymentClient", url = "http://localhost:8082/v1/payment")
interface PaymentClient {
    @GetMapping("/user/{userId}/first-method")
    fun getPaymentMethod(
        @PathVariable userId: Long,
    ): PaymentMethodResponse

    @PostMapping("/process")
    fun processPayment(
        @RequestBody request: PaymentRequest,
    )

    @GetMapping("/{paymentId}")
    fun getPayment(
        @PathVariable paymentId: Long,
    ): PaymentResponse
}