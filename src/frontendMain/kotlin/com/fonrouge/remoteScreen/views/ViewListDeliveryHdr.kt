package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListDeliveryHdr
import com.fonrouge.remoteScreen.model.DeliveryOrderHdr
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Editor
import io.kvision.tabulator.Formatter

class ViewListDeliveryHdr(
    override var urlParams: UrlParams?,
) : ViewList<DeliveryOrderHdr, DataListService, String>(
    configView = ConfigViewListDeliveryHdr,
) {

    override val columnDefinitionList: List<ColumnDefinition<DeliveryOrderHdr>> = listOf(
        ColumnDefinition(
            title = "",
            formatter = Formatter.ROWSELECTION
        ),
        ColumnDefinition(
            title = "_id",
            field = "_id"
        ),
        ColumnDefinition(
            title = DeliveryOrderHdr::customerOrderItm_id.name,
            field = DeliveryOrderHdr::customerOrderItm_id.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = DeliveryOrderHdr::dateDelivered.name,
            field = DeliveryOrderHdr::dateDelivered.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = DeliveryOrderHdr::qtyDelivered.name,
            field = DeliveryOrderHdr::qtyDelivered.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = DeliveryOrderHdr::statusDeliver.name,
            formatterComponentFunction = { _, _, data -> Span(data.statusDeliver) }
        ),
    )

    override fun Container.pageListBody() {
        fsTabulator(this@ViewListDeliveryHdr)
    }
}
