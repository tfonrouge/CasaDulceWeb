package com.fonrouge.remoteScreen

import ProductModel
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.text
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.modal.Modal
import io.kvision.panel.flexPanel
import io.kvision.toast.Toast
import io.kvision.utils.rem
import kotlinx.coroutines.launch

class DialogEditProduct : Modal(caption = "New Product") {

    var form: FormPanel<InventoryItm>

    init {
        form = formPanel {

            text(label = "Code:")
                .bind(key = InventoryItm::code, required = true)

            text(label = "Description:")
                .bind(key = InventoryItm::description, required = true)

            text(label = "Unit:")
                .bind(key = InventoryItm::unit, required = true)
        }

        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.FLEXEND) {
            button(text = "Cancel", style = ButtonStyle.OUTLINEDANGER) {
                marginRight = 1.rem
            }.onClick {
                this@DialogEditProduct.hide()
            }
            button(text = "Create", style = ButtonStyle.OUTLINEPRIMARY).onClick {
                if (!form.validate()) {
                    Toast.warning("incomplete form...")
                } else {
                    this@DialogEditProduct.hide()
                    AppScope.launch {
                        try {
                            ProductModel.createProduct(form.getData())
                        } catch (e: Exception) {
                            Toast.error(e.message ?: "?")
                        }
                    }
                }
            }
        }
    }
}
