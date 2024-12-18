package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.util.List;
import java.util.Map;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.order.Order.Status;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductDTO;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/orders")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDTO>> listOrdersByUser(@PathVariable Long id) {
        User authenticatedUser = userRepository.findById(id).get();
        List<OrderDTO> orderList = orderService.listOrdersByUser(authenticatedUser);
        return ResponseEntity.ok(orderList);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderList = orderService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/order/status/{orderId}")
    public ResponseEntity<Status> getStatus(@PathVariable Long orderId) {
        Status retrievedstatus = orderService.getStatus(orderId);
        return ResponseEntity.ok().body(retrievedstatus);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> updateStatus(@PathVariable Long orderId, @RequestParam Status status) {
        orderService.updateOrderStatus(orderId, status);
        Map<String, String> response = Map.of("message\n", "Order status updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/pdf/{orderId}")
    public ResponseEntity<byte[]> generateOrderPDFId(@PathVariable Long orderId) {
        byte[] pdfData = orderService.generateOrderPDFId(orderId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("order_" + orderId + ".pdf").build());
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    @GetMapping("/details/month")
    public ResponseEntity<List<OrderDTO>> listByMonth() {
        List<OrderDTO> salesByMonth = orderService.listByMonth();
        return ResponseEntity.ok(salesByMonth);
    }

    @GetMapping("/details/sales")
    public ResponseEntity<List<ProductDTO>> getBestSellers() {
        List<ProductDTO> bestSeller = orderService.getBestSellers();
        return ResponseEntity.ok(bestSeller);
    }

    @GetMapping("/details/pdf")
    public ResponseEntity<byte[]> generateOrderPDF() {
        byte[] pdfData = orderService.generatePDFAllOrders();
        if (pdfData.length == 0) {
            throw new IllegalStateException("Generated PDF is empty.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("detailorder.pdf").build());

        System.out.println("PDF generated successfully. Size: " + pdfData.length + " bytes");
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }
}