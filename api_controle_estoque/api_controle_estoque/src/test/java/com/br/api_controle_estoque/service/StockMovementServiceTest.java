package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.StockMovementRequestDto;
import com.br.api_controle_estoque.DTO.StockMovementRequestUpdateDto;
import com.br.api_controle_estoque.model.MovementType;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.StockMovement;
import com.br.api_controle_estoque.model.User;
import com.br.api_controle_estoque.repository.ProductRepository;
import com.br.api_controle_estoque.repository.StockMovementRepository;
import com.br.api_controle_estoque.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StockMovementServiceTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StockMovementService stockMovementService;


    private Product product;
    private User user;
    private StockMovementRequestDto requestDto;

    @BeforeEach
    void setUp(){
        product = new Product();
        product.setId(1l);
        product.setName("Produto teste");
        product.setQuantity(0);

        requestDto = new StockMovementRequestDto(
                1L,
                MovementType.ENTRADA,
                10,
                "Movimentação de teste"
        );
    }

    @Test
    void testCreateStockMovement_Sucess(){
        // Mockando as chamadas do banco de dados
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StockMovement stockMovement = stockMovementService.createStockMovement(requestDto);

        // Verificações
        assertNotNull(stockMovement);
        assertEquals(product, stockMovement.getProduct());
        assertEquals(MovementType.ENTRADA, stockMovement.getMovementType());
        assertEquals(10, stockMovement.getQuantity());
        assertEquals("Movimentação de teste", stockMovement.getObservation());
        assertEquals(10, product.getQuantity());

        // Garantir que os métodos de repositório foram chamados
        verify(productRepository, times(1)).findById(1L);
        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }

    @Test
    void testCreateStockMovement_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                stockMovementService.createStockMovement(requestDto));
        assertEquals("Produto não encontrado", exception.getMessage());
    }

   /* @Test
    public void testUpdateStockMovement_CorrectReversal() {
        // Arrange - Criando um produto com quantidade inicial de 50
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(50);

        // Criando uma movimentação de ENTRADA de 10 unidades
        StockMovement initialMovement = new StockMovement();
        initialMovement.setId(1L);
        initialMovement.setProduct(product);
        initialMovement.setMovementType(MovementType.ENTRADA);
        initialMovement.setQuantity(10);

        // Atualizando estoque manualmente para simular a entrada correta
        product.setQuantity(product.getQuantity() + initialMovement.getQuantity()); // 50 + 10 = 60

        // Criando um DTO para a nova movimentação que será aplicada após a reversão
        StockMovementRequestUpdateDto updateDto = new StockMovementRequestUpdateDto(1L, 15, MovementType.SAIDA, "Nova Observação");

        // Simulando a busca pela movimentação existente no banco de dados
        when(stockMovementRepository.findById(1L)).thenReturn(Optional.of(initialMovement));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Logs para verificar os valores
        System.out.println("Antes da reversão: " + product.getQuantity()); // Deve ser 60

        // Act - Chamando o método que atualiza a movimentação
        StockMovement result = stockMovementService.updateStockMovement(updateDto);

        // Logs para verificar os valores após a reversão e a nova movimentação
        System.out.println("Depois da reversão: " + product.getQuantity()); // Deve voltar para 50
        System.out.println("Depois de aplicar novo movimento: " + product.getQuantity()); // Deve ser 35

        // Assert - Verificando se os valores finais estão corretos
        assertNotNull(result);
        assertEquals(15, result.getQuantity());
        assertEquals(MovementType.SAIDA, result.getMovementType());
        assertEquals("Nova Observação", result.getObservation());
        assertEquals(35, product.getQuantity()); // Verificando se o estoque final está correto

        // Garantindo que os métodos do repositório foram chamados corretamente
        verify(productRepository, times(2)).save(product);
        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }
*/

}