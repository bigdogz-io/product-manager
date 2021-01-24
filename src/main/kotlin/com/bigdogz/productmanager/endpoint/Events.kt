package com.bigdogz.productmanager.endpoint

import com.bigdogz.productmanager.service.ProductType

class ProductCreated(
    val id: String,
    val name: String,
    val type: ProductType,
)
