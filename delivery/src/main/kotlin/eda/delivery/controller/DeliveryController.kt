package eda.delivery.controller

import eda.delivery.dto.AddressRequest
import eda.delivery.dto.AddressResponse
import eda.delivery.dto.DeliveryRequest
import eda.delivery.dto.DeliveryResponse
import eda.delivery.service.DeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService,
) {
    @PostMapping("/address")
    fun registerAddress(
        @RequestBody request: AddressRequest,
    ): ResponseEntity<AddressResponse> {
        return ResponseEntity.ok(
            deliveryService.addAddress(request)
        )
    }

    @PostMapping("/process")
    fun processDelivery(
        @RequestBody request: DeliveryRequest,
    ): ResponseEntity<DeliveryResponse> {
        return ResponseEntity.ok(
            deliveryService.process(request)
        )
    }

    @GetMapping("/{deliveryId}")
    fun getDelivery(
        @PathVariable deliveryId: Long,
    ): ResponseEntity<DeliveryResponse> {
        return ResponseEntity.ok(
            deliveryService.getDelivery(deliveryId)
        )
    }

    @GetMapping("/address/{addressId}")
    fun getAddress(
        @PathVariable addressId: Long,
    ): ResponseEntity<AddressResponse> {
        return ResponseEntity.ok(
            deliveryService.getAddress(addressId)
        )
    }

    @GetMapping("/user/{userId}/address")
    fun getAddressByUser(
        @PathVariable userId: Long,
    ): ResponseEntity<AddressResponse> {
        return ResponseEntity.ok(
            deliveryService.getUserAddress(userId)
        )
    }
}