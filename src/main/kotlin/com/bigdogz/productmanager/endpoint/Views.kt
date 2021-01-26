package com.bigdogz.productmanager.endpoint

import com.bigdogz.productmanager.service.Product
import com.bigdogz.productmanager.service.ProductType

data class ProductView(
    val id: Long? = null,
    val name: String? = null,
    val type: ProductType? = null,
    val abv: String? = null,
    val company: String? = null
)

fun Product.toProductView() = ProductView(
    id = id,
    name = name,
    type = productType,
    abv = abv,
    company = company
)

fun List<Product>.toProductViewList(): List<ProductView> {
    val productViewList = ArrayList<ProductView>()
    this.forEach { product -> productViewList.add(
        product.toProductView()
    ) }
    return productViewList
}
