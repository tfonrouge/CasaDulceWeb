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
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.modal.Dialog
import io.kvision.modal.ModalSize
import io.kvision.navigo.Match
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.tabulator.*
import io.kvision.toolbar.buttonGroup
import io.kvision.toolbar.toolbar
import io.kvision.utils.px
import io.kvision.utils.rem
import kotlinx.coroutines.launch

class ViewCustomerOrderHdrItem(match: Match?) : FlexPanel(direction = FlexDirection.COLUMN) {

    private var formPanel: FormPanel<CustomerOrderHdr>
    private lateinit var selectCustomer: SelectRemote<CasaDulceService>
    private lateinit var tabRemote: TabulatorRemote<CustomerOrderItm, CasaDulceService>
    private lateinit var customerOrderHdr: CustomerOrderHdr

    init {

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
            ).bind(key = CustomerOrderHdr::customer_id)
        }

        div {
            marginTop = 1.rem
            borderBottom = Border(width = 2.px, style = BorderStyle.DOUBLE, color = Color("blue"))
        }

        toolbar {
            marginTop = 1.rem
            buttonGroup {
                button(text = "", icon = "fas fa-plus").onClick {
                    val dialog = Dialog<CustomerOrderItm>(
                        caption = "New item",
                        size = ModalSize.LARGE,
                        centered = true
                    )
                    dialog.apply {
                        add(
                            ViewCustomerOrderItmItem(
                                action = "new",
                                customerOrderHdr_id = customerOrderHdr._id,
                                dialog = this
                            )
                        )
                    }
                    AppScope.launch {
                        dialog.getResult()?.let {
                            it.customerOrderHdr_id = customerOrderHdr._id
                            console.warn("****", it)
                            tabRemote.reload()
                        }
                    }
                }
            }
        }

        tabRemote = tabulatorRemote(
            serviceManager = CasaDulceServiceManager,
            function = ICasaDulceService::customerOrderItmByHdrList,
            options = TabulatorOptions(
                columns = listOf(
                    ColumnDefinition(
                        title = "#",
                        formatter = Formatter.ROWSELECTION
                    ),
                    ColumnDefinition(
                        title = "",
                        formatterComponentFunction = { cell, onRendered, data ->
                            console.warn("DATA =", data.asDynamic()._id)
                            Button(text = "", icon = "fas fa-edit", style = ButtonStyle.OUTLINEPRIMARY)
                        }
                    ),
                    ColumnDefinition(
                        title = "#",
                        formatter = Formatter.ROWNUM
                    ),
                    ColumnDefinition(
                        title = "Inv Item Name",
                        field = "inventoryItm.name",
                    ),
                    ColumnDefinition(
                        title = "Inv Item UPC",
                        field = "inventoryItm.upc"
                    ),
                    ColumnDefinition(
                        title = "Qty",
                        field = CustomerOrderItm::qty.name
                    ),
                    ColumnDefinition(
                        title = "Size",
                        field = CustomerOrderItm::size.name
                    ),
                )
            )
        )

        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.FLEXEND) {
            button(text = "Submit and create New Customer Order").onClick {
                if (formPanel.validate()) {
                    this@ViewCustomerOrderHdrItem.hide()
                }
            }
        }

        if (match?.params["action"] == "new") {
            AppScope.launch {
                Model.createNewCustomerOrderHdr().let {
                    customerOrderHdr = it
                    formPanel.setData(it)
                }
            }
        }
    }
}
