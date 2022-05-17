package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.IProductCatalogService
import com.fonrouge.remoteScreen.services.ProductCatalogServiceManager
import io.kvision.core.FlexDirection
import io.kvision.panel.FlexPanel
import io.kvision.tabulator.*

class ViewProductCatalog : FlexPanel(direction = FlexDirection.COLUMN) {
    init {
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
