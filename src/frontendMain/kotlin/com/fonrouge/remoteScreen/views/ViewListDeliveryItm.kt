package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewDeliverList
import com.fonrouge.remoteScreen.model.DeliveryOrderItm
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Editor
import io.kvision.tabulator.Formatter

class ViewListDeliveryItm(
    override var urlParams: UrlParams?,
) : ViewList<DeliveryOrderItm, DataListService, String>(
    configView = ConfigViewDeliverList,
) {
    override val columnDefinitionList: List<ColumnDefinition<DeliveryOrderItm>> = listOf(
        ColumnDefinition(
            title = "",
            formatter = Formatter.ROWSELECTION
        ),
        ColumnDefinition(
            title = "_id",
            field = "_id"
        ),
        ColumnDefinition(
            title = DeliveryOrderItm::_id.name,
            field = DeliveryOrderItm::_id.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = DeliveryOrderItm::dateDelivered.name,
            field = DeliveryOrderItm::dateDelivered.name,
            headerFilter = Editor.INPUT
        ),
        ColumnDefinition(
            title = DeliveryOrderItm::qtyDelivered.name,
            field = DeliveryOrderItm::qtyDelivered.name
        ),
        ColumnDefinition(
            title = DeliveryOrderItm::statusDeliver.name,
            field = DeliveryOrderItm::statusDeliver.name
        )
    )

    override fun Container.pageListBody() {
        fsTabulator(this@ViewListDeliveryItm)
    }

}
