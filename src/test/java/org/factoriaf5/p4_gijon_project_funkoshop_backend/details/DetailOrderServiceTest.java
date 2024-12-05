package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderDto;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DetailOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private DetailOrderService detailOrderService;

    private String mockAuthorizationHeader;
    private User mockUser;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        // Configuración de mocks
        mockAuthorizationHeader = "Bearer mockToken";
        mockUser = mock(User.class);
        mockOrder = mock(Order.class);
    }

    @Test
    void testListByMonth_AdminAccess() {
        // Configurar mocks para acceso de admin
        when(jwtUtil.extractEmailFromToken("mockToken")).thenReturn("admin@example.com");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(mockUser));
        when(mockUser.getRoles()).thenReturn("admin");
        
        // Crear lista de órdenes simuladas
        List<Order> mockOrders = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderDate(LocalDate.now().minusMonths(1).withDayOfMonth(15));
        mockOrders.add(order1);
        
        when(orderRepository.findAll()).thenReturn(mockOrders);

        // Ejecutar método
        List<OrderDto> result = detailOrderService.listByMonth(mockAuthorizationHeader);

        // Verificar
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(orderRepository).findAll();
    }

    @Test
    void testRetrieveBestSellers_AdminAccess() {
        // Configurar mocks para acceso de admin
        when(jwtUtil.extractEmailFromToken("mockToken")).thenReturn("admin@example.com");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(mockUser));
        when(mockUser.getRoles()).thenReturn("admin");
        
        // Crear lista de órdenes simuladas
        List<Order> mockOrders = new ArrayList<>();
        
        // Ejecutar método
        List<ProductDto> result = detailOrderService.retrieveBestSellers(mockAuthorizationHeader);

        // Verificar
        assertNotNull(result);
    }

    @Test
    void testGeneratePDFAllOrders_ValidAccess() {
        // Configurar mocks para generación de PDF
        when(jwtUtil.generateEmailFromToken("mockToken")).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(mockUser);
        when(mockUser.getToken()).thenReturn("mockToken");
        
        // Crear lista de órdenes simuladas
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(mockOrder);
        when(orderRepository.findAll()).thenReturn(mockOrders);

        // Ejecutar método
        byte[] pdfBytes = detailOrderService.generatePDFAllOrders(mockAuthorizationHeader, null);

        // Verificar
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

    @Test
    void testCalculateQuantity_ThrowsSecurityException() {
        // Configurar mocks para acceso no autorizado
        when(jwtUtil.extractEmailFromToken("mockToken")).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
        when(mockUser.getRoles()).thenReturn("user");

        // Verificar que se lanza SecurityException para usuario sin permisos de admin
        assertThrows(SecurityException.class, () -> {
            detailOrderService.calculateQuantity(mockAuthorizationHeader, mockOrder, null);
        });
    }

    // Test adicional para manejo de token inválido
    @Test
    void testMethodsWithInvalidToken() {
        // Verificar manejo de token nulo
        assertThrows(IllegalArgumentException.class, () -> {
            detailOrderService.generatePDFAllOrders(null, null);
        });

        // Verificar manejo de token sin formato correcto
        assertThrows(IllegalArgumentException.class, () -> {
            detailOrderService.generatePDFAllOrders("InvalidToken", null);
        });
    }
}