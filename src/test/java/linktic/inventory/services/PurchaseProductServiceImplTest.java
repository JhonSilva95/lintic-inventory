package linktic.inventory.services;

import linktic.inventory.dtos.PurchaseRequestDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.entities.InventoryEntity;
import linktic.inventory.entities.PurchaseHistoryEntity;
import linktic.inventory.exceptions.BusinessException;
import linktic.inventory.repositories.InventoryRepository;
import linktic.inventory.repositories.PurchaseHistoryRepository;
import linktic.inventory.services.impl.PurchaseProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseProductServiceImplTest {

    @Mock
    private PurchaseHistoryRepository historyRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private PurchaseProductServiceImpl purchaseService;

    private PurchaseRequestDto validRequest;
    private InventoryEntity mockInventory;

    @BeforeEach
    void setUp() {
        validRequest = PurchaseRequestDto.builder()
                .productId(1)
                .quantity(5)
                .build();

        mockInventory = InventoryEntity.builder()
                .productId(1)
                .quantity(10)
                .build();
    }

    @Test
    void processPurchase_Success() {
        when(inventoryRepository.findByProductId(1)).thenReturn(Optional.of(mockInventory));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(historyRepository.save(any(PurchaseHistoryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseDto response = purchaseService.processPurchase(validRequest);

        assertNotNull(response);
        assertEquals(0, response.getCode()); // Suponiendo que OK.getCode() es 0

        verify(inventoryRepository).save(argThat(entity -> entity.getQuantity() == 5));
        verify(historyRepository, times(1)).save(any(PurchaseHistoryEntity.class));
    }

    @Test
    void processPurchase_InsufficientStock_ThrowsException() {
        validRequest.setQuantity(15);
        when(inventoryRepository.findByProductId(1)).thenReturn(Optional.of(mockInventory));

        assertThrows(BusinessException.class, () -> {
            purchaseService.processPurchase(validRequest);
        });

        verify(inventoryRepository, never()).save(any());
        verify(historyRepository, never()).save(any());
    }

    @Test
    void processPurchase_ProductNotFoundInInventory_ThrowsException() {
        when(inventoryRepository.findByProductId(anyInt())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            purchaseService.processPurchase(validRequest);
        });
    }
}
