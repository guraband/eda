package eda.order.service;

import eda.common.dto.AddressResponse;
import eda.common.dto.PaymentMethodResponse;
import eda.common.dto.ProductResponse;
import eda.common.dto.message.Message;
import eda.common.enums.MessageTopic;
import eda.common.enums.OrderStatus;
import eda.common.enums.PaymentMethodType;
import eda.order.dto.FinishOrderRequest;
import eda.order.dto.StartOrderRequest;
import eda.order.entity.ProductOrder;
import eda.order.feign.CatalogClient;
import eda.order.feign.DeliveryClient;
import eda.order.feign.PaymentClient;
import eda.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({OrderService.class})
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @MockitoSpyBean
    OrderRepository orderRepository;

    @MockitoBean
    PaymentClient paymentClient;

    @MockitoBean
    DeliveryClient deliveryClient;

    @MockitoBean
    CatalogClient catalogClient;

    @MockitoBean
    KafkaTemplate<String, Message> kafkaTemplate;

    @Test
    void startOrderTest() {
        // given
        var paymentMethodResponse = new PaymentMethodResponse(1L, PaymentMethodType.CREDIT_CARD, "123456");
        var deliveryAddressResponse = new AddressResponse(1L, 1L, "서울 강남구", "address1");

        when(paymentClient.getPaymentMethod(1L)).thenReturn(paymentMethodResponse);
        when(deliveryClient.getAddressByUser(1L)).thenReturn(deliveryAddressResponse);

        // when
        var startOrderResponse = orderService.startOrder(
                new StartOrderRequest(1L, 1L, 2)
        );

        // then
        assertNotNull(startOrderResponse);
        assertEquals(paymentMethodResponse, startOrderResponse.getPaymentMethod());
        assertEquals(deliveryAddressResponse, startOrderResponse.getAddress());

        var order = orderRepository.findById(startOrderResponse.getOrderId()).orElseThrow();
        assertEquals(OrderStatus.INITIATED, order.getOrderStatus());
    }

    @Test
    void finishOrderTest() {
        // given
        var orderId = 1L;
        var userId = 2L;
        var productId = 3L;
        var addressId = 5L;
        var deliveryId = 6L;
        var paymentId = 7L;
        var deliveryAddress = "서울 강남구";

        var orderStarted = new ProductOrder(
                null,
                userId,
                productId,
                1,
                OrderStatus.INITIATED,
                paymentId,
                deliveryId,
                deliveryAddress
        );
        orderRepository.save(orderStarted);

        var productResponse = new ProductResponse(
                productId,
                111L,
                "맥북",
                "",
                1_500_000,
                10,
                List.of("애플", "맥북")
        );

        var addressResponse = new AddressResponse(
                addressId,
                userId,
                deliveryAddress,
                "address1");

        when(catalogClient.getProductById(productId)).thenReturn(productResponse);
        when(deliveryClient.getAddress(addressId)).thenReturn(addressResponse);

        // when
        var finishOrderResponse = orderService.finishOrder(
                new FinishOrderRequest(
                        orderId,
                        6L,
                        addressId
                )
        );

        // then
        assertNotNull(finishOrderResponse);
        assertEquals(finishOrderResponse.getOrderStatus(), OrderStatus.PAYMENT_REQUESTED);
        assertEquals(finishOrderResponse.getDeliveryAddress(), deliveryAddress);
        verify(kafkaTemplate, times(1))
                .send(eq(MessageTopic.PAYMENT_REQUEST.getTopicName()),
                        any(Message.class));
    }
}
