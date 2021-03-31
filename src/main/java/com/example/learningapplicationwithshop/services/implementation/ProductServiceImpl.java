package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.NotFoundException;
import com.example.learningapplicationwithshop.model.Product;
import com.example.learningapplicationwithshop.model.dto.ProductDto;
import com.example.learningapplicationwithshop.repositories.ProductRepository;
import com.example.learningapplicationwithshop.services.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductDto create(ProductDto productCreate) {
        Product productToSave = modelMapper.map(productCreate, Product.class);
        return modelMapper.map(
                productRepository.save(productToSave),
                ProductDto.class
        );
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Product productToDelete = getOneSafe(id);
        productRepository.delete(productToDelete);
    }

    @Override
    @Transactional
    public ProductDto update(int id, ProductDto productUpdate) {
        Product productToUpdate = getOneSafe(id);
        if (productUpdate.getName() != null) productToUpdate.setName(productUpdate.getName());
        if (productUpdate.getDescription() != null) productToUpdate.setDescription(productUpdate.getDescription());
        if (productUpdate.getPrice() != null) productToUpdate.setPrice(productUpdate.getPrice());
        if (productUpdate.getNumberAvailable() != null)
            productToUpdate.setNumberAvailable(productUpdate.getNumberAvailable());
        if (productUpdate.getPicture() != null) productToUpdate.setPicture(productUpdate.getPicture());
        return modelMapper.map(productToUpdate, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProductsPaginatedWithSortAndAvailable(int page, int size, Boolean sortByAsc, boolean getOnlyAvailable) {
        Pageable pageable = PageRequest.of(page, size);

        if (sortByAsc == null) {
            return getOnlyAvailable ?
                    convertList(productRepository.findAllByNumberAvailableIsGreaterThan(0, pageable).getContent())
                    : convertList(productRepository.findAll(pageable).getContent());
        } else if (sortByAsc) {
            return getOnlyAvailable ?
                    convertList(productRepository.findAllByNumberAvailableIsGreaterThanOrderByPriceAsc(0, pageable).getContent())
                    : convertList(productRepository.findAllByOrderByPriceAsc(pageable).getContent());
        } else {
            return getOnlyAvailable ?
                    convertList(productRepository.findAllByNumberAvailableIsGreaterThanOrderByPriceDesc(0, pageable).getContent())
                    : convertList(productRepository.findAllByOrderByPriceDesc(pageable).getContent());
        }
    }

    @Override
    public int getPagesCountBySize(int size) {
        int productsCount = (int) productRepository.count();
        return size == 0 ? -1 : (productsCount - 1) / size;
    }

    private Product getOneSafe(Integer id) {
        if (productRepository.existsById(id)) {
            return productRepository.getOne(id);
        } else {
            throw new NotFoundException("Product with this id:" + id + " was not found!");
        }
    }

    private ProductDto convert(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private List<ProductDto> convertList(List<Product> list) {
        return list.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

}
