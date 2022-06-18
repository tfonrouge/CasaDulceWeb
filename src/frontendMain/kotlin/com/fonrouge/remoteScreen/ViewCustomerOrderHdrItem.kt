package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerItmService
import com.fonrouge.remoteScreen.services.CustomerItmServiceManager
import com.fonrouge.remoteScreen.services.ICustomerItmService
import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.SelectRemote
import io.kvision.form.select.selectRemote
import io.kvision.form.select.simpleSelect
import io.kvision.form.spinner.spinner
import io.kvision.form.time.dateTime
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.navigo.Match
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.toast.Toast
import io.kvision.utils.px
import io.kvision.utils.rem
import kotlinx.browser.window
import kotlinx.coroutines.launch

class ViewCustomerOrderHdrItem(match: Match?) : FlexPanel(direction = FlexDirection.COLUMN) {

    private var formPanel: FormPanel<CustomerOrderHdr>
    private lateinit var selectCustomer: SelectRemote<CustomerItmService>
    internal lateinit var customerOrderHdr: CustomerOrderHdr

    init {

        hide()

        marginLeft = 2.rem
        marginRight = 2.rem

        formPanel = formPanel {
            flexPanel(direction = FlexDirection.ROW, spacing = 20) {
                spinner(label = "Doc Id:").bind(key = CustomerOrderHdr::docId, required = true)
                dateTime(label = "Created:", format = "MMM DD, YYYY hh:mm a")
                    .bind(key = CustomerOrderHdr::created, required = true)

                simpleSelect(label = "Status", options = customerOrderHdrStatusList)
                    .bind(key = CustomerOrderHdr::status)

            }
            selectCustomer = selectRemote(
                label = "Customer:",
                serviceManager = CustomerItmServiceManager,
                function = ICustomerItmService::selectCustomerItm,
                stateFunction = { "JuanaLaCubana" },
//                preload = true
            ).bind(key = CustomerOrderHdr::customerItm_id, required = true)
        }

        div {
            marginTop = 1.rem
            borderBottom = Border(width = 2.px, style = BorderStyle.DOUBLE, color = Color("blue"))
        }

        add(ViewCustomerOrderItmList(this))

        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEBETWEEN) {
            button(text = "Submit and create New Customer Order").onClick {
                if (formPanel.validate()) {
                    AppScope.launch {
                        val customerOrderHdr1 = formPanel.getData()
                        ModelCustomerOrderHdr.updateCustomerOrderHdr(customerOrderHdr1)
                        routing.navigate("/${State.CustomerOrderHdrList}")
                    }
                }
                Toast.success("Successfully Created")
            }
        }
        AppScope.launch {
            when (match?.action) {
                ViewAction.create -> {
                    ModelCustomerOrderHdr.createNewCustomerOrderHdr().let {
                        customerOrderHdr = it
                        formPanel.setData(customerOrderHdr)
                        this@ViewCustomerOrderHdrItem.show()
                    }
                }
                ViewAction.update -> {
                    customerOrderHdr = ModelCustomerOrderHdr.customerOrderHdrItem(match.params["_id"] as String)
                    formPanel.setData(customerOrderHdr)
                    this@ViewCustomerOrderHdrItem.show()
                    window.setTimeout(
                        handler = {
                            selectCustomer.buttonLabel = customerOrderHdr.customerItm?.company ?: "?"
                        },
                        timeout = 500
                    )
                }
                null -> {}
            }
        }
    }
}

var SelectRemote<*>.buttonLabel: String
    get() {
        val button = input.getElementJQuery()?.next()
        return button?.prop("title").toString()
    }
    set(value) {
        val button = input.getElementJQuery()?.next()
        button?.removeClass("bs-placeholder")
        button?.prop("title", value)
        button?.find(".filter-option-inner-inner")?.html(value)
    }
