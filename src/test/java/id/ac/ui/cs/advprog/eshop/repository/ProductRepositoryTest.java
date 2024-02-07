package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    
    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testDelete() {
        // Create
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Delete
        Product deletedProduct = productRepository.deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // Verify that the product was deleted successfully
        assertNotNull(deletedProduct);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", deletedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", deletedProduct.getProductName());
        assertEquals(100, deletedProduct.getProductQuantity());

        // Verify that the product is no longer in the repository
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistingProduct() {
        // Try to delete a product that doesn't exist
        Product deletedProduct = productRepository.deleteById("non-existing-id");

        // Verify that no product was deleted
        assertNull(deletedProduct);
    }


    
    @Test
    void testEditProduct() {
        // Arrange
        Product originalProduct = new Product();
        originalProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        originalProduct.setProductName("Sampo Cap Bambang");
        originalProduct.setProductQuantity(100);
        productRepository.create(originalProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bango");
        updatedProduct.setProductQuantity(500);

        // Act
        boolean isEdited = originalProduct.editProduct(updatedProduct);

        // Assert
        assertTrue(isEdited);
        assertEquals(updatedProduct.getProductId(), originalProduct.getProductId());
        assertEquals(updatedProduct.getProductName(), originalProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), originalProduct.getProductQuantity());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(originalProduct.getProductId(), savedProduct.getProductId());
        assertEquals(originalProduct.getProductName(), savedProduct.getProductName());
        assertEquals(originalProduct.getProductQuantity(), savedProduct.getProductQuantity());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductNegative() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bango");
        updatedProduct.setProductQuantity(-500);

        String edittedProductId = updatedProduct.getProductId();
        Product edittedProduct = productRepository.getProduct(edittedProductId);

        assertFalse(edittedProduct.editProduct(updatedProduct));

        assertEquals(edittedProduct.getProductId(), product.getProductId());
        assertEquals(edittedProduct.getProductName(), product.getProductName());
        assertEquals(edittedProduct.getProductQuantity(), product.getProductQuantity());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());

        assertFalse(productIterator.hasNext());
    }

}
