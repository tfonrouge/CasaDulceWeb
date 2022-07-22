package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.DeliverItemServiceManager
import com.fonrouge.remoteScreen.services.IDeliverItemService
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

class ViewDeliverItem (action: ViewAction, customerOrderHdr_id: String, dialog: Dialog<CustomerOrderItm>?) :
    FlexPanel(direction = FlexDirection.COLUMN){

    private var formPanel: FormPanel<CustomerOrderItm>
    private lateinit var textSize: Text

    init {

        formPanel = formPanel {
            selectRemote(
                serviceManager = DeliverItemServiceManager,
                function = IDeliverItemService::selectDeliverItem,
                label = "Delivery Item:"
            ) {
                onEvent {
                    change = {
                        AppScope.launch {
                            ModelDeliverItem.getDeliverItem(self.value ?: "").let {
                                textSize.value = it.size
                            }
                        }
                    }
                }
            }.bind(key = DeliveryOrderItm::_id, required = true)

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

        if (action == ViewAction.create) {
            formPanel.setData(
                CustomerOrderItm(
                    _id = "",
                    customerOrderHdr_id = customerOrderHdr_id,
                    inventoryItm_id = "",
                    qty = 1,
                    size = ""
                )
            )
        }

        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.FLEXEND) {
            button(text = "Add Inventory Item to Customer Order").onClick {
                if (formPanel.validate()) {
                    AppScope.launch {
                        formPanel.getData().let {
                            console.warn("adding...", it)
                            ModelDeliverOrderItem.addDeliveryOrderItem(it)
                            dialog?.setResult(it)
                        }
                    }
                }
            }
        }
    }
}
