package linktic.inventory.repositories;

import linktic.inventory.dtos.ResponseDto;

public interface IProductRepository {
    ResponseDto findProductById(Integer productId);
}
