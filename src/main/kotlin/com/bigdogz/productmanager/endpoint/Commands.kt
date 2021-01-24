package com.bigdogz.productmanager.endpoint

import com.bigdogz.productmanager.service.ProductType

class CreateProduct(
    val name: String,
    val type: ProductType,
    val abv: String? = null,
    val company: String? = null
)
