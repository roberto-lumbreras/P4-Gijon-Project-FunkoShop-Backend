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
@RequestMapping("/api")

public class OrderController {
    
        OrderService orderService;
    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {

        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam Status status) {   

        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/pdf/{orderId}")
    public ResponseEntity<byte[]> generateOrderPDF(@PathVariable Long orderId) {   

    byte[] pdfData = orderService.generateOrderPDF(orderId);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename("order_" + orderId + ".pdf").build());
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/all")
    public ResponseEntity<OrderDto> retreiveAllOrder (@RequestBody OrderDto order) {
        OrderDto orderDto = orderService.retreiveAllOrder(userId);
    } 

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/status/{orderId}")
    public ResponseEntity<Status> retreiveOrderStatus (@RequestBody Status status) {
        Status status = orderService.getStatus(status);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping ("/order/payment/{orderId}")
    public ResponseEntity<OrderDto> SelectPaymentMethod (@RequestBody OrderDto payment) {
        OrderDto payment = orderService.SelectPaymentMethod(payment);   
    }

    
}