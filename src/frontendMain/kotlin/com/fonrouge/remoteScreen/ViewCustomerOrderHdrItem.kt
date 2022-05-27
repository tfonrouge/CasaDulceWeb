package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CasaDulceServiceManager
import com.fonrouge.remoteScreen.services.ICasaDulceService
import io.kvision.core.FlexDirection
import io.kvision.form.formPanel
import io.kvision.form.select.selectRemote
import io.kvision.form.text.text
import io.kvision.form.time.dateTime
import io.kvision.html.button
import io.kvision.navigo.Match
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Formatter
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulatorRemote
import io.kvision.utils.rem

class ViewCustomerOrderHdrItem(match: Match?) : FlexPanel(direction = FlexDirection.COLUMN) {

    init {
        marginLeft = 2.rem
        marginRight = 2.rem

        formPanel<CustomerOrderHdr> {
            flexPanel(direction = FlexDirection.ROW) {
                text(label = "Doc Id:")
                    .bind(key = CustomerOrderHdr::docId, required = true)
                dateTime(label = "Created:",format = "MMM DD, YYYY hh:mm a")
                    .bind(key = CustomerOrderHdr::created, required = true)
            }
        }

        selectRemote(
            label = "Customer:",
            serviceManager = CasaDulceServiceManager,
            function = ICasaDulceService::selectCustomerItm
        )

        tabulatorRemote(
            serviceManager = CasaDulceServiceManager,
            function = ICasaDulceService::customerOrderItmByHdrList,
            options = TabulatorOptions(
                columns = listOf(
                    ColumnDefinition(
                        title = "#",
                        formatter = Formatter.ROWNUM
                    ),
                    ColumnDefinition(
                        title = "Inv Item Name",
                        field = "inventoryItm.name"
                    ),
                    ColumnDefinition(
                        title = "Inv Item UPC",
                        field = InventoryItm::upc.name
                    ),
                    ColumnDefinition(
                        title = "Qty",
                        field = CustomerOrderItm::qty.name
                    ),
                    ColumnDefinition(
                        title = "Unit",
                        field = CustomerOrderItm::unit.name
                    ),
                )
            )
        )

        flexPanel(direction = FlexDirection.ROW) {
            button(text = "Create New Customer Order")
        }
    }
}
