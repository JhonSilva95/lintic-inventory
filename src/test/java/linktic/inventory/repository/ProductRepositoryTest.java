package linktic.inventory.repository;

import linktic.inventory.client.ProductClient;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.exceptions.BusinessException;
import linktic.inventory.repositories.impl.ProductRepositoryImpl;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    void findProductById_Success() {
        Integer productId = 1;
        ResponseDto expectedResponse = new ResponseDto();
        when(productClient.findProductById(productId)).thenReturn(expectedResponse);

        ResponseDto actualResponse = productRepository.findProductById(productId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(productClient, times(1)).findProductById(productId);
    }

    @Test
    void findProductById_ThrowsBusinessException() {
        Integer productId = 1;
        when(productClient.findProductById(productId)).thenThrow(new RuntimeException("Connection failed"));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            productRepository.findProductById(productId);
        });

        assertEquals(CodesAndDescriptionResponses.ERROR_PRODUCT_NOT_FOUND.getDescription(), exception.getMessage());
        assertEquals(CodesAndDescriptionResponses.ERROR_PRODUCT_NOT_FOUND.getCode(), Integer.parseInt(exception.getCode()));

        verify(productClient, times(1)).findProductById(productId);
    }
}
