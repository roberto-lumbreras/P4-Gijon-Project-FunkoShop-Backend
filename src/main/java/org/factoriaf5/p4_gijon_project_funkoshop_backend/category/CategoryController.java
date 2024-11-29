package org.factoriaf5.p4_gijon_project_funkoshop_backend.category;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class CategoryController {

@RestController
@RequestMapping(path = "/api")
public class DeviceController {

        @Autowired
        CategoryService service;

        @GetMapping(path = "/categories")
        public ResponseEntity<List<CategoryDTO>> getCategories() {
        return new ResponseEntity<>(service.getCategories(), HttpStatus.OK);
        }

        @PutMapping(path = "/{categoryId}")
        public ResponseEntity<CategoryDTO> setCategory(@RequestBody CategoryDTO category, @PathVariable Long categoryId){
        return new ResponseEntity<>(service.setCategory(category, categoryId), HttpStatus.OK);
    
        }


    }

}
