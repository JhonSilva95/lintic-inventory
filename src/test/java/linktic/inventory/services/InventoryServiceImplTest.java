package linktic.inventory.services;

import linktic.inventory.dtos.InventoryResponseDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.entities.InventoryEntity;
import linktic.inventory.exceptions.BusinessException;
import linktic.inventory.repositories.IProductRepository;
import linktic.inventory.repositories.InventoryRepository;
import linktic.inventory.services.impl.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private InventoryEntity mockInventory;
    private ResponseDto mockProductResponse;

    @BeforeEach
    void setUp() {
        mockInventory = InventoryEntity.builder()
                .productId(101)
                .quantity(5)
                .build();

        mockProductResponse = ResponseDto.builder()
                .code(0)
                .description("OK")
                .content(Map.of("name", "Laptop Pro"))
                .build();
    }

    @Test
    void getStockByProductId_Success() {
        when(repository.findByProductId(101)).thenReturn(Optional.of(mockInventory));
        when(productRepository.findProductById(101)).thenReturn(mockProductResponse);

        ResponseDto result = inventoryService.getStockByProductId(101);

        assertNotNull(result);
        assertEquals(0, result.getCode());

        InventoryResponseDto content = (InventoryResponseDto) result.getContent();
        assertEquals("Laptop Pro", content.getProductName());
        assertEquals(5, content.getQuantity());
        assertEquals("DISPONIBLE", content.getStatus());

        verify(repository, times(1)).findByProductId(101);
        verify(productRepository, times(1)).findProductById(101);
    }

    @Test
    void getStockByProductId_ProductNotFoundInLocalInventory_ThrowsException() {
        when(repository.findByProductId(anyInt())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            inventoryService.getStockByProductId(999);
        });

        verify(productRepository, never()).findProductById(anyInt());
    }
}
