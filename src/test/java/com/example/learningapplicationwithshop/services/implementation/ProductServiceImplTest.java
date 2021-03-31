package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.model.Product;
import com.example.learningapplicationwithshop.model.dto.ProductDto;
import com.example.learningapplicationwithshop.repositories.ProductRepository;
import com.example.learningapplicationwithshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Spy
    private final ModelMapper modelMapper  = new ModelMapper();

    @InjectMocks
    private ProductServiceImpl productService;

    private final Product product1 = new Product();
    private final Product product2 = new Product();;
    private final Product product3 = new Product();;
    private final Product product4 = new Product();;

    private boolean compareProducts(ProductDto expected, ProductDto actual) {
        return expected.getName().equals(actual.getName()) &&
                expected.getDescription().equals(actual.getDescription()) &&
                expected.getNumberAvailable().equals(actual.getNumberAvailable()) &&
                expected.getPrice().equals(actual.getPrice()) &&
                expected.getPicture() == actual.getPicture();
    }

    @BeforeEach
    void setUp() {
        product1.setName("Product 1");
        product1.setDescription("Description product 1");
        product1.setId(1);
        product1.setNumberAvailable(3);
        product1.setPrice(BigDecimal.valueOf(34.32));

        product2.setName("Product 2");
        product2.setDescription("Description product 2");
        product2.setId(2);
        product2.setNumberAvailable(0);
        product2.setPrice(BigDecimal.valueOf(2.32));

        product2.setName("Product 3");
        product2.setDescription("Description product 3");
        product2.setId(3);
        product2.setNumberAvailable(17);
        product2.setPrice(BigDecimal.valueOf(3432.32));

        product2.setName("Product 4");
        product2.setDescription("Description product 4");
        product2.setId(4);
        product2.setNumberAvailable(0);
        product2.setPrice(BigDecimal.valueOf(9384.4));
    }
    @Test
    void create() {
        ProductDto productDto = new ProductDto();

        productDto.setName("Product 1");
        productDto.setDescription("Description product 1");
        productDto.setNumberAvailable(3);
        productDto.setPrice(BigDecimal.valueOf(34.32));

        when(productRepository.save(any())).thenReturn(product1);

        ProductDto actual = productService.create(productDto);

        assertTrue(compareProducts(productDto, actual));
    }

    @Test
    void deleteById() {

        when(productRepository.existsById(anyInt())).thenReturn(true);
        when(productRepository.getOne(anyInt())).thenReturn(product1);
        doNothing().when(productRepository).delete(any());

        productService.deleteById(1);

        verify(productRepository, times(1)).existsById(anyInt());
        verify(productRepository, times(1)).getOne(anyInt());
        verify(productRepository, times(1)).delete(any());

    }

    @Test
    void update() {
        ProductDto productUpdate = ProductDto.builder()
                .name("NewName")
                .numberAvailable(2)
                .build();

        when(productRepository.existsById(anyInt())).thenReturn(true);
        when(productRepository.getOne(anyInt())).thenReturn(product1);

        ProductDto actual = productService.update(1, productUpdate);

        assertEquals(productUpdate.getName(), actual.getName());
        assertEquals(productUpdate.getNumberAvailable() , actual.getNumberAvailable());
    }
}