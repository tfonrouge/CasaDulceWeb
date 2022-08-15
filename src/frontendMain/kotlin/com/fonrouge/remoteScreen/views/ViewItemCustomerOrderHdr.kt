package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewItemCustomerOrderHdr
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.customerOrderHdrStatusList
import com.fonrouge.remoteScreen.services.ISelectService
import com.fonrouge.remoteScreen.services.SelectServiceManager
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

class ViewItemCustomerOrderHdr(
    override var urlParams: UrlParams? = null
) : ViewItem<CustomerOrderHdr, String>(
    configView = ConfigViewItemCustomerOrderHdr,
) {
    override fun Container.pageItemBody(): FormPanel<CustomerOrderHdr> {
        val fPanel = formPanel {
            flexPanel(direction = FlexDirection.ROW, spacing = 20) {
                spinner(label = "Doc Id:", value = 0).bind(key = CustomerOrderHdr::numId, required = true)
                dateTime(label = "Created:", format = "MMM DD, YYYY hh:mm a", value = Date())
                    .bind(key = CustomerOrderHdr::created, required = true)
                simpleSelect(label = "Status", options = customerOrderHdrStatusList)
                    .bind(key = CustomerOrderHdr::status)
            }
            selectRemote(
                label = "Customer:",
                serviceManager = SelectServiceManager,
                function = ISelectService::customerItm,
            ).bind(key = CustomerOrderHdr::customerItm_id, required = true)
        }

        fsTabulator(
            configViewList = ConfigViewListCustomerOrderItm,
            masterViewItem = this@ViewItemCustomerOrderHdr
        )

        return fPanel
    }
}
