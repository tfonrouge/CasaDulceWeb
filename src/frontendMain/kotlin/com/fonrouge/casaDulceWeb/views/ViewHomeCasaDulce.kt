package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.KVWebManager.routing
import com.fonrouge.fsLib.view.ViewHome
import com.fonrouge.casaDulceWeb.config.ConfigViewCasaDulceHome
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListCustomerItm
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderHdr
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListDeliveryHdr
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListInventoryItm
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListQuickbooksProduct
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListShopifyProduct
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewPriceChecker
import com.fonrouge.casaDulceWeb.model.Model
import com.fonrouge.casaDulceWeb.model.Model.user
import io.kvision.core.Container
import io.kvision.dropdown.dropDown
import io.kvision.html.Span
import io.kvision.html.div
import io.kvision.navbar.nav
import io.kvision.navbar.navLink
import io.kvision.navbar.navbar
import io.kvision.state.bind
import kotlinx.coroutines.launch

class ViewHomeCasaDulce(
    override var urlParams: UrlParams? = null
) : ViewHome(configView = ConfigViewCasaDulceHome) {

    init {
        AppScope.launch {
            Model.readProfile()
        }
    }

    override fun Container.displayPage() {

        routing.updatePageLinks()

        pageBanner()

        div().bind(user) { userProfile ->
            if (!userProfile.name.isNullOrEmpty()) {
                navbar {
                    nav(className = "navButtons") {
                        navLink(
                            label = "Customer Orders",
                            url = ConfigViewListCustomerOrderHdr.url
                        )
                        navLink(
                            label = "Delivery Orders",
                            url = ConfigViewListDeliveryHdr.url,
                        )
                        dropDown(
                            text = "Catalogs",
                            elements = listOf(
                                ConfigViewListInventoryItm.labelUrl,
                                ConfigViewListCustomerItm.labelUrl,
                            ),
                            icon = "fas fa-rep",
//                                forNavbar = true
                            forNavbar = true
                        )
                        navLink(
                            label = "Price Checker",
                            icon = "fas fa-barcode",
                            url = ConfigViewPriceChecker.url,
//                                forNavbar = true
                        )
                        dropDown(
                            text = "Shopify/Quickbooks",
                            icon = "fas fa-store",
                            elements = listOf(
                                ConfigViewListShopifyProduct.labelUrl,
                                ConfigViewListQuickbooksProduct.labelUrl
                            ),
                            forNavbar = true
                        )
                    }
                    nav(rightAlign = true) {
                        navLink("System", icon = "fab fa-windows")
                    }
                }
            } else {
                Span("")
            }
        }
    }
}
