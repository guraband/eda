package eda.order.feign

import eda.common.dto.AddressResponse
import eda.common.dto.DeliveryRequest
import eda.common.dto.DeliveryResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "deliveryClient", url = "http://delivery-service/v1/delivery")
interface DeliveryClient {
    @GetMapping("/user/{userId}/address")
    fun getAddressByUser(
        @PathVariable userId: Long,
    ): AddressResponse

    @GetMapping("/address/{addressId}")
    fun getAddress(
        @PathVariable addressId: Long,
    ): AddressResponse

    @PostMapping("/process")
    fun processDelivery(
        @RequestBody request: DeliveryRequest,
    ): DeliveryResponse

    @GetMapping("/{deliveryId}")
    fun getDelivery(
        @PathVariable deliveryId: Long,
    ): DeliveryResponse
}