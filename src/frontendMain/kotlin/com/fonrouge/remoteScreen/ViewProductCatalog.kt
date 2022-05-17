package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.IProductCatalogService
import com.fonrouge.remoteScreen.services.ProductCatalogServiceManager
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.tabulator.*

class ViewProductCatalog : FlexPanel(direction = FlexDirection.COLUMN) {
    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY ) {
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
        }
        tabulatorRemote(
            serviceManager = ProductCatalogServiceManager,
            function = IProductCatalogService::products,
            options = TabulatorOptions(
                layout = Layout.FITCOLUMNS,
                paginationMode = PaginationMode.REMOTE,
                filterMode = FilterMode.REMOTE,
                sortMode = SortMode.REMOTE,
                columns = listOf(
                    ColumnDefinition(
                        title = Product::id.name,
                        field = Product::id.name
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
    }
}
