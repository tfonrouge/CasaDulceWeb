package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerOrderHdrService
import com.fonrouge.remoteScreen.services.CustomerOrderHdrServiceManager
import com.fonrouge.remoteScreen.services.ICustomerOrderHdrService
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.tabulator.*

class ViewCustomerOrderHdrList : FlexPanel(direction = FlexDirection.COLUMN) {

    var tabRemote: TabulatorRemote<CustomerOrderHdr, CustomerOrderHdrService>

    companion object {
        var timerHandle: Int? = null
    }

    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY) {
            button("Home").onClick {
                routing.navigate("")
            }
            button("Product Catalog").onClick {
                routing.navigate("/${State.ProductCatalog}")
            }
            button("Customer Catalog").onClick {
                routing.navigate("/${State.CustomerCatalog}")
            }
        }

        tabRemote = tabulatorRemote(
            serviceManager = CustomerOrderHdrServiceManager,
            function = ICustomerOrderHdrService::customerOrderHdrList,
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
//                    ColumnDefinition(
//                        title = "#",
//                        formatter = Formatter.ROWNUM
//                    ),
                    ColumnDefinition(
                        title = CustomerOrderHdr::_id.name,
                        field = CustomerOrderHdr::_id.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = CustomerOrderHdr::docId.name,
                        field = CustomerOrderHdr::docId.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = CustomerOrderHdr::created.name,
                        field = CustomerOrderHdr::created.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = CustomerItm::company.name,
                        field = "customerItm.company",
                        headerFilter = Editor.INPUT
                    ),
                )
            )
        )

        flexPanel(direction = FlexDirection.ROW) {
            button(text = "Create new Customer Order").onClick {
                routing.navigate("${State.CustomerOrderHdrItem}?action=${ViewAction.create}")
            }
        }
    }
}
