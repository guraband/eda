package eda.delivery.service

import eda.delivery.dg.DeliveryAdapter
import eda.delivery.dto.AddressRequest
import eda.delivery.dto.AddressResponse
import eda.delivery.dto.DeliveryRequest
import eda.delivery.dto.DeliveryResponse
import eda.delivery.entity.Address
import eda.delivery.entity.Delivery
import eda.delivery.enums.DeliveryStatus
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
        return AddressResponse(address)
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
        return DeliveryResponse(delivery)
    }

    fun getDelivery(deliveryId: Long): DeliveryResponse {
        val delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow { throw IllegalArgumentException("Delivery not found") }
        return DeliveryResponse(delivery)
    }

    fun getAddress(addressId: Long): AddressResponse {
        val address = addressRepository.findById(addressId)
            .orElseThrow { throw IllegalArgumentException("Address not found") }
        return AddressResponse(address)
    }

    fun getUserAddress(userId: Long): AddressResponse {
        return addressRepository.findAllByUserId(userId)
            .firstOrNull()
            ?.let {
                AddressResponse(it)
            } ?: throw IllegalArgumentException("Address not found")
    }
}