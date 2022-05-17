package com.fonrouge.remoteScreen

import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.modal.Modal
import io.kvision.toast.Toast

enum class EditMode {
    Create,
    Update
}

class DialogEditProduct(val editMode: EditMode) : Modal() {

    var form: FormPanel<Product>

    init {
        form = formPanel {

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
                val product = form.getData()
                console.warn("product =", product)

            }
        }
    }
}
