package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.apiLib.AppScope
import com.fonrouge.fsLib.layout.formColumn
import com.fonrouge.fsLib.layout.formRow
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.fsLib.view.View
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewPriceChecker
import com.fonrouge.remoteScreen.model.InventoryItm
import com.fonrouge.remoteScreen.model.ModelDataItemService
import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.spinner.spinner
import io.kvision.form.text.Text
import io.kvision.form.text.text
import io.kvision.html.*
import io.kvision.panel.flexPanel
import io.kvision.utils.em
import io.kvision.utils.vw
import kotlinx.browser.window
import kotlinx.coroutines.launch

class ViewPriceCheck(
    override var urlParams: UrlParams?
) : View(
    configView = ConfigViewPriceChecker
) {

    lateinit var image1: Image
    lateinit var formPanel1: FormPanel<InventoryItm>
    lateinit var textBarcode: Text
    override fun Container.displayPage() {
        div(className = "showItem") {
            div(className = "content") {
                formRow {
                    formColumn(6) {
                        image1 = image(src = "Logotipo-Casa-Dulce2.jpeg", shape = ImageShape.THUMBNAIL) {
                            width = 50.vw
                        }
                    }
                    formColumn(6) {
                        flexPanel(direction = FlexDirection.COLUMN, alignItems = AlignItems.CENTER) {
                            formPanel1 = formPanel(type = null) {
                                formRow {
                                    formColumn(12) {
                                        fontSize = 2.em
                                        text(label = "Name:").bind(InventoryItm::name)
                                        text(label = "Size:").bind(InventoryItm::size)
                                        text(label = "Upc:").bind(InventoryItm::upc)
                                        text(label = "Dept:").bind(InventoryItm::departmentName)
                                        spinner(
                                            label = "Case Price",
                                            decimalSeparator = "."
                                        ).bind(InventoryItm::casePrice)
                                        spinner(
                                            label = "Wholesale Price",
                                            decimalSeparator = "."
                                        ).bind(InventoryItm::wholesalePrice)
                                        spinner(
                                            label = "Regular Price:",
                                            decimalSeparator = "."
                                        ).bind(InventoryItm::price)
                                    }
                                }
                            }
                            label("Barcode:")
                            textBarcode = text {
                                onEvent {
                                    change = { event ->
                                        self.value?.let { barcode ->
                                            AppScope.launch {
//                                                textBarcode.input.getElementJQuery()?.select()
                                                textBarcode.input.getElementJQuery()?.select()
                                                val item = ModelDataItemService.dataItemService.inventoryItm(
                                                    _id = barcode,
                                                    state = StateItem(
                                                        crudAction = CrudAction.Read,
                                                        callType = StateItem.CallType.Query
                                                    )
                                                ).item
                                                if (item != null) {
                                                    formPanel1.setData(item)
                                                } else {
                                                    formPanel1.clearData()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        window.setInterval(
            {
                textBarcode.focus()
            },
            1000,
        )
    }
}
