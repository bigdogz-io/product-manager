package com.bigdogz.productmanager.service

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum class ProductType {
    BEER, WINE, WHISKEY
}

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String? = null,
    val productType: ProductType? = null,
    val abv: String? = null,
    val company: String? = null
)
