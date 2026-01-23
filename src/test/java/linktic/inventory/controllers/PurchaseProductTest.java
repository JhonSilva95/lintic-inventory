package linktic.inventory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import linktic.inventory.dtos.PurchaseRequestDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.services.IPurchaseProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseProduct.class)
class PurchaseProductTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IPurchaseProductService purchaseService;

    @Test
    void purchase_ShouldReturnSuccessResponse() throws Exception {
        PurchaseRequestDto request = PurchaseRequestDto.builder()
                .productId(101)
                .quantity(2)
                .build();

        ResponseDto mockResponse = ResponseDto.builder()
                .code(0)
                .description("OK")
                .content(Map.of("status", "DISPONIBLE"))
                .build();

        when(purchaseService.processPurchase(any(PurchaseRequestDto.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/linktic/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Convertimos el DTO a JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.description").value("OK"))
                .andExpect(jsonPath("$.content.status").value("DISPONIBLE"));
    }
}