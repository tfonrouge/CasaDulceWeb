package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderItm
import com.fonrouge.casaDulceWeb.model.CustomerOrderItm
import com.fonrouge.casaDulceWeb.model.ModelDataItemService
import com.fonrouge.casaDulceWeb.services.DataListService
import io.kvision.core.Container
import io.kvision.core.onEvent
import io.kvision.form.number.SpinnerInput
import io.kvision.form.text.TextInput
import io.kvision.remote.obj
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Editor
import io.kvision.tabulator.Formatter
import kotlinx.coroutines.launch

class ViewListCustomerOrderItm(
    override var urlParams: UrlParams?
) : ViewList<CustomerOrderItm, DataListService, OId<CustomerOrderItm>>(
    configView = ConfigViewListCustomerOrderItm,
) {

    override val columnDefinitionList: List<ColumnDefinition<CustomerOrderItm>> = listOf(
        ColumnDefinition(
            title = "#",
            formatter = Formatter.ROWSELECTION
        ),
        ColumnDefinition(
            title = "#",
            formatter = Formatter.ROWNUM
        ),
        ColumnDefinition(
            title = "Inv Item Name",
            field = "inventoryItm.name",
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "Inv Item UPC",
            field = "inventoryItm.upc",
            headerFilter = Editor.INPUT,
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
            headerFilter = Editor.INPUT,
            editorComponentFunction = { cell, onRendered, success, cancel, data ->
                console.warn("value", data.size)
                TextInput(value = data.size).apply {
                    onEvent {
                        blur = {
                            self.value?.let { size ->
                                AppScope.launch {
                                    val json = obj { }
                                    json[CustomerOrderItm::size.name] = size
                                    ModelDataItemService.updateField(
                                        _id = data._id,
                                        value = json
                                    )
                                    success(self.value)
                                }
                            }
                        }
                    }
                }
            }
        ),
    )

    override fun Container.pageListBody() {
        fsTabulator(this@ViewListCustomerOrderItm)
    }
}
