package eda.order.service;

import eda.common.dto.AddressResponse;
import eda.common.dto.PaymentMethodResponse;
import eda.common.dto.message.Message;
import eda.common.enums.OrderStatus;
import eda.common.enums.PaymentMethodType;
import eda.order.dto.StartOrderRequest;
import eda.order.feign.CatalogClient;
import eda.order.feign.DeliveryClient;
import eda.order.feign.PaymentClient;
import eda.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(OrderService.class)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @SpyBean
    OrderRepository orderRepository;

    @MockBean
    PaymentClient paymentClient;

    @MockBean
    DeliveryClient deliveryClient;

    @MockBean
    CatalogClient catalogClient;

    @MockBean
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
}
