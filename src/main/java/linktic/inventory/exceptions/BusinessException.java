package linktic.inventory.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = String.valueOf(code);
    }
}
