package linktic.inventory.client;

import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.exeptions.BusinessException;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient.Builder builder,
                         @Value("${endpoint.product-service.url}") String url) {
        this.restClient = builder.baseUrl(url).build();
    }

    public ResponseDto findProductById(Integer productId) {
        log.info("Calling Product Microservice for ID: {}", productId);
        return restClient.get()
                .uri("/product/{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new BusinessException(CodesAndDescriptionResponses.NON_EXISTENT_PRODUCT.getDescription(),
                                CodesAndDescriptionResponses.NON_EXISTENT_PRODUCT.getCode());
                })
                .body(ResponseDto.class);
    }
}
