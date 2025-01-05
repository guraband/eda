package eda.order.controller

import eda.order.dto.*
import eda.order.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/order")
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/start-order")
    fun startOrder(
        @RequestBody request: StartOrderRequest,
    ): ResponseEntity<StartOrderResponse> {
        return ResponseEntity.ok(
            orderService.startOrder(request)
        )
    }

    @PostMapping("/finish-order")
    fun finishOrder(
        @RequestBody request: FinishOrderRequest,
    ): ResponseEntity<FinishOrderResponse> {
        return ResponseEntity.ok(
            orderService.finishOrder(request)
        )
    }

    @GetMapping("/user/{userId}/orders")
    fun getUserOrders(
        @PathVariable userId: Long,
    ): ResponseEntity<List<FinishOrderResponse>> {
        return ResponseEntity.ok(
            orderService.getUserOrders(userId)
        )
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable orderId: Long,
    ): ResponseEntity<ProductOrderDetailDto> {
        return ResponseEntity.ok(
            orderService.getOrderDetail(orderId)
        )
    }
}