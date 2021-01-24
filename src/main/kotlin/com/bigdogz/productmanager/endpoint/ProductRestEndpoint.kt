package com.bigdogz.productmanager.endpoint

import com.bigdogz.productmanager.config.Log
import com.bigdogz.productmanager.service.ProductCommandHandler
import com.bigdogz.productmanager.service.ProductQueryHandler
import com.bigdogz.productmanager.service.ProductType
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductRestEndpoint(var productCommandHandler: ProductCommandHandler, var productQueryHandler: ProductQueryHandler) {

    companion object : Log {}

    @PostMapping("/")
    fun createProduct(@RequestBody createProductCommand: CreateProduct): String {
        return productCommandHandler.createProduct(createProductCommand)
    }

    @GetMapping("/")
    fun getProducts(@RequestParam("productType", required = false, defaultValue = "WINE") productType: ProductType): List<ProductView> {
        return productQueryHandler.getProducts(productType)
    }

    @GetMapping("/{productId}")
    fun getProductById(@PathVariable("productId") productId: String): ProductView {
        return productQueryHandler.getProductById(productId)
    }
}
