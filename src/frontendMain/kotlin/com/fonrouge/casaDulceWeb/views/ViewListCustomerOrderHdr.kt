package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderHdr
import com.fonrouge.casaDulceWeb.model.CustomerItm
import com.fonrouge.casaDulceWeb.model.CustomerOrderHdr
import com.fonrouge.casaDulceWeb.services.DataListService
import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Editor
import io.kvision.tabulator.Formatter

class ViewListCustomerOrderHdr(
    override var urlParams: UrlParams?,
) : ViewList<CustomerOrderHdr, DataListService, OId<CustomerOrderHdr>>(
    configView = ConfigViewListCustomerOrderHdr,
) {

    override val columnDefinitionList: List<ColumnDefinition<CustomerOrderHdr>> = listOf(
        ColumnDefinition(
            title = "",
            formatter = Formatter.ROWSELECTION
        ),
        ColumnDefinition(
            title = CustomerOrderHdr::numId.name,
            field = CustomerOrderHdr::numId.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = CustomerOrderHdr::created.name,
            field = CustomerOrderHdr::created.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = CustomerItm::firstName.name,
            field = "customerItm.firstName"
        ),
        ColumnDefinition(
            title = CustomerItm::lastName.name,
            field = "customerItm.lastName"
        ),
        ColumnDefinition(
            title = CustomerOrderHdr::status.name,
//                        field = CustomerOrderHdr::statusLabel.name, // this need to have property declared as var
            formatterComponentFunction = { _, _, data -> Span(data.statusLabel) }
        ),
    )

    override fun Container.pageListBody() {
        fsTabulator(this@ViewListCustomerOrderHdr)
    }
}
