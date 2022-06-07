package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerOrderItmService
import com.fonrouge.remoteScreen.services.CustomerOrderItmServiceManager
import com.fonrouge.remoteScreen.services.ICustomerOrderItmService
import io.kvision.core.FlexDirection
import io.kvision.core.onEvent
import io.kvision.form.spinner.SpinnerInput
import io.kvision.form.text.textInput
import io.kvision.html.Button
import io.kvision.html.ButtonSize
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.i18n.I18n
import io.kvision.modal.Alert
import io.kvision.modal.Confirm
import io.kvision.modal.Dialog
import io.kvision.modal.ModalSize
import io.kvision.panel.FlexPanel
import io.kvision.tabulator.*
import io.kvision.toolbar.buttonGroup
import io.kvision.toolbar.toolbar
import io.kvision.utils.rem
import kotlinx.coroutines.launch

class ViewCustomerOrderItmList(viewCustomerOrderHdrItem: ViewCustomerOrderHdrItem) :
    FlexPanel(direction = FlexDirection.COLUMN) {

    private lateinit var tabRemote: TabulatorRemote<CustomerOrderItm, CustomerOrderItmService>

    init {
        toolbar {
            marginTop = 1.rem
            marginBottom = 1.rem
            buttonGroup {
                button(text = "", icon = "fas fa-folder-plus", style = ButtonStyle.OUTLINEPRIMARY) {
                    size = ButtonSize.LARGE
                }.onClick {
                    val dialog = Dialog<CustomerOrderItm>(
                        caption = "New item",
                        size = ModalSize.LARGE,
                        centered = true
                    )
                    dialog.apply {
                        add(
                            ViewCustomerOrderItmItem(
                                action = ViewAction.create,
                                customerOrderHdr_id = viewCustomerOrderHdrItem.customerOrderHdr._id,
                                dialog = this
                            )
                        )
                    }
                    AppScope.launch {
                        dialog.getResult()?.let {
                            it.customerOrderHdr_id = viewCustomerOrderHdrItem.customerOrderHdr._id
                            tabRemote.reload()
                        }
                    }
                }
            }
        }

        tabRemote = tabulatorRemote(
            serviceManager = CustomerOrderItmServiceManager,
            function = ICustomerOrderItmService::customerOrderItmByHdrList,
            stateFunction = { viewCustomerOrderHdrItem.customerOrderHdr._id },
            options = TabulatorOptions(
                columns = listOf(
                    ColumnDefinition(
                        title = "#",
                        formatter = Formatter.ROWSELECTION
                    ),
                    ColumnDefinition(
                        title = "",
                        formatterComponentFunction = { cell, onRendered, data ->
                            Button(text = "", icon = "fas fa-edit", style = ButtonStyle.OUTLINESUCCESS)
                        }
                    ),
                    ColumnDefinition(
                        title = "",
                        formatterComponentFunction = { cell, onRendered, data ->
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
                                            if (ModelCustomerOrderItm.deleteCustomerOrderItm(_id)) {
                                                tabRemote.reload()
                                                Alert.show(I18n.tr("Result"), I18n.tr("Item deleted ok"))
                                            } else {
                                                Alert.show(I18n.tr("Result"), I18n.tr("Item NOT deleted"))
                                            }
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
                                                ModelCustomerOrderItm.updateFieldQty(data._id, qty.toInt())
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
                            textInput(value = data.size).apply {
                                onEvent {
                                    blur = {
                                        self.value?.let { size ->
                                            AppScope.launch {
                                                ModelCustomerOrderItm.updateFieldSize(data._id, size.toString())
                                                success(self.value)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    ),
                )
            )
        )
    }
}
