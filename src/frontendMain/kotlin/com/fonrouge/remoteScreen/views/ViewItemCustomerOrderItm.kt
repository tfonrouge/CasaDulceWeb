package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.apiLib.AppScope
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewItemCustomerOrderItm
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.services.*
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.core.onEvent
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.selectRemote
import io.kvision.form.spinner.spinner
import io.kvision.form.text.Text
import io.kvision.form.text.text
import io.kvision.panel.flexPanel
import kotlinx.coroutines.launch

class ViewItemCustomerOrderItm(
    override var urlParams: UrlParams?
) : ViewItem<CustomerOrderItm, DataItemService, String>(
    configView = ConfigViewItemCustomerOrderItm,
    serverManager = DataItemServiceManager,
    function = IDataItemService::customerOrderItm,
    klass = CustomerOrderItm::class
) {

    private lateinit var textSize: Text

    override fun Container.pageItemBody(): FormPanel<CustomerOrderItm> {
        return formPanel {
            selectRemote(
                serviceManager = SelectServiceManager,
                function = ISelectService::inventoryItm,
                label = "Inventory Item:"
            ) {
                onEvent {
                    change = {
                        AppScope.launch {
//                            ModelInventoryItm.getInventoryItm(self.value ?: "").let {
//                                textSize.value = it.size
//                            }
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
    }
}
