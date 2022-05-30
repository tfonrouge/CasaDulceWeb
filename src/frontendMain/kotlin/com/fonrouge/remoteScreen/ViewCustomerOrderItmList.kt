package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerOrderItmService
import com.fonrouge.remoteScreen.services.CustomerOrderItmServiceManager
import com.fonrouge.remoteScreen.services.ICustomerOrderItmService
import io.kvision.core.FlexDirection
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.button
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
    }
}
