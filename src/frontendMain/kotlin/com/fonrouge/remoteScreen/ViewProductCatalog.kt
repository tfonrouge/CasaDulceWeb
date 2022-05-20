package com.fonrouge.remoteScreen

import ProductModel
import com.fonrouge.remoteScreen.services.IInventoryItmService
import com.fonrouge.remoteScreen.services.InventoryItmService
import com.fonrouge.remoteScreen.services.InventoryItmServiceManager
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.InputSize
import io.kvision.form.spinner.Spinner
import io.kvision.form.spinner.SpinnerInput
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

    var tabRemote: TabulatorRemote<InventoryItm, InventoryItmService>

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
            serviceManager = InventoryItmServiceManager,
            function = IInventoryItmService::inventoryItmList,
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
                        title = InventoryItm::id.name,
//                        field = InventoryItm::id.name,
                        field = "_id",
                    ),
                    ColumnDefinition(
                        title = InventoryItm::code.name,
                        field = InventoryItm::code.name,
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
                        title = InventoryItm::description.name,
                        field = InventoryItm::description.name,
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
                        title = InventoryItm::unit.name,
                        field = InventoryItm::unit.name,
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
                    ColumnDefinition(
                        title = InventoryItm::stock.name,
                        field = InventoryItm::stock.name,
//                        headerFilter = Editor.INPUT,
                        editorComponentFunction = { _, _, success, _, data ->
                            SpinnerInput(value = data.stock).apply {
                                size = InputSize.SMALL
                                onEvent {
                                    change = {
                                        editing = false
                                        data.stock = (self.value ?: 0) as Int
                                        AppScope.launch {
                                            console.warn("valor de data =", data)
                                            ProductModel.updateProduct(data, "stock")
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
                val modal = DialogEditProduct()
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
