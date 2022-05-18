package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.IProductCatalogService
import com.fonrouge.remoteScreen.services.ProductCatalogService
import com.fonrouge.remoteScreen.services.ProductCatalogServiceManager
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.tabulator.*
import io.kvision.utils.event
import io.kvision.utils.px
import kotlinx.browser.window

class ViewProductCatalog : FlexPanel(direction = FlexDirection.COLUMN) {

    lateinit var tabRemote: TabulatorRemote<Product, ProductCatalogService>

    companion object {
        var timerHandle: Int? = null
    }

    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY) {
            button("Home").onClick {
                routing.navigate("")
            }
            button("Pending List").onClick {
                routing.navigate("/pendingList")
            }
            button("Customer Catalog").onClick {
                routing.navigate("/customerCatalog")
            }
            button("Edit List").onClick {
                routing.navigate("/editList")
            }
            marginBottom = 10.px
        }
        tabRemote = tabulatorRemote(
            serviceManager = ProductCatalogServiceManager,
            function = IProductCatalogService::products,
            options = TabulatorOptions(
                layout = Layout.FITCOLUMNS,
                pagination = true,
                paginationMode = PaginationMode.REMOTE,
                filterMode = FilterMode.REMOTE,
                sortMode = SortMode.REMOTE,
                dataLoader = false,
                columns = listOf(
                    ColumnDefinition(
                        title = Product::id.name,
                        field = Product::id.name
                    ),
                    ColumnDefinition(
                        title = Product::code.name,
                        field = Product::code.name
                    ),
                    ColumnDefinition(
                        title = Product::description.name,
                        field = Product::description.name
                    ),
                    ColumnDefinition(
                        title = Product::unit.name,
                        field = Product::unit.name
                    ),
                )
            )
        )

        flexPanel(direction = FlexDirection.ROW) {
            button(text = "Add Product").onClick {
                val modal = DialogEditProduct(EditMode.Create)
                modal.show()
            }
        }

        onEvent {
            event("show.bs.modal") {
                console.warn("focusing...")
            }
            hide = {
                console.warn("hidding...")
            }
        }

        window.setInterval(
            handler = {
                tabRemote.reload()
            }, timeout = 1000
        )
    }
}
