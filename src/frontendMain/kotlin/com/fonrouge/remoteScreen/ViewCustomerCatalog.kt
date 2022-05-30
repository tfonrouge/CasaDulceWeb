package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerItmService
import com.fonrouge.remoteScreen.services.CustomerItmServiceManager
import com.fonrouge.remoteScreen.services.ICustomerItmService
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.tabulator.*
import kotlinx.browser.window
import kotlinx.coroutines.launch

class ViewCustomerCatalog : FlexPanel(direction = FlexDirection.COLUMN) {

    private var tabRemote: TabulatorRemote<CustomerItm, CustomerItmService>

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
            button("Customer Order List").onClick {
                routing.navigate("/${State.CustomerOrderHdrList}")
            }
        }

        tabRemote = tabulatorRemote(
            serviceManager = CustomerItmServiceManager,
            function = ICustomerItmService::customerItmList,
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
//                    ColumnDefinition(
//                        title = "#",
//                        formatter = Formatter.ROWNUM
//                    ),
                    ColumnDefinition(
                        title = CustomerItm::_id.name,
                        field = CustomerItm::_id.name,
                        headerFilter = Editor.NUMBER
                    ),
                    ColumnDefinition(
                        title = CustomerItm::company.name,
                        field = CustomerItm::company.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::lastName.name,
                        field = CustomerItm::lastName.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::firstName.name,
                        field = CustomerItm::firstName.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::street.name,
                        field = CustomerItm::street.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::city.name,
                        field = CustomerItm::city.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::state.name,
                        field = CustomerItm::state.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::zip.name,
                        field = CustomerItm::zip.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::phone1.name,
                        field = CustomerItm::phone1.name,
                        headerFilter = Editor.INPUT,
                    ),
                    ColumnDefinition(
                        title = CustomerItm::email.name,
                        field = CustomerItm::email.name,
                        headerFilter = Editor.INPUT,
                    ),
                )
            )
        )

        flexPanel(direction = FlexDirection.ROW) {
            button(text = "Upload Customer Catalog").onClick {
                val uploadCatalog = UploadCatalog(CatalogType.Customers)
                AppScope.launch {
                    uploadCatalog.getResult()
                    tabRemote.reload()
                }
            }
        }

        timerHandle = window.setInterval(
            handler = {
                val page = tabRemote.getPage()
                tabRemote.setPage(page)
            }, timeout = 5000
        )
    }
}
