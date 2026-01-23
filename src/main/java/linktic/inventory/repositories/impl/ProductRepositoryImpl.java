package linktic.inventory.repositories.impl;

import linktic.inventory.client.ProductClient;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.exceptions.BusinessException;
import linktic.inventory.repositories.IProductRepository;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {

    private final ProductClient productClient;

    public ResponseDto findProductById(Integer productId) {
        try {
            return productClient.findProductById(productId);
        } catch (Exception e) {
            log.error("Error connecting to Product: {}", e.getMessage());
            throw new BusinessException(CodesAndDescriptionResponses.ERROR_PRODUCT_NOT_FOUND.getDescription(),
                    CodesAndDescriptionResponses.ERROR_PRODUCT_NOT_FOUND.getCode());
        }
    }
}
