package linktic.inventory.services.impl;

import jakarta.transaction.Transactional;
import linktic.inventory.dtos.PurchaseRequestDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.entities.InventoryEntity;
import linktic.inventory.entities.PurchaseHistoryEntity;
import linktic.inventory.exceptions.BusinessException;
import linktic.inventory.repositories.InventoryRepository;
import linktic.inventory.repositories.PurchaseHistoryRepository;
import linktic.inventory.services.IPurchaseProductService;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseProductServiceImpl implements IPurchaseProductService {

    private final PurchaseHistoryRepository historyRepository;
    private final InventoryRepository repository;

    @Override
    @Transactional
    public ResponseDto processPurchase(PurchaseRequestDto request) {
        log.info("Starting purchase process for product: {}", request.getProductId());

        InventoryEntity updatedInventory = updateStock(
                request.getProductId(),
                request.getQuantity()
        );

        saveHistory(request);

        Map<String, Object> content = Map.of(
                "productId", request.getProductId(),
                "quantityPurchased", request.getQuantity(),
                "newStock", updatedInventory.getQuantity(),
                "date", LocalDateTime.now()
        );

        return ResponseDto.builder()
                .code(CodesAndDescriptionResponses.OK.getCode())
                .description(CodesAndDescriptionResponses.OK.getDescription())
                .content(content)
                .build();
    }

    private void saveHistory(PurchaseRequestDto request) {
        PurchaseHistoryEntity history = PurchaseHistoryEntity.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .purchaseDate(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }

    @Transactional
    private InventoryEntity updateStock(Integer productId, Integer quantityToReduce) {
        InventoryEntity inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException(CodesAndDescriptionResponses.ERROR_BD_INVENTORY.getDescription(),
                        CodesAndDescriptionResponses.ERROR_BD_INVENTORY.getCode()));

        if (inventory.getQuantity() < quantityToReduce) {
            throw new BusinessException(CodesAndDescriptionResponses.PRODUCT_NOT_AVAILABLE.getDescription(),
                    CodesAndDescriptionResponses.PRODUCT_NOT_AVAILABLE.getCode());
        }

        inventory.setQuantity(inventory.getQuantity() - quantityToReduce);
        InventoryEntity updated = repository.save(inventory);

        log.info("Emitting inventory change event for product {}", productId);

        return updated;
    }
}
