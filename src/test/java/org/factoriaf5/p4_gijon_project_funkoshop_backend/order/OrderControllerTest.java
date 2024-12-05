/* 


TEST COMENTADO : AUN NO SE HAN REALIZADO LAS PRUEBAS CORRESPONDIENTES , ESTE TEST ACTUALMENTE SOLO FUNCIONARIA COMO GUIA
PARA EL FUTURO DESARROLLO DES TEST


package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.info.SslInfo.CertificateValidityInfo.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private static final String VALID_AUTH_HEADER = "Bearer validToken";
    private static final String INVALID_AUTH_HEADER = "Invalid";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ValidRequest_ReturnsOk() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        doNothing().when(orderService).createOrder(VALID_AUTH_HEADER, orderDto);

        // Act
        ResponseEntity<Map<String, String>> response = orderController.createOrder(VALID_AUTH_HEADER, orderDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message : "));
        verify(orderService).createOrder(VALID_AUTH_HEADER, orderDto);
    }

    @Test
    void createOrder_InvalidAuthHeader_ReturnsUnauthorized() {
        // Arrange
        OrderDto orderDto = new OrderDto();

        // Act
        ResponseEntity<Map<String, String>> response = orderController.createOrder(INVALID_AUTH_HEADER, orderDto);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void createOrder_ServiceThrowsException_ReturnsBadRequest() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        doThrow(new IllegalArgumentException()).when(orderService).createOrder(VALID_AUTH_HEADER, orderDto);

        // Act
        ResponseEntity<Map<String, String>> response = orderController.createOrder(VALID_AUTH_HEADER, orderDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void listOrdersByUser_ValidRequest_ReturnsOrderList() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        List<OrderDto> expectedOrders = Arrays.asList(new OrderDto(), new OrderDto());
        when(orderService.ListOrdersByUser(VALID_AUTH_HEADER, orderDto)).thenReturn(expectedOrders);

        // Act
        ResponseEntity<List<OrderDto>> response = orderController.ListOrdersByUser(VALID_AUTH_HEADER, orderDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrders, response.getBody());
        verify(orderService).ListOrdersByUser(VALID_AUTH_HEADER, orderDto);
    }

    @Test
    void getAllOrders_ValidRequest_ReturnsAllOrders() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        List<OrderDto> expectedOrders = Arrays.asList(new OrderDto(), new OrderDto());
        when(orderService.getAllOrders(VALID_AUTH_HEADER, orderDto)).thenReturn(expectedOrders);

        // Act
        ResponseEntity<List<OrderDto>> response = orderController.getAllOrders(VALID_AUTH_HEADER, orderDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrders, response.getBody());
        verify(orderService).getAllOrders(VALID_AUTH_HEADER, orderDto);
    }

    @Test
    void updateStatus_ValidRequest_ReturnsOk() {
        // Arrange
        Long orderId = 1L;
        Status status = Status.PROCESSING;
        doNothing().when(orderService).updateStatus(VALID_AUTH_HEADER, orderId, status);

        // Act
        ResponseEntity<Map<String, String>> response = orderController.updateStatus(VALID_AUTH_HEADER, orderId, status);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message"));
        verify(orderService).updateStatus(VALID_AUTH_HEADER, orderId, status);
    }

    @Test
    void generateOrderPDF_ValidRequest_ReturnsPDF() {
        // Arrange
        Long orderId = 1L;
        byte[] expectedPdfData = "PDF Content".getBytes();
        when(orderService.generateOrderPDF(VALID_AUTH_HEADER, orderId)).thenReturn(expectedPdfData);

        // Act
        ResponseEntity<byte[]> response = orderController.generateOrderPDF(VALID_AUTH_HEADER, orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(expectedPdfData, response.getBody());
        assertNotNull(response.getHeaders().getContentType());
        assertNotNull(response.getHeaders().getContentDisposition());
        verify(orderService).generateOrderPDF(VALID_AUTH_HEADER, orderId);
    }

    @Test
    void getStatus_ValidRequest_ReturnsStatus() {
        // Arrange
        Status status = Status.ACTIVE;
        when(orderService.getStatus(VALID_AUTH_HEADER, status)).thenReturn(status);

        // Act
        ResponseEntity<Status> response = orderController.getStatus(VALID_AUTH_HEADER, status);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());
        verify(orderService).getStatus(VALID_AUTH_HEADER, status);
    }
} */