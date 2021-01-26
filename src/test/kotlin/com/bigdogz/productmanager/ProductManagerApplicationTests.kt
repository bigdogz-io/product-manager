package com.bigdogz.productmanager

import com.bigdogz.productmanager.endpoint.CreateProduct
import com.bigdogz.productmanager.endpoint.ProductView
import com.bigdogz.productmanager.endpoint.toProductView
import com.bigdogz.productmanager.service.Product
import com.bigdogz.productmanager.service.ProductRepository
import com.bigdogz.productmanager.service.ProductType
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductManagerApplicationTests {

    companion object {
        @Container
        private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

    @Autowired lateinit var restTemplate: TestRestTemplate
    @Autowired lateinit var productRepository: ProductRepository

    @Test
    fun `when CreateProduct is received it should succeed`() {
        val createProductCommand = CreateProduct("Big Beer", ProductType.BEER, "12.5%", "Big Company")

        val entity = restTemplate.postForEntity<String>("/", createProductCommand)
        entity.statusCode shouldBe HttpStatus.OK
    }

    @Test
    fun `when query for Product by id should return with a valid ProductView`() {
        val product = Product(null,"Big Beer", ProductType.BEER, "12.5%", "Big Company")
        val testProduct = productRepository.save(product)

        val entity = restTemplate.getForEntity("/{productId}", ProductView::class.java, mapOf("productId" to testProduct.id))

        entity.statusCode shouldBe HttpStatus.OK
        assertThat(entity.body).isEqualTo(testProduct.toProductView())
    }

    @Test
    fun `when query for Products by type should return with a valid ProductView list`() {
        val testProduct1 = productRepository.save(Product(null,"Big Beer", ProductType.WINE, "12.5%", "Big Company"))
        productRepository.save(Product(null,"Big Beer", ProductType.WINE, "12.5%", "Big Company"))
        productRepository.save(Product(null,"Big Beer", ProductType.WINE, "12.5%", "Big Company"))

        val entity = restTemplate.getForEntity<List<ProductView>>("/?productType={productType}", mapOf("productType" to testProduct1.productType))

        entity.statusCode shouldBe HttpStatus.OK
        assertThat(entity.body?.size).isEqualTo(3)
    }
}
