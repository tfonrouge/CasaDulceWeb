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
            button("Product Catalog").onClick {
                routing.navigate("/${State.ProductCatalog}")
            }
            button("Customer Catalog").onClick {
                routing.navigate("/${State.CustomerCatalog}")
            }
            button("Customer Order List").onClick {
                routing.navigate("/${State.CustomerOrderHdrList}")
            }
        }
    }
}
