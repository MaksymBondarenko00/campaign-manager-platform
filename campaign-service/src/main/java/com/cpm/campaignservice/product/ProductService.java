package com.cpm.campaignservice.product;

import com.cpm.campaignservice.clients.AccountClient;
import com.cpm.campaignservice.clients.UserClient;
import com.cpm.campaignservice.product.dto.CreateProductRequest;
import com.cpm.campaignservice.product.dto.UpdateProductRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    UserClient userClient;
    AccountClient accountClient;


    public Product createProduct(CreateProductRequest request) {
        return productRepository.save(
                productMapper.toEntity(request)
        );
    }

    public Product updateProduct(Long id, UpdateProductRequest request) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        updateIfNotNull(request.name(), product::setName);
        updateIfNotNull(request.description(), product::setDescription);
        updateIfNotNull(request.price(), product::setPrice);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findByAccountId(getCurrentAccountId());
    }


    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private Long getCurrentAccountId() {
        return accountClient.getCurrentAccountId(
                userClient.getCurrentUserId()
        );
    }
}
