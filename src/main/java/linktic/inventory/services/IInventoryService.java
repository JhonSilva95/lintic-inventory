package linktic.inventory.services;

import linktic.inventory.dtos.ResponseDto;

public interface IInventoryService {
    ResponseDto getStockByProductId(Integer productId);
}
