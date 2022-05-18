import com.fonrouge.remoteScreen.Product
import com.fonrouge.remoteScreen.services.ProductCatalogService

object ProductModel {

    private val productCatalogService = ProductCatalogService()

    suspend fun createProduct(product: Product) {
        productCatalogService.createProductWith(product)
    }

    suspend fun updateProduct(product: Product, fieldName: String) {
        productCatalogService.updateProduct(product, fieldName)
    }
}
