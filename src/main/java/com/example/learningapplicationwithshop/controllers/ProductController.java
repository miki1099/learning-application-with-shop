package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.dto.ProductDto;
import com.example.learningapplicationwithshop.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/admin/product/create", method = RequestMethod.POST)
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @RequestMapping(value = "/admin/product/update/{id}", method = RequestMethod.PUT)
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable("id") int id) {
        return productService.update(id, productDto);
    }

    @RequestMapping(value = "/admin/product/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") int id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/product/getPagesCount", method = RequestMethod.GET)
    public int getPagesCount(@RequestParam int size) {
        return productService.getPagesCountBySize(size);
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public List<ProductDto> getProductList(@RequestParam int page, @RequestParam int size,
                                           @Param("sortByAsc") Boolean sortByAsc, @RequestParam boolean getOnlyAvailable) {
        return productService.getAllProductsPaginatedWithSortAndAvailable(page, size, sortByAsc, getOnlyAvailable);
    }

}
