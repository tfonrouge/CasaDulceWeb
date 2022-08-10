package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.config.ConfigViewImpl
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListDeliveryHdr
import com.fonrouge.remoteScreen.model.DeliveryOrderItm
import com.fonrouge.remoteScreen.model.deliveryStatusList
import com.fonrouge.remoteScreen.services.DataListServiceManager
import com.fonrouge.remoteScreen.services.IDataListService
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.selectRemote
import io.kvision.form.select.simpleSelect
import io.kvision.form.spinner.spinner
import io.kvision.form.time.dateTime
import io.kvision.panel.flexPanel
import kotlin.js.Date

class ViewListDeliveryHdr (
    override var urlParams: UrlParams? = null
) : ViewItem<DeliveryOrderItm, String>(
    configView = ConfigViewListDeliveryHdr
) {
    override fun Container.pageItemBody(): FormPanel<DeliveryOrderItm> {
        val fPanel = formPanel {
            flexPanel(direction = FlexDirection.ROW, spacing = 20) {
                spinner(label = "Doc Id:", value = 0).bind(key = DeliveryOrderItm::numId, required = true)
                dateTime(label = "Created:", format = "MMM DD, YYYY hh:mm a", value = Date())
                    .bind(key = DeliveryOrderItm::created, required = true)
                simpleSelect(label = "Status", options = deliveryStatusList)
                    .bind(key = DeliveryOrderItm::status)
            }
            selectRemote(
                label = "Customer:",
                serviceManager = DataListServiceManager,
                function = IDataListService::deliverList,
            ).bind(key = DeliveryOrderItm::customerOrderItm_id, required = true)
        }

        fsTabulator(ConfigViewImpl.ConfigViewDeliverList.viewFunc(null).apply {
            masterViewItem = this@ViewListDeliveryHdr
        })

        return fPanel
    }
}
