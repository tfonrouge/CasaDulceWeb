package com.fonrouge.remoteScreen

import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing

class ViewHome : FlexPanel(
    direction = FlexDirection.COLUMN,
) {
    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY) {
            button("Pending List").onClick {
                routing.navigate("/pendingList")
            }
            button("Product Catalog").onClick {
                routing.navigate("/productCatalog")
            }
            button("Customer Catalog").onClick {
                routing.navigate("/customerCatalog")
            }
            button("Edit List").onClick {
                routing.navigate("/editList")
            }
        }
    }
}
