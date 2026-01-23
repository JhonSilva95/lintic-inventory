package linktic.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private String status; // Ejemplo: "DISPONIBLE" o "AGOTADO"
}
