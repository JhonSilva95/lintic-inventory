package linktic.inventory.services.impl;

import linktic.inventory.dtos.InventoryResponseDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.entities.InventoryEntity;
import linktic.inventory.exceptions.BusinessException;
import linktic.inventory.repositories.IProductRepository;
import linktic.inventory.repositories.InventoryRepository;
import linktic.inventory.services.IInventoryService;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements IInventoryService {

    private final InventoryRepository repository;
    private final IProductRepository productRepository;

    @Override
    public ResponseDto getStockByProductId(Integer productId) {
        log.info("Consultando inventario para ID: {}", productId);

        InventoryEntity inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException(CodesAndDescriptionResponses.PRODUCT_NOT_AVAILABLE.getDescription(),
                        CodesAndDescriptionResponses.PRODUCT_NOT_AVAILABLE.getCode()));

        ResponseDto productResponse = productRepository.findProductById(productId);

        return ResponseDto.builder()
                .code(CodesAndDescriptionResponses.OK.getCode())
                .description(CodesAndDescriptionResponses.OK.getDescription())
                .content(assembleInventoryData(inventory, productResponse))
                .build();
    }

    private InventoryResponseDto assembleInventoryData(InventoryEntity inv, ResponseDto prod) {
        String name = "Desconocido";

        if (prod.getContent() instanceof Map<?, ?> map) {
            name = (String) map.get("name");
        }

        return InventoryResponseDto.builder()
                .productId(inv.getProductId())
                .productName(name)
                .quantity(inv.getQuantity())
                .status(inv.getQuantity() > 0 ? "DISPONIBLE" : "AGOTADO")
                .build();
    }
}
