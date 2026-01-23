package linktic.inventory.controllers;

import linktic.inventory.dtos.InventoryResponseDto;
import linktic.inventory.dtos.ResponseDto;
import linktic.inventory.services.IInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IInventoryService inventoryService;

    private ResponseDto mockResponse;

    @BeforeEach
    void setUp() {
        InventoryResponseDto content = InventoryResponseDto.builder()
                .productId(1)
                .productName("Producto de Prueba")
                .quantity(10)
                .status("DISPONIBLE")
                .build();

        mockResponse = ResponseDto.builder()
                .code(0)
                .description("OK")
                .content(content)
                .build();
    }

    @Test
    void getStock_ShouldReturnProductInfoAndOk() throws Exception {
        when(inventoryService.getStockByProductId(anyInt())).thenReturn(mockResponse);

        mockMvc.perform(get("/inventory/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.description").value("OK"))
                .andExpect(jsonPath("$.content.productName").value("Producto de Prueba"))
                .andExpect(jsonPath("$.content.quantity").value(10));
    }
}
