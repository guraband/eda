package eda.order.service

import eda.common.dto.AddressResponse
import eda.common.dto.PaymentMethodResponse
import eda.common.dto.ProductResponse
import eda.common.dto.message.Message
import eda.common.enums.OrderStatus
import eda.common.enums.PaymentMethodType
import eda.order.dto.StartOrderRequest
import eda.order.feign.CatalogClient
import eda.order.feign.DeliveryClient
import eda.order.feign.PaymentClient
import eda.order.repository.OrderRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import jakarta.annotation.PostConstruct
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.kafka.core.KafkaTemplate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
@DataJpaTest
class OrderServiceKtTest {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    private val paymentClient = mockk<PaymentClient>()
    private val deliveryClient = mockk<DeliveryClient>()
    private val catalogClient = mockk<CatalogClient>()
    private val kafkaTemplate = mockk<KafkaTemplate<String, Message>>()

    private lateinit var orderService: OrderService

    @PostConstruct
    fun setup() {
        orderService = OrderService(
            orderRepository,
            paymentClient,
            deliveryClient,
            catalogClient,
            kafkaTemplate,
        )
    }

    @Test
    fun startOrderTest() {
        // given
        val userId = 1L
        val productId = 2L
        val paymentMethodResponse = PaymentMethodResponse(1L, PaymentMethodType.CREDIT_CARD, "123456")
        val deliveryAddressResponse = AddressResponse(1L, 1L, "서울 강남구", "address1")
        val productResponse = ProductResponse(
            productId,
            111L,
            "맥북",
            "",
            1500000,
            10, listOf("애플", "맥북")
        )

        every { catalogClient.getProductById(productId) } returns productResponse
        every { paymentClient.getPaymentMethod(userId) } returns paymentMethodResponse
        every { deliveryClient.getAddressByUser(userId) } returns deliveryAddressResponse

        // when
        val startOrderResponse = orderService.startOrder(
            StartOrderRequest(userId, productId, 2)
        )

        // then
        assertNotNull(startOrderResponse)
        assertEquals(paymentMethodResponse, startOrderResponse.paymentMethod)
        assertEquals(deliveryAddressResponse, startOrderResponse.address)

        val order = orderRepository.findById(startOrderResponse.orderId).get()
        assertEquals(OrderStatus.INITIATED, order.orderStatus)
    }
}