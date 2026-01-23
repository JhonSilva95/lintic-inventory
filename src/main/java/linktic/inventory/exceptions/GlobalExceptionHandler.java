package linktic.inventory.exceptions;

import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.utils.CodesAndDescriptionResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto> handleBusinessException(BusinessException ex) {
        log.error("Error Business Exception: {}", ex.getMessage());
        ResponseDto error = ResponseDto.builder()
                .code(Integer.parseInt(ex.getCode()))
                .description(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception ex) {
        log.error("General Exception: {}", ex.getMessage());
        ResponseDto error = ResponseDto.builder()
                .code(CodesAndDescriptionResponses.GENERAL_ERROR_INVENTORY.getCode())
                .description(CodesAndDescriptionResponses.GENERAL_ERROR_INVENTORY.getDescription())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
