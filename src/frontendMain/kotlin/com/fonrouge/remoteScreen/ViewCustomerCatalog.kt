package com.fonrouge.remoteScreen

import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.html.span
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.routing.routing

class ViewCustomerCatalog : FlexPanel(direction = FlexDirection.COLUMN) {
    init {
        flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.SPACEEVENLY) {
            button("Home").onClick {
                routing.navigate("")
            }
            button("Product Catalog").onClick {
                routing.navigate("/productCatalog")
            }
            button("Customer Order List").onClick {
                routing.navigate("/customerOrderList")
            }
        }
        span("Customer Catalog")
    }
}
