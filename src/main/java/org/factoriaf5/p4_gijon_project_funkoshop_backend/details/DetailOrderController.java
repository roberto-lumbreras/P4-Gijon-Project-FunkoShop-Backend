package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class DetailOrderController {

    @Autowired
    DetailOrderServices detailOrderServices;

    @GetMapping("/details/sales")
    public ResponseEntity<List<DetailOrderDto>> retrieveSales(
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<DetailOrderDto> salesList = detailOrderServices.getSales(authorizationHeader);
            return ResponseEntity.ok(salesList);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}