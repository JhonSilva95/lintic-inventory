package linktic.inventory.service.impl;

import linktic.inventory.client.ProductClient;
import linktic.inventory.dtos.InventoryResponseDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.entity.InventoryEntity;
import linktic.inventory.repository.InventoryRepository;
import linktic.inventory.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements IInventoryService {

    private final InventoryRepository repository;
    private final ProductClient productClient;

    @Override
    public ResponseDto getStockByProductId(Integer productId) {
        log.info("Consulting inventory for product id: {}", productId);

        InventoryEntity inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException("Producto no registrado en inventario", "404"));

        // 2. Consultar información al Microservicio 1 usando Feign
        ResponseDto productResponse;
        try {
            productResponse = productClient.findProductById(productId);
        } catch (Exception e) {
            log.error("Error connecting to Product Service: {}", e.getMessage());
            throw new BusinessException("Servicio de productos no disponible", "503");
        }

        // 3. Estructurar la respuesta combinada
        InventoryResponseDto inventoryData = InventoryResponseDto.builder()
                .productId(productId)
                .productName(productResponse.getDescription()) // O mapear el 'content'
                .quantity(inventory.getQuantity())
                .status(inventory.getQuantity() > 0 ? "DISPONIBLE" : "AGOTADO")
                .build();

        return ResponseDto.builder()
                .code(200)
                .description("Consulta de inventario exitosa")
                .content(inventoryData)
                .build();
    }
}
