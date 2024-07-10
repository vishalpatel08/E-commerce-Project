package com.ecommerce.ecommercebackend;

import com.ecommerce.ecommercebackend.api.product.ProductController;
import com.ecommerce.ecommercebackend.dataModel.Product;
import com.ecommerce.ecommercebackend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    public void setUp() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product2");
        product2.setPrice(20.0);

        products = Arrays.asList(product1, product2);
    }

    @Test
    public void testGetProducts_Success() throws Exception {
        Mockito.when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Product1")))
                .andExpect(jsonPath("$[0].price", is(10.0)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Product2")))
                .andExpect(jsonPath("$[1].price", is(20.0)))
                .andReturn();
    }
}
