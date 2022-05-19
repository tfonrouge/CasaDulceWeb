import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.services.InventoryItmService

object ProductModel {

    private val productCatalogService = InventoryItmService()

    suspend fun createProduct(inventoryItm: InventoryItm) {
        productCatalogService.createProductWith(inventoryItm)
    }

    suspend fun updateProduct(inventoryItm: InventoryItm, fieldName: String) {
        productCatalogService.updateProduct(inventoryItm, fieldName)
    }
}
