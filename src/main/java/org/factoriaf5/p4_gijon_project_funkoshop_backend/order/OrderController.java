package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.List;
import java.util.Map;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrderDTO;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order.Status;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductDTO;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RequestMapping("/orders") // ENDPOINT VISTO EN EL FRONTEND DEL FUNKO
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/orders/user")
    public ResponseEntity<List<OrderDTO>> listOrdersByUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User authenticatedUser = (User) authentication.getPrincipal();

        List<OrderDTO> orderList = orderService.listOrdersByUser(authenticatedUser);

        return ResponseEntity.ok(orderList);
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderList = orderService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }

    // ENDPOINT BACKEND - sin implementation en el frontend actual
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/status/{orderId}")
    public ResponseEntity<Status> getStatus(@PathVariable Long orderId) {
        Status retrievedstatus = orderService.getStatus(orderId);
        return ResponseEntity.ok().body(retrievedstatus);
    }

    // ENDPOINT BACKEND - sin implementation en el frontend actual
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<Map<String, String>> updateStatus(@PathVariable Long orderId, @RequestBody Status status) {
        orderService.updateOrderStatus(orderId, status);
        Map<String, String> response = Map.of("message\n", "Order status updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ENDPOINT BACKEND - sin implementation en el frontend actual
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/pdf/{orderId}")
    public ResponseEntity<byte[]> generateOrderPDF(@PathVariable Long orderId) {
            byte[] pdfData = orderService.generateOrderPDF(orderId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("order_" + orderId + ".pdf").build());
            return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    // ENDPOINT FRONTEND
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/details/sales")
    public ResponseEntity<List<OrderDTO>> listByMonth() {
            List<OrderDTO> salesByMonth = orderService.listByMonth();
            return ResponseEntity.ok(salesByMonth);
        }

    @PreAuthorize("hasRole('ADMIN')") // Este podria ser mas de user que de admin
    @GetMapping("/details/sales")
    public ResponseEntity<List<ProductDTO>> getBestSellers() {
        List<ProductDTO> bestSeller = orderService.getBestSellers();
        return ResponseEntity.ok(bestSeller);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/details/pdf")
    public ResponseEntity<byte[]> generateOrderPDF(DetailOrderDTO detailOrderDto) {
            byte[] pdfData = orderService.generatePDFAllOrders(detailOrderDto);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("detailorder_" + detailOrderDto + ".pdf").build());
            return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    /* @PreAuthorize("hasRole('USER')")
    @GetMapping("/details/email")
    public ResponseEntity<Void> sendEmail(DetailOrderDTO detailOrderDto) {
            orderService.sendEmail(detailOrderDto);
            return ResponseEntity.noContent().build();
        } */

    // ENDPOINT BACKEND - sin implementation en el frontend actual
    /*
     * @PreAuthorize("hasRole('USER')")
     * 
     * @PatchMapping("/order/payment/{orderId}")
     * public ResponseEntity<OrderDto>
     * selectPaymentMethod(@RequestHeader("Authorization") String
     * authorizationHeader,
     * 
     * @RequestParam OrderDto payment, @PathVariable Long orderId) {
     * try {
     * if (authorizationHeader == null ||
     * !authorizationHeader.startsWith("Bearer ")) {
     * return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
     * }
     * 
     * OrderDto result = orderService.selectPayment(authorizationHeader, payment,
     * orderId);
     * return ResponseEntity.ok(result);
     * } catch (IllegalArgumentException error) {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
     * }
     * }
     */
}