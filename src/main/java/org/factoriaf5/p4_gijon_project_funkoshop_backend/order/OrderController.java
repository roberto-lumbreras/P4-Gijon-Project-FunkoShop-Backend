package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.List;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.P4GijonProjectFunkoshopBackendApplication.order.OrderDTO;
import org.springframework.boot.info.SslInfo.CertificateValidityInfo.Status;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1") // ENDPOINT VISTO EN EL FRONTEND DEL FUNKO
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrderDTO orderDTO) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            OrderDTO createdOrder = orderService.createOrder(authorizationHeader, orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/user")
    public ResponseEntity<List<OrderDto>> retrieveAllOrdersByUser(
            @RequestHeader("Authorization") String authorizationHeader, @RequestBody OrderDto orderDto) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<OrderDto> orderList = orderService.getOrdersByUser(authorizationHeader, orderDto);
            return ResponseEntity.ok(orderList);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<OrderDto> retrieveAllOrdersByAdmin(@RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrderDto orderDto) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<OrderDto> orderList = orderService.getAllOrders(authorizationHeader, orderDto);
            return ResponseEntity.ok(orderList);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long orderId, @RequestParam Status status) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            OrderDTO updatedOrder = orderService.updateOrderStatus(authorizationHeader, orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/pdf/{orderId}")
    public ResponseEntity<byte[]> generateOrderPDF(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long orderId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            byte[] pdfData = orderService.generateOrderPDF(authorizationHeader, orderId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("order_" + orderId + ".pdf").build());
            return ResponseEntity.ok().headers(headers).body(pdfData);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/status/{orderId}")
    public ResponseEntity<Status> retreiveOrderStatus(@RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Status status) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Status status = orderService.getStatus(authorizationHeader, status);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/order/payment/{orderId}")
    public ResponseEntity<OrderDto> selectPaymentMethod(@RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrderDto payment) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            OrderDto result = orderService.selectPayment(authorizationHeader, payment);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
