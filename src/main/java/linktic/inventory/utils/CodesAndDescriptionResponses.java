package linktic.inventory.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodesAndDescriptionResponses {

    OK(0, "OK"),
    GENERAL_ERROR_INVENTORY(11, "GENERAL INVENTORY ERROR"),
    PRODUCT_NOT_FOUND(12, "Producto no encontrado en el inventario."),
    PRODUCT_NOT_AVAILABLE(13, "Producto no disponible, intenta de nuevo más tarde.");

    private final Integer code;
    private final String description;
}
