package linktic.inventory.service.impl;

import linktic.inventory.client.ProductClient;
import linktic.inventory.dtos.InventoryResponseDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.entity.InventoryEntity;
import linktic.inventory.exeptions.BusinessException;
import linktic.inventory.repository.IProductRepository;
import linktic.inventory.repository.InventoryRepository;
import linktic.inventory.service.IInventoryService;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements IInventoryService {

    private final InventoryRepository repository;
    private final IProductRepository productRepository;

    @Override
    public ResponseDto getStockByProductId(Integer productId) {
        log.info("Consulting inventory for product id: {}", productId);

        InventoryEntity inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException(CodesAndDescriptionResponses.PRODUCT_NOT_FOUND.getDescription(),
                        CodesAndDescriptionResponses.GENERAL_ERROR_INVENTORY.getCode()));

        ResponseDto productResponse = productRepository.findProductById(productId);

        InventoryResponseDto inventoryData = InventoryResponseDto.builder()
                .productId(productId)
                .productName(productResponse.getDescription())
                .quantity(inventory.getQuantity())
                .status(inventory.getQuantity() > 0 ? "DISPONIBLE" : "AGOTADO")
                .build();

        return ResponseDto.builder()
                .code(CodesAndDescriptionResponses.OK.getCode())
                .description(CodesAndDescriptionResponses.OK.getDescription())
                .content(inventoryData)
                .build();
    }
}
