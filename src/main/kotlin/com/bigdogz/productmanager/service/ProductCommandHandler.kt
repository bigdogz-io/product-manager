package com.bigdogz.productmanager.service

import com.bigdogz.productmanager.config.Log
import com.bigdogz.productmanager.endpoint.CreateProduct
import org.springframework.stereotype.Service

@Service
class ProductCommandHandler(var productRepository: ProductRepository) {

    companion object : Log {}

    fun createProduct(createProductCommand: CreateProduct): String {
        logger().debug("create.product={}", createProductCommand)
        val product = productRepository.save(
            Product(
                null,
                createProductCommand.name,
                createProductCommand.type,
                createProductCommand.abv,
                createProductCommand.company
            )
        )

        return product.id!!
    }
}
