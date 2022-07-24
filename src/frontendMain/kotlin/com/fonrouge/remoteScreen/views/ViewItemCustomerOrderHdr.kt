package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewItemCustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.customerOrderHdrStatusList
import com.fonrouge.remoteScreen.services.*
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.SelectRemote
import io.kvision.form.select.selectRemote
import io.kvision.form.select.simpleSelect
import io.kvision.form.spinner.spinner
import io.kvision.form.time.dateTime
import io.kvision.panel.flexPanel

class ViewItemCustomerOrderHdr(
    override var urlParams: UrlParams? = null
) : ViewItem<CustomerOrderHdr, DataItemService, String>(
    configView = ConfigViewItemCustomerOrderHdr,
    serverManager = DataItemServiceManager,
    function = IDataItemService::customerOrderHdr,
    klass = CustomerOrderHdr::class
) {

    private lateinit var selectCustomer: SelectRemote<SelectService>
    internal lateinit var customerOrderHdr: CustomerOrderHdr

    override fun Container.pageItemBody(): FormPanel<CustomerOrderHdr>? {
        return formPanel {
            flexPanel(direction = FlexDirection.ROW, spacing = 20) {
                spinner(label = "Doc Id:").bind(key = CustomerOrderHdr::numId, required = true)
                dateTime(label = "Created:", format = "MMM DD, YYYY hh:mm a")
                    .bind(key = CustomerOrderHdr::created, required = true)

                simpleSelect(label = "Status", options = customerOrderHdrStatusList)
                    .bind(key = CustomerOrderHdr::status)

            }
            selectCustomer = selectRemote(
                label = "Customer:",
                serviceManager = SelectServiceManager,
                function = ISelectService::customerItm,
                stateFunction = { "JuanaLaCubana" },
//                preload = true
            ).bind(key = CustomerOrderHdr::customerItm_id, required = true)
        }
    }
}
