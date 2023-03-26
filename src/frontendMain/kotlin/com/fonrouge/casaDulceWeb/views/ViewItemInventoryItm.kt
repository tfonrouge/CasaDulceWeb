package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.layout.formColumn
import com.fonrouge.fsLib.layout.formRow
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl
import com.fonrouge.casaDulceWeb.model.InventoryItm
import io.kvision.core.Container
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.text

class ViewItemInventoryItm(
    override var urlParams: UrlParams?
) : ViewItem<InventoryItm, String>(
    configView = ConfigViewImpl.ConfigViewItemInventoryItm,
) {
    override fun Container.pageItemBody(): FormPanel<InventoryItm> {
        return formPanel {
            formRow {
                formColumn(1) {
                    text(label = "Id:").bind(InventoryItm::_id, required = true)
                }
                formColumn(11) {
                    text(label = "Product Name:").bind(InventoryItm::name, required = true)
                }
            }
            formRow {
                formColumn(6) {
                    text(label = "Department:").bind(InventoryItm::departmentName)
                }
            }
            formRow {
                formColumn(6) {
                    text(label = "Size:").bind(InventoryItm::size)
                }
                formColumn(6) {
                    text(label = "Upc:").bind(InventoryItm::upc)
                }
            }
        }
    }
}
