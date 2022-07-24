package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.apiLib.AppScope
import com.fonrouge.fsLib.layout.tabulatorCommon
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderItm
import com.fonrouge.remoteScreen.services.DataListService
import com.fonrouge.remoteScreen.services.DataListServiceManager
import io.kvision.core.Container
import io.kvision.core.onEvent
import io.kvision.form.spinner.SpinnerInput
import io.kvision.form.text.TextInput
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.i18n.I18n
import io.kvision.modal.Alert
import io.kvision.modal.Confirm
import io.kvision.tabulator.*
import kotlinx.coroutines.launch

class ViewListCustomerOrderItm(
    override var urlParams: UrlParams?
) : ViewList<CustomerOrderItm, DataListService>(
    configView = ConfigViewListCustomerOrderItm,
    serverManager = DataListServiceManager,
    function = DataListService::customerOrderItm
) {

    override val columnDefinitionList: List<ColumnDefinition<CustomerOrderItm>> = listOf(
        ColumnDefinition(
            title = "#",
            formatter = Formatter.ROWSELECTION
        ),
        ColumnDefinition(
            title = "",
            formatterComponentFunction = { _, _, data ->
                Button(text = "", icon = "fas fa-edit", style = ButtonStyle.OUTLINESUCCESS)
            }
        ),
        ColumnDefinition(
            title = "",
            formatterComponentFunction = { _, _, data ->
                Button(text = "", icon = "fas fa-trash-can", style = ButtonStyle.OUTLINEDANGER).onClick {
                    AppScope.launch {
                        Confirm.show(
                            caption = I18n.tr("Confirm Delete"),
                            text = "Are you sure to delete item id '${data._id}' item '${data.inventoryItm?.name}'",
                            yesTitle = I18n.tr("Yes"),
                            noTitle = I18n.tr("No"),
                            cancelTitle = I18n.tr("Cancel"),
                            noCallback = {
                                Alert.show(I18n.tr("Result"), I18n.tr("You pressed NO button."))
                            }
                        ) {
                            AppScope.launch {
                                val _id = data._id
                                console.warn("data._id", data._id)
//                                if (ModelCustomerOrderItm.deleteCustomerOrderItm(_id)) {
//                                    tabRemote.reload()
//                                    Alert.show(I18n.tr("Result"), I18n.tr("Item deleted ok"))
//                                } else {
//                                    Alert.show(I18n.tr("Result"), I18n.tr("Item NOT deleted"))
//                                }
                            }
                        }
                    }
                }
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
            field = CustomerOrderItm::qty.name,
            editorComponentFunction = { cell, onRendered, success, cancel, data ->
                console.warn("value", data.qty)
                SpinnerInput(value = data.qty).apply {
                    onEvent {
                        blur = {
                            self.value?.let { qty ->
                                AppScope.launch {
//                                    ModelCustomerOrderItm.updateFieldQty(data._id, qty.toInt())
                                    success(self.value)
                                }
                            }
                        }
                    }
                }
            }
        ),
        ColumnDefinition(
            title = "Size",
            field = CustomerOrderItm::size.name,
            editorComponentFunction = { cell, onRendered, success, cancel, data ->
                console.warn("value", data.size)
                TextInput(value = data.size).apply {
                    onEvent {
                        blur = {
                            self.value?.let { size ->
                                AppScope.launch {
//                                    ModelCustomerOrderItm.updateFieldSize(data._id, size.toString())
                                    success(self.value)
                                }
                            }
                        }
                    }
                }
            }
        ),
    )

    override fun pageListBody(container: Container) {
        container.tabulatorCommon(this, columnDefinitionList)
    }
}
