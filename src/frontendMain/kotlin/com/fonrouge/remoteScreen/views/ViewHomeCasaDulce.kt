package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.ViewHome
import com.fonrouge.remoteScreen.config.ConfigViewCasaDulceHome
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderHdr
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListDeliveryHdr
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListInventoryItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListQuickbooksItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListShopifyItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewPriceChecker
import com.fonrouge.remoteScreen.model.Model
import com.fonrouge.remoteScreen.model.Model.user
import io.kvision.core.Container
import io.kvision.dropdown.dropDown
import io.kvision.html.Span
import io.kvision.html.div
import io.kvision.navbar.nav
import io.kvision.navbar.navLink
import io.kvision.navbar.navbar
import io.kvision.routing.routing
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
                                ConfigViewListShopifyItm.labelUrl,
                                ConfigViewListQuickbooksItm.labelUrl
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
