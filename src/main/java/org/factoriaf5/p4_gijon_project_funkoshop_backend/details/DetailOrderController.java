// package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ContentDisposition;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RestController;

// @RestController("/api/v1") //ENDPOINT VISTO EN EL FRONTEND DEL FUNKO
// public class DetailOrderController {
// DetailOrderService detailOrderService;
// @Autowired
// DetailOrderDto orderDto;
// public DetailOrderController(DetailOrderService detailOrderService,
// DetailOrderDto orderDto) {
// this.detailOrderService = detailOrderService;
// this.orderDto = orderDto;
// }
// // ENDPOINT FRONTEND
// @PreAuthorize("hasRole('ADMIN')")
// @GetMapping("/details/sales")
// public ResponseEntity<List<DetailOrderDto>> retrieveSales(
// @RequestHeader("Authorization") String authorizationHeader) {
// try {
// if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer
// ")) {
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// List<DetailOrderDto> salesList =
// detailOrderService.getSales(authorizationHeader);
// return ResponseEntity.ok(salesList);
// } catch (IllegalArgumentException error) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
// }
// }
// @PreAuthorize("hasRole('ADMIN')") // Este podria ser mas de user que de admin
// @GetMapping("/details/sales")
// public ResponseEntity<DetailOrderDto> retrieveBestSeller(
// @RequestHeader("Authorization") String authorizationHeader,DetailOrderDto
// detailOrderDto) {
// try{
// if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer
// ")) {
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// DetailOrderDto bestSeller =
// detailOrderService.getBestSeller(authorizationHeader,detailOrderDto);
// return ResponseEntity.ok(bestSeller);
// } catch (IllegalArgumentException error) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
// }
// }
// // ENDPOINT FRONTEND
// @PreAuthorize("hasRole('ADMIN')")
// @GetMapping("/details/sales")
// public ResponseEntity<DetailOrderDto> listByMonth(
// @RequestHeader("Authorization") String authorizationHeader,DetailOrderDto
// detailOrderDto) {
// try{
// if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer
// ")) {
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// DetailOrderDto salesByMonth =
// detailOrderService.getSalesByMonth(authorizationHeader,detailOrderDto);
// return ResponseEntity.ok(salesByMonth);
// } catch (IllegalArgumentException error) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
// }
// }
// // ENDPOINT BACKEND - sin implementation en el frontend actual (aun no hemos
// podido acceder como admin)
// @PreAuthorize("hasRole('ADMIN')")
// @GetMapping("/details/pdf/")
// public ResponseEntity<byte[]>
// generateOrderPDF(@RequestHeader("Authorization") String authorizationHeader,
// DetailOrderDto detailOrderDto) {
// try {
// if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer
// ")) {
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// byte[] pdfData = detailOrderService.generatePDFAllOrders(authorizationHeader,
// detailOrderDto);
// HttpHeaders headers = new HttpHeaders();
// headers.setContentType(MediaType.APPLICATION_PDF);
// headers.setContentDisposition(ContentDisposition.inline().filename("detailorder_"
// + detailOrderDto + ".pdf").build());
// return ResponseEntity.ok().headers(headers).body(pdfData);
// } catch (IllegalArgumentException error) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
// }

// }
// @PreAuthorize("hasRole('USER')")
// @GetMapping("/details/productquantity")
// public ResponseEntity<Integer> CalculateQuantity(
// @RequestHeader("Authorization") String authorizationHeader,DetailOrderDto
// detailOrderDto) {
// try {
// if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer
// ")) {
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// Integer quantity =
// detailOrderService.calculateProductQuantity(authorizationHeader,
// detailOrderDto);
// return ResponseEntity.ok(quantity);
// } catch (IllegalArgumentException error) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
// }
// }
// @PreAuthorize("hasRole('USER')")
// @GetMapping("/details/email")
// public ResponseEntity<Void> sendEmail(
// @RequestHeader("Authorization") String authorizationHeader, DetailOrderDto
// detailOrderDto) {
// try {
// if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer "))
// {
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// detailOrderService.sendEmail(authorizationHeader, detailOrderDto);
// return ResponseEntity.noContent().build();
// } catch (IllegalArgumentException error) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
// }
// }
// }