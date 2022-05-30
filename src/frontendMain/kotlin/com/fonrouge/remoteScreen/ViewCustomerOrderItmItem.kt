package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.IInventoryItmService
import com.fonrouge.remoteScreen.services.InventoryItmServiceManager
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
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

object KSerializer1 : KSerializer<InventoryItm> {

    override fun deserialize(decoder: Decoder): InventoryItm {
        val _id = decoder.decodeInt()

        var inventoryItm = InventoryItm(0, "", "", "", "")
        AppScope.launch {
            console.warn(">>> getInventoryItm")
            inventoryItm = Model.getInventoryItm(_id)
            console.warn(">>> getInventoryItm", inventoryItm)
        }
        console.warn("returning from deserialize", inventoryItm)
        return inventoryItm
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SInventoryItm", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: InventoryItm) {
        encoder.encodeString(value = value._id.toString())
    }
}

class ViewCustomerOrderItmItem(action: ViewAction, customerOrderHdr_id: String, dialog: Dialog<CustomerOrderItm>?) :
    FlexPanel(direction = FlexDirection.COLUMN) {

    private var formPanel: FormPanel<CustomerOrderItm>
    private lateinit var textSize: Text

    init {

        val map: MutableMap<KClass<*>, KSerializer<*>> = mutableMapOf()
        map.set(key = InventoryItm::class, value = KSerializer1)

        formPanel = formPanel {
            selectRemote(
                serviceManager = InventoryItmServiceManager,
                function = IInventoryItmService::selectInventoryItm,
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
            }.bindCustom(key = CustomerOrderItm::inventoryItm, required = true)
//            }.bind(key = CustomerOrderItm::inventoryItm_id, required = true)
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
                        val j = formPanel.getDataJson()
                        console.warn("getDataJson =", j)
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
