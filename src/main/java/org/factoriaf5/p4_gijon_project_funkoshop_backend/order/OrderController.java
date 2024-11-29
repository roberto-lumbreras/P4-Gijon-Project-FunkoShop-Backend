package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    OrderService orderService;

    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
    OrderDTO createdOrder = orderService.createOrder(orderDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping(" ??? ") #solo puede admin?
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
    OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
    return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}/pdf")
    public ResponseEntity<byte[]> generateOrderPDF(@PathVariable Long orderId) {
    byte[] pdfData = orderService.generateOrderPDF(orderId);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename("order_" + orderId + ".pdf").build());
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

}
