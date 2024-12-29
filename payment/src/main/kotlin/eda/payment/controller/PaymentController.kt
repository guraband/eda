package eda.payment.controller

import eda.payment.dto.PaymentMethodRequest
import eda.payment.dto.PaymentMethodResponse
import eda.payment.dto.PaymentRequest
import eda.payment.dto.PaymentResponse
import eda.payment.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/payment")
class PaymentController(
    private val paymentService: PaymentService,
) {
    @PostMapping("/method")
    fun registerPaymentMethod(
        @RequestBody request: PaymentMethodRequest,
    ): ResponseEntity<Unit> {
        paymentService.registerPaymentMethod(request)
        return ResponseEntity.ok(Unit)
    }

    @PostMapping("/process")
    fun processPayment(
        @RequestBody request: PaymentRequest,
    ): ResponseEntity<Unit> {
        paymentService.processPayment(request)
        return ResponseEntity.ok(Unit)
    }

    @GetMapping("/user/{userId}/first-method")
    fun getPaymentMethod(
        @PathVariable userId: Long,
    ): ResponseEntity<PaymentMethodResponse> {
        return ResponseEntity.ok(
            paymentService.getPaymentMethod(userId)
        )
    }

    @GetMapping("/{paymentId}")
    fun getPayment(
        @PathVariable paymentId: Long,
    ): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(
            paymentService.getPayment(paymentId)
        )
    }
}