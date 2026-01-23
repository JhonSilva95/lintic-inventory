package linktic.inventory.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodesAndDescriptionResponses {

    OK(0, "OK"),
    GENERAL_ERROR_INVENTORY(11, "GENERAL INVENTORY ERROR"),
    ERROR_PRODUCT_NOT_FOUND(12, "Error buscando el producto, por favor intenta de nuevo mas tarde."),
    ERROR_BD_INVENTORY(13, "Error a la hora de buscar el producto en nuestra base de datos"),
    PRODUCT_NOT_AVAILABLE(14, "Producto no disponible en el inventario"),
    NON_EXISTENT_PRODUCT(15, "Producto no existe en el catálogo");

    private final Integer code;
    private final String description;
}
