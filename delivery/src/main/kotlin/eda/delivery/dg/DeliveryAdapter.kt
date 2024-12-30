package eda.delivery.dg

interface DeliveryAdapter {
    fun processDelivery(
        productName: String,
        productCount: Int,
        address: String,
    ): Long
}