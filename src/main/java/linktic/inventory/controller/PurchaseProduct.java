package linktic.inventory.controller;

import linktic.inventory.dtos.PurchaseRequestDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.service.IPurchaseProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linktic")
@RequiredArgsConstructor
@Slf4j
public class PurchaseProduct {

    private final IPurchaseProductService service;

    @PostMapping("/purchase")
    public ResponseEntity<ResponseDto> purchase(@RequestBody PurchaseRequestDto request) {
        log.info("Request received for purchase");
        return ResponseEntity.ok(service.processPurchase(request));
    }
}
