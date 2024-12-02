package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

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
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/user")
    public ResponseEntity<OrderDto> retrieveAllOrdersByUser(@RequestBody OrderDto order) {
        try {
            OrderDto orderDto = orderService.retreiveAllOrderByUser(userId);
            // return new ResponseEntity<OrderDto>(orderDto).build();
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<OrderDto> retrieveAllOrdersByAdmin(@RequestBody OrderDto order) {
        try {
            OrderDto orderDto = orderService.retreiveAllOrderByAdmin(userId);
            // return new ResponseEntity<OrderDto>(orderDto).build();
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam Status status) {
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/pdf/{orderId}")
    public ResponseEntity<byte[]> generateOrderPDF(@PathVariable Long orderId) {
        try {
            byte[] pdfData = orderService.generateOrderPDF(orderId);
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
    public ResponseEntity<Status> retreiveOrderStatus(@RequestBody Status status) {
        try {
            Status status = orderService.getStatus(status);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/order/payment/{orderId}")
    public ResponseEntity<OrderDto> selectPaymentMethod(@RequestBody OrderDto payment) {
        try {
            OrderDto result = orderService.selectPayment(payment);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
