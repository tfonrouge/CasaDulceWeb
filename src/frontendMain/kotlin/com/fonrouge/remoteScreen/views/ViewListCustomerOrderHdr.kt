package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.layout.tabulatorCommon
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.services.DataListService
import com.fonrouge.remoteScreen.services.DataListServiceManager
import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.Span
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Editor

class ViewListCustomerOrderHdr(
    override var urlParams: UrlParams?,
) : ViewList<CustomerOrderHdr, DataListService>(
    configView = ConfigViewListCustomerOrderHdr,
    serverManager = DataListServiceManager,
    function = DataListService::customerOrderHdr
) {

    override val columnDefinitionList: List<ColumnDefinition<CustomerOrderHdr>> = listOf(
/*                    ColumnDefinition(
                        title = "#",
                        formatter = Formatter.ROWSELECTION
                    ),*/
        ColumnDefinition(
            title = "",
            formatterComponentFunction = { _, _, data ->
                Button(text = "", icon = "fas fa-edit").onClick {
//                                routing.navigate("/${State.CustomerOrderHdrItem}?action=${ViewAction.update}&_id=${data._id}")
                }
            }
        ),
        ColumnDefinition(
            title = "",
            formatterComponentFunction = { _, _, data ->
                Button(text = "", icon = "fas fa-trash-can", style = ButtonStyle.OUTLINEDANGER).onClick {
                }
            }
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
            title = CustomerItm::company.name,
            field = "customerItm.company",
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = CustomerOrderHdr::status.name,
//                        field = CustomerOrderHdr::statusLabel.name, // this need to have property declared as var
            formatterComponentFunction = { _, _, data -> Span(data.statusLabel) }
        ),
    )

    override fun pageListBody(container: Container) {
        container.tabulatorCommon(this, columnDefinitionList)
    }
}
