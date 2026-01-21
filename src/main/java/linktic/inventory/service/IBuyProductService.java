package linktic.inventory.service;

import linktic.inventory.dtos.ResponseDto;

public interface IBuyProductService {
    ResponseDto buyProduct(Integer idProduct, Integer quantity);
}
