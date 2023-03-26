package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.layout.formColumn
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewItemCustomerOrderItm
import com.fonrouge.casaDulceWeb.model.CustomerOrderItm
import com.fonrouge.casaDulceWeb.model.ModelDataItemService.dataItemService
import com.fonrouge.casaDulceWeb.services.ISelectService
import com.fonrouge.casaDulceWeb.services.SelectServiceManager
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.core.onEvent
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.number.spinner
import io.kvision.form.select.tomSelectRemote
import io.kvision.form.text.Text
import io.kvision.form.text.text
import io.kvision.panel.flexPanel
import kotlinx.coroutines.launch

class ViewItemCustomerOrderItm(
    override var urlParams: UrlParams?
) : ViewItem<CustomerOrderItm, OId<CustomerOrderItm>>(
    configView = ConfigViewItemCustomerOrderItm,
) {

    private lateinit var textSize: Text

    override fun Container.pageItemBody(): FormPanel<CustomerOrderItm> {
        return formPanel {
            formColumn(12) {
//                typeaheadRemote(
//                    label = "Inventory Item:",
//                    serviceManager = TypeaheadServiceManager,
//                    function = ITypeaheadService::inventoryItem,
//                    floating = true,
//                ).bind(key = CustomerOrderItm::inventoryItm_id)
//            }
                tomSelectRemote(
                    serviceManager = SelectServiceManager,
                    function = ISelectService::inventoryItm,
                    label = "Inventory Item:"
                ) {
                    onEvent {
                        change = {
                            self.value?.let { _id ->
                                AppScope.launch {
                                    dataItemService.inventoryItm(
                                        _id = _id,
                                        state = StateItem(
                                            crudAction = CrudAction.Read,
                                            callType = StateItem.CallType.Query
                                        )
                                    ).item?.let {
                                        textSize.value = it.size
                                    }
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
        }
    }
}