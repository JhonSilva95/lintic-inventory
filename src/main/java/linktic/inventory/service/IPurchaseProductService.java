package linktic.inventory.service;

import linktic.inventory.dtos.PurchaseRequestDto;
import linktic.inventory.dtos.ResponseDto;

public interface IPurchaseProductService {

    ResponseDto processPurchase(PurchaseRequestDto request);
}
