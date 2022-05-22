package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.IInventoryItmService
import com.fonrouge.remoteScreen.services.InventoryItmService
import com.fonrouge.remoteScreen.services.InventoryItmServiceManager
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

class ViewProductCatalog : FlexPanel(direction = FlexDirection.COLUMN) {

    var tabRemote: TabulatorRemote<InventoryItm, InventoryItmService>

    companion object {
        var timerHandle: Int? = null
        var editing = false
    }

    init {

        setDragDropData("text/plain", "element")

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
//            types = setOf(TableType.STRIPED, TableType.HOVER, TableType.BORDERED),
            options = TabulatorOptions(
                layout = Layout.FITCOLUMNS,
                pagination = true,
                paginationMode = PaginationMode.REMOTE,
                paginationSize = 10,
                paginationSizeSelector = arrayOf(10, 20, 30, 40, 50, 100),
                filterMode = FilterMode.REMOTE,
                sortMode = SortMode.REMOTE,
                dataLoader = false,
                columns = listOf(
                    ColumnDefinition(
                        title = InventoryItm::_id.name,
                        field = InventoryItm::_id.name,
                        headerFilter = Editor.NUMBER
                    ),
                    ColumnDefinition(
                        title = InventoryItm::name.name,
                        field = InventoryItm::name.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = InventoryItm::size.name,
                        field = InventoryItm::size.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = InventoryItm::upc.name,
                        field = InventoryItm::upc.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = InventoryItm::departmentName.name,
                        field = InventoryItm::departmentName.name,
                        headerFilter = Editor.INPUT
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
            button(text = "Upload Product Catalog").onClick {
                val modal = UploadCatalog()
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

//        timerHandle = window.setInterval(
//            handler = {
//                if (!editing) {
//                    tabRemote.reload()
//                }
//            }, timeout = 1000
//        )
    }
}
