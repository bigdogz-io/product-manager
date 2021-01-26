package com.bigdogz.productmanager.service

import com.bigdogz.productmanager.endpoint.ProductView
import com.bigdogz.productmanager.endpoint.toProductView
import com.bigdogz.productmanager.endpoint.toProductViewList
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Slf4j
@Service
class ProductQueryHandler(var productRepository: ProductRepository) {

    fun getProductById(productId: Long): ProductView {
        val product = productRepository.findById(productId)
        if (product.isEmpty) {
            throw RuntimeException("ooops")
        }

        return product.get().toProductView()
    }

    fun getProducts(productType: ProductType): List<ProductView> {
        return productRepository.findByProductType(productType).toProductViewList()
    }
}




