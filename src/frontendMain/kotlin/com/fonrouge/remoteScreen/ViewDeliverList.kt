package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.DeliveryService
import com.fonrouge.remoteScreen.services.DeliveryServiceManager
import com.fonrouge.remoteScreen.services.IDeliveryService
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.Span
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing
import io.kvision.tabulator.*

class ViewDeliverList : FlexPanel(direction = FlexDirection.COLUMN) {
    lateinit var tabRemote: TabulatorRemote<DeliveryOrderItm, DeliveryService>

    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY) {
            button("Home").onClick {
                routing.navigate("")
            }
            button("Product Catalog").onClick {
                routing.navigate("/${State.ProductCatalog}")
            }
            button("Customer Catalog").onClick {
                routing.navigate("/${State.CustomerCatalog}")
            }
        }

        tabRemote = tabulatorRemote(
            serviceManager = DeliveryServiceManager,
            function = IDeliveryService::DeliveryOrderItm,
            options = TabulatorOptions(
                layout = Layout.FITCOLUMNS,
                pagination = true,
                paginationMode = PaginationMode.REMOTE,
                paginationSize = 10,
                paginationSizeSelector = arrayOf(10, 20, 30, 40, 50, 100),
                filterMode = FilterMode.REMOTE,
                sortMode = SortMode.REMOTE,
                dataLoader = false,
                columns = listOf(
/*                    ColumnDefinition(
                        title = "#",
                        formatter = Formatter.ROWSELECTION
                    ),
                    ColumnDefinition(
                        title = "",
                        formatterComponentFunction = { _, _, data ->
                            Button(text = "", icon = "fas fa-edit")
                        }
                    ),
                    ColumnDefinition(
                        title = "",
                        formatterComponentFunction = { _, _, data ->
                            Button(text = "", icon = "fas fa-trash-can", style = ButtonStyle.OUTLINEDANGER).onClick {
                                AppScope.launch {
                                    Confirm.show(
                                        caption = I18n.tr("Confirm Delete"),
                                        text = "Are you sure to delete item id '${data._id}' docId '${data.docId}'",
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
                                            if (ModelCustomerOrderHdr.deleteCustomerOrderHdr(_id)) {
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

 */
                    ColumnDefinition(
                        title = DeliveryOrderItm::customerOrderItm.name,
                        field = DeliveryOrderItm::customerOrderItm.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = DeliveryOrderItm::customerOrderItm.name,
                        field = DeliveryOrderItm::customerOrderItm.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = DeliveryOrderItm::qtyDelivered.name,
                        field = DeliveryOrderItm::qtyDelivered.name,
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = DeliveryOrderItm::dateDelivered.name,
                        field = "DeliveryOrderItm.name",
                        headerFilter = Editor.INPUT
                    ),
                    ColumnDefinition(
                        title = DeliveryOrderItm::status.name,
//                        field = CustomerOrderHdr::statusLabel.name, // this need to have property declared as var
                        formatterComponentFunction = { _, _, data -> Span(data.statusDeliver) }
                    ),
                )
            )
        )
    }
}
