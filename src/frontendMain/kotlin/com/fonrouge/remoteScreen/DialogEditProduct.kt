package com.fonrouge.remoteScreen

import ProductModel
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.modal.Modal
import io.kvision.toast.Toast
import kotlinx.coroutines.launch

class DialogEditProduct(product: Product?) : Modal() {

    var form: FormPanel<Product>

    init {
        form = formPanel {

            text(label = "Code:")
                .bind(key = Product::code, required = true)

            text(label = "Description:")
                .bind(key = Product::description, required = true)

            text(label = "Unit:")
                .bind(key = Product::unit, required = true)
        }
        button(text = "Cancel").onClick {
            this@DialogEditProduct.hide()
        }
        button(text = "Add").onClick {
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
