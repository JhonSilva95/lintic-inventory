package linktic.inventory.client;

import linktic.inventory.dtos.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8080/products")
public interface ProductClient {

    @GetMapping("/product/{id}")
    ResponseDto findProductById(@PathVariable("id") Integer id);
}
