package com.example.learningapplicationwithshop.services;

import com.example.learningapplicationwithshop.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productCreate);

    void deleteById(int id);

    ProductDto update(int id, ProductDto productUpdate);

    List<ProductDto> getAllProductsPaginatedWithSortAndAvailable(int page, int size,
                                                                 Boolean sortByAsc, boolean getOnlyAvailable);

    int getPagesCountBySize(int size);

}
