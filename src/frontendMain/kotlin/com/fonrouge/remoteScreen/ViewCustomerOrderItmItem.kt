package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CasaDulceServiceManager
import com.fonrouge.remoteScreen.services.ICasaDulceService
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.selectRemote
import io.kvision.form.spinner.spinner
import io.kvision.form.text.Text
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.modal.Dialog
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import kotlinx.coroutines.launch

class ViewCustomerOrderItmItem(action: String, customerOrderHdr_id: String, dialog: Dialog<CustomerOrderItm>?) :
    FlexPanel(direction = FlexDirection.COLUMN) {

    private var formPanel: FormPanel<CustomerOrderItm>
    private lateinit var textSize: Text

    init {

        formPanel = formPanel {
            selectRemote(
                serviceManager = CasaDulceServiceManager,
                function = ICasaDulceService::selectInventoryItm,
                label = "Inventory Item:"
            ) {
                onEvent {
                    change = {
                        AppScope.launch {
                            Model.getInventoryItm(self.value?.toIntOrNull()).let {
                                textSize.value = it.size
                            }
                        }
                    }
                }
            }.bind(key = CustomerOrderItm::inventoryItm_id, required = true)
            flexPanel(direction = FlexDirection.ROW, spacing = 20) {
                spinner(label = "Qty:")
                    .bind(key = CustomerOrderItm::qty, required = true) { spinner ->
                        spinner.value?.let {
                            it.toInt() > 0
                        }
                    }
                textSize = text(label = "Size:")
                    .bind(key = CustomerOrderItm::size, required = true)
            }
        }

        if (action == "new") {
            formPanel.setData(
                CustomerOrderItm(
                    _id = "",
                    customerOrderHdr_id = customerOrderHdr_id,
                    inventoryItm = null,
                    qty = 1,
                    size = ""
                )
            )
        }

        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.FLEXEND) {
            button(text = "Add Inventory Item to Customer Order").onClick {
                if (formPanel.validate()) {
                    AppScope.launch {
                        val o = formPanel.getData()
                        o.let {
                            Model.addCustomerOrderItm(it)
                            dialog?.setResult(it)
                        }
                    }
                }
            }
        }
    }
}
