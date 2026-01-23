package linktic.inventory.exceptions;

import linktic.inventory.controllers.PurchaseProduct;
import linktic.inventory.dtos.PurchaseRequestDto;
import linktic.inventory.services.IPurchaseProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PurchaseProduct.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IPurchaseProductService purchaseService;

    @Test
    void handleBusinessException_ShouldReturnNotFound() throws Exception {
        BusinessException businessException = new BusinessException("Producto no encontrado", 404);

        when(purchaseService.processPurchase(any(PurchaseRequestDto.class)))
                .thenThrow(businessException);

        mockMvc.perform(post("/linktic/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 1}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.description").value("Producto no encontrado"));
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() throws Exception {
        when(purchaseService.processPurchase(any(PurchaseRequestDto.class)))
                .thenThrow(new RuntimeException("Error inesperado en el servidor"));

        mockMvc.perform(post("/linktic/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 1}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.description").exists());
    }
}
