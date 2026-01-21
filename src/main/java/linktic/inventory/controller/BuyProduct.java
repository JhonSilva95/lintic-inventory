package linktic.inventory.controller;

import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.service.IBuyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/linktic")
@RequiredArgsConstructor
public class BuyProduct {

    private final IBuyProductService service;

    @GetMapping("/buy-product")
    public ResponseEntity<ResponseDto> buyProduct(@RequestParam Integer idProduct,
                                                  @RequestParam Integer quantity) {
        return ResponseEntity.ok(service.buyProduct(idProduct, quantity));
    }
}
