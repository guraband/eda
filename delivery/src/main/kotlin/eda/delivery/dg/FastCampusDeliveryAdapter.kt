package eda.delivery.dg

import org.springframework.stereotype.Component

@Component
class FastCampusDeliveryAdapter : DeliveryAdapter {
    override fun processDelivery(productName: String, productCount: Int, address: String): Long {
        println("FastCampusDeliveryAdapter.processDelivery")
        return Math.round(Math.random() * 1_000_000L)
    }
}