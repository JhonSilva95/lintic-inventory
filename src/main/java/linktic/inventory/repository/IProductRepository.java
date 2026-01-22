package linktic.inventory.repository;

import linktic.inventory.dtos.ResponseDto;

public interface IProductRepository {
    ResponseDto findProductById(Integer productId);
}
