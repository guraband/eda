package eda.delivery.service

import eda.common.dto.AddressRequest
import eda.common.dto.AddressResponse
import eda.common.dto.DeliveryRequest
import eda.common.dto.DeliveryResponse
import eda.common.enums.DeliveryStatus
import eda.delivery.dg.DeliveryAdapter
import eda.delivery.entity.Address
import eda.delivery.entity.Delivery
import eda.delivery.repository.AddressRepository
import eda.delivery.repository.DeliveryRepository
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    private val deliveryRepository: DeliveryRepository,
    private val addressRepository: AddressRepository,
    private val deliveryAdapter: DeliveryAdapter,
) {
    fun addAddress(request: AddressRequest): AddressResponse {
        val address = Address(
            userId = request.userId,
            address = request.address,
            alias = request.alias,
        )
        addressRepository.save(address)
        return address.toResponseDto()
    }

    fun process(request: DeliveryRequest): DeliveryResponse {
        val address = addressRepository.findById(request.addressId)
            .orElseThrow { throw IllegalArgumentException("Address not found") }

        val referenceCode = deliveryAdapter.processDelivery(
            address = address.address,
            productName = request.productName,
            productCount = request.productCount,
        )

        val delivery = Delivery(
            orderId = request.orderId,
            productName = request.productName,
            productCount = request.productCount,
            addressId = request.addressId,
            deliveryStatus = DeliveryStatus.REQUESTED,
            referenceCode = referenceCode,
        )
        deliveryRepository.save(delivery)
        return delivery.toResponseDto()
    }

    fun getDelivery(deliveryId: Long): DeliveryResponse {
        val delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow { throw IllegalArgumentException("Delivery not found") }
        return delivery.toResponseDto()
    }

    fun getAddress(addressId: Long): AddressResponse {
        val address = addressRepository.findById(addressId)
            .orElseThrow { throw IllegalArgumentException("Address not found") }
        return address.toResponseDto()
    }

    fun getUserAddress(userId: Long): AddressResponse {
        return addressRepository.findAllByUserId(userId)
            .firstOrNull()
            ?.toResponseDto()
            ?: throw IllegalArgumentException("Address not found")
    }
}