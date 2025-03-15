package com.br.api_controle_estoque.service;

import com.br.api_controle_estoque.DTO.Request.StockMovementRequestDto;
import com.br.api_controle_estoque.exceptions.NotFoundException;
import com.br.api_controle_estoque.model.Enum.MovementType;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.StockMovement;
import com.br.api_controle_estoque.model.Supplier;
import com.br.api_controle_estoque.repository.ProductRepository;
import com.br.api_controle_estoque.repository.StockMovementRepository;
import com.br.api_controle_estoque.repository.SupplierRepository;
import com.br.api_controle_estoque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    public List<StockMovement> listStockMovement(){
        return stockMovementRepository.findAll();
    }



    // Método para excluir o que a movimentação tinha retirado ou colocado no estoque
    private void revertStockMovement(Product product, StockMovement stockMovement){
        if (stockMovement.getMovementType() == MovementType.ENTRADA){
            product.setQuantity_stock(product.getQuantity_stock() - stockMovement.getQuantity());
        } else if (stockMovement.getMovementType() == MovementType.SAIDA) {
            product.setQuantity_stock(product.getQuantity_stock() + stockMovement.getQuantity());
        }

    }

    //Método para atualizar a tabela de produtos
    private void updateProductStock(Product product, StockMovement stockMovement) {
        if (stockMovement.getMovementType() == MovementType.ENTRADA) {
            product.setQuantity_stock(product.getQuantity_stock() + stockMovement.getQuantity());
        } else if (stockMovement.getMovementType() == MovementType.SAIDA) {
            if (product.getQuantity_stock() < stockMovement.getQuantity()) {
                throw new RuntimeException("Quantidade insuficiente em estoque");
            } else {
                product.setQuantity_stock(product.getQuantity_stock() - stockMovement.getQuantity());
            }
        }
    }

    public StockMovement createStockMovement(StockMovementRequestDto requestDto) {



        Product product = productRepository.findById(requestDto.productId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        Supplier supplier = null;
        if (requestDto.movementType() != MovementType.SAIDA) {
            supplier = supplierRepository.findById(requestDto.supplierId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        }


        if (requestDto.movementType() == MovementType.SAIDA && product.getQuantity_stock() < requestDto.quantity()) {
            throw new RuntimeException("Quantidade insuficiente em estoque para essa saída");
        }
        StockMovement stockMovement = new StockMovement();
        stockMovement.setProduct(product);
        stockMovement.setMovementType(requestDto.movementType());
        stockMovement.setMovement_date(LocalDateTime.now());
        stockMovement.setQuantity(requestDto.quantity());
        stockMovement.setObservation(requestDto.observation());
        stockMovement.setPrice(requestDto.price());
        if (supplier != null) {
            stockMovement.setSupplier(supplier);
        }
        // Lógica para atualizar na tabela de produtos
        updateProductStock(product, stockMovement);


        productRepository.save(product);

        return stockMovementRepository.save(stockMovement);
    }

    public StockMovement updateStockMovement(Long id,StockMovementRequestDto updateDto){
        StockMovement existingMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        Supplier supplier = null;
        if (updateDto.movementType() != MovementType.SAIDA) {
            supplier = supplierRepository.findById(updateDto.supplierId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        }


        Product product = existingMovement.getProduct();


        revertStockMovement(product, existingMovement);
        productRepository.save(product);

        existingMovement.setMovementType(updateDto.movementType());
        existingMovement.setQuantity(updateDto.quantity());
        existingMovement.setObservation(updateDto.observation());
        existingMovement.setSupplier(supplier);
        existingMovement.setPrice(updateDto.price());
        existingMovement.setMovement_date(LocalDateTime.now());
        if (supplier != null){
            existingMovement.setSupplier(supplier);
        }

        updateProductStock(product, existingMovement);

        productRepository.save(product);

        return stockMovementRepository.save(existingMovement);
    }

    public StockMovement searchStockMovement(Long id){

        return stockMovementRepository.findById(id).orElseThrow( () -> new NotFoundException("Nenhuma movimentação encontrada com esse id"));
    }

    public void deleteStockMovement(Long id){
        StockMovement existingMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        Product product = existingMovement.getProduct();

        revertStockMovement(product, existingMovement);
        productRepository.save(product);

        stockMovementRepository.deleteById(id);
    }
}
