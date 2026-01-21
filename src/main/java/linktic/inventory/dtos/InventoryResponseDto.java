package linktic.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryResponseDto {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private String status;
}
