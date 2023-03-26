package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewItemCustomerOrderHdr
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderItm
import com.fonrouge.casaDulceWeb.model.CustomerOrderHdr
import com.fonrouge.casaDulceWeb.model.customerOrderHdrStatusList
import com.fonrouge.casaDulceWeb.services.ISelectService
import com.fonrouge.casaDulceWeb.services.SelectServiceManager
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.number.spinner
import io.kvision.form.select.select
import io.kvision.form.select.tomSelectRemote
import io.kvision.form.time.dateTime
import io.kvision.panel.flexPanel
import kotlin.js.Date

class ViewItemCustomerOrderHdr(
    override var urlParams: UrlParams? = null
) : ViewItem<CustomerOrderHdr, OId<CustomerOrderHdr>>(
    configView = ConfigViewItemCustomerOrderHdr,
) {
    override fun Container.pageItemBody(): FormPanel<CustomerOrderHdr> {
        val fPanel = formPanel {
            flexPanel(direction = FlexDirection.ROW, spacing = 20) {
                spinner(label = "Doc Id:", value = 0).bind(key = CustomerOrderHdr::numId, required = true)
                dateTime(label = "Created:", format = "MMM DD, YYYY hh:mm a", value = Date())
                    .bind(key = CustomerOrderHdr::created, required = true)
                select(label = "Status", options = customerOrderHdrStatusList)
                    .bind(key = CustomerOrderHdr::status)
            }
            tomSelectRemote(
                label = "Customer:",
                serviceManager = SelectServiceManager,
                function = ISelectService::customerItm,
            ).bindCustom(key = CustomerOrderHdr::customerItm_id, validator = {
                !it.value.isNullOrEmpty()
            })
        }

        fsTabulator(
            configViewList = ConfigViewListCustomerOrderItm,
            masterViewItem = this@ViewItemCustomerOrderHdr
        )

        return fPanel
    }
}
