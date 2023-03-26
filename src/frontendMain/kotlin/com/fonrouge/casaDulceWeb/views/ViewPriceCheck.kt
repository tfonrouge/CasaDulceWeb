package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.layout.formColumn
import com.fonrouge.fsLib.layout.formRow
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.View
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewPriceChecker
import com.fonrouge.casaDulceWeb.model.InventoryItm
import com.fonrouge.casaDulceWeb.model.ModelDataItemService
import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.number.spinner
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
                                        ).bind(InventoryItm::casePrice)
                                        spinner(
                                            label = "Wholesale Price",
                                        ).bind(InventoryItm::wholesalePrice)
                                        spinner(
                                            label = "Regular Price:",
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
                                                ModelDataItemService.dataItemService.inventoryItmByUpc(barcode).item?.let { item ->
                                                    formPanel1.setData(item)
                                                    AppScope.launch {
                                                        image1.src = ModelDataItemService.getImageSrc(barcode = barcode)
                                                    }
                                                } ?: formPanel1.clearData()
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
                textBarcode.input.getElementJQuery()?.select()
            },
            1000,
        )
    }
}
