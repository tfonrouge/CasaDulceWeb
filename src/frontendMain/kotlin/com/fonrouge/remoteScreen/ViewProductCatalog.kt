package com.fonrouge.remoteScreen

import ProductModel
import com.fonrouge.remoteScreen.services.IProductCatalogService
import com.fonrouge.remoteScreen.services.ProductCatalogService
import com.fonrouge.remoteScreen.services.ProductCatalogServiceManager
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.InputSize
import io.kvision.form.text.TextInput
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.tabulator.*
import io.kvision.utils.event
import io.kvision.utils.px
import kotlinx.browser.window
import kotlinx.coroutines.launch

class ViewProductCatalog : FlexPanel(direction = FlexDirection.COLUMN) {

    var tabRemote: TabulatorRemote<Product, ProductCatalogService>

    companion object {
        var timerHandle: Int? = null
        var editing = false
    }

    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY) {
            button("Home").onClick {
                routing.navigate("")
            }
            button("Customer Catalog").onClick {
                routing.navigate("/customerCatalog")
            }
            button("Customer Order List").onClick {
                routing.navigate("/customerOrderList")
            }
            marginBottom = 10.px
        }
        tabRemote = tabulatorRemote(
            serviceManager = ProductCatalogServiceManager,
            function = IProductCatalogService::products,
            types = setOf(TableType.STRIPED, TableType.HOVER, TableType.BORDERED),
            options = TabulatorOptions(
                layout = Layout.FITCOLUMNS,
                pagination = true,
                paginationMode = PaginationMode.REMOTE,
//                filterMode = FilterMode.REMOTE,
//                sortMode = SortMode.REMOTE,
                dataLoader = false,
                columns = listOf(
                    ColumnDefinition(
                        title = Product::id.name,
                        field = Product::id.name,
                    ),
                    ColumnDefinition(
                        title = Product::code.name,
                        field = Product::code.name,
                        headerFilter = Editor.INPUT,
                        editorComponentFunction = { _, _, success, _, data ->
                            TextInput(value = data.code).apply {
                                size = InputSize.SMALL
                                onEvent {
                                    change = {
                                        editing = false
                                        data.code = self.value ?: "***"
                                        AppScope.launch {
                                            ProductModel.updateProduct(data, "code")
                                            success(self.value)
                                        }
                                    }
                                }
                            }
                        }
                    ),
                    ColumnDefinition(
                        title = Product::description.name,
                        field = Product::description.name,
                        headerFilter = Editor.INPUT,
                        editorComponentFunction = { _, _, success, _, data ->
                            TextInput(value = data.description).apply {
                                size = InputSize.SMALL
                                onEvent {
                                    change = {
                                        editing = false
                                        data.description = self.value ?: "***"
                                        AppScope.launch {
                                            ProductModel.updateProduct(data, "description")
                                            success(self.value)
                                        }
                                    }
                                }
                            }
                        }
                    ),
                    ColumnDefinition(
                        title = Product::unit.name,
                        field = Product::unit.name,
                        headerFilter = Editor.INPUT,
                        editorComponentFunction = { _, _, success, _, data ->
                            TextInput(value = data.unit).apply {
                                size = InputSize.SMALL
                                onEvent {
                                    change = {
                                        editing = false
                                        data.unit = self.value ?: "***"
                                        AppScope.launch {
                                            ProductModel.updateProduct(data, "unit")
                                            success(self.value)
                                        }
                                    }
                                }
                            }
                        }
                    ),
                )
            )
        ) {
            onEvent {
                cellEditingTabulator = {
                    console.warn("cellEditingTabulator")
                    editing = true
                }
                cellEditedTabulator = {
                    console.warn("cellEditedTabulator")
                    editing = false
                }
                cellEditCancelledTabulator = {
                    console.warn("cellEditCancelledTabulator")
                    editing = false
                }
            }
        }

        flexPanel(direction = FlexDirection.ROW) {
            button(text = "Create Product").onClick {
                val modal = DialogEditProduct(null)
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

        timerHandle = window.setInterval(
            handler = {
                if (!editing) {
                    tabRemote.reload()
                }
            }, timeout = 1000
        )
    }
}
