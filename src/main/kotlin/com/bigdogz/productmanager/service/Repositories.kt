package com.bigdogz.productmanager.service

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByProductType(productType: ProductType): List<Product>
}
