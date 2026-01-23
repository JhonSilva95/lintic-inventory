package linktic.inventory.controllers;

import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.services.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseDto> getStock(@PathVariable Integer productId) {
        return ResponseEntity.ok(inventoryService.getStockByProductId(productId));
    }

}
