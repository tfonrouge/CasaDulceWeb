package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CasaDulceService
import com.fonrouge.remoteScreen.services.CasaDulceServiceManager
import com.fonrouge.remoteScreen.services.ICasaDulceService
import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.SelectRemote
import io.kvision.form.select.selectRemote
import io.kvision.form.spinner.spinner
import io.kvision.form.time.dateTime
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.navigo.Match
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.utils.px
import io.kvision.utils.rem
import kotlinx.coroutines.launch

class ViewCustomerOrderHdrItem(match: Match?) : FlexPanel(direction = FlexDirection.COLUMN) {

    private var formPanel: FormPanel<CustomerOrderHdr>
    private lateinit var selectCustomer: SelectRemote<CasaDulceService>
    internal lateinit var customerOrderHdr: CustomerOrderHdr

    init {

        hide()

        console.warn("match =", match)

        marginLeft = 2.rem
        marginRight = 2.rem

        formPanel = formPanel {
            flexPanel(direction = FlexDirection.ROW) {
                spinner(label = "Doc Id:").bind(key = CustomerOrderHdr::docId, required = true)
                dateTime(label = "Created:", format = "MMM DD, YYYY hh:mm a").bind(
                    key = CustomerOrderHdr::created,
                    required = true
                )
            }
            selectCustomer = selectRemote(
                label = "Customer:",
                serviceManager = CasaDulceServiceManager,
                function = ICasaDulceService::selectCustomerItm,
            ).bindCustom(key = CustomerOrderHdr::customer, required = true)
        }

        div {
            marginTop = 1.rem
            borderBottom = Border(width = 2.px, style = BorderStyle.DOUBLE, color = Color("blue"))
        }

        add(ViewCustomerOrderItmList(this))

        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.FLEXEND) {
            button(text = "Submit and create New Customer Order").onClick {
                if (formPanel.validate()) {
                    AppScope.launch {
                        val json = formPanel.getSimpleJson()
                        Model.updateCustomerOrderHdr(customerOrderHdr._id, json)
                        this@ViewCustomerOrderHdrItem.hide()
                    }
                }
            }
        }

        if (match?.params["action"] == "${ViewAction.create}") {
            AppScope.launch {
                Model.createNewCustomerOrderHdr().let {
                    customerOrderHdr = it
                    formPanel.setData(customerOrderHdr)
                    this@ViewCustomerOrderHdrItem.show()
                }
            }
        }
    }
}
