package linktic.inventory.service;

import linktic.inventory.dtos.ResponseDto;

public interface IInventoryService {
    ResponseDto getStockByProductId(Integer productId);
}
