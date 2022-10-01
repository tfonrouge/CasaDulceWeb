package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.apiLib.AppScope
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewHome
import com.fonrouge.remoteScreen.config.ConfigViewCasaDulceHome
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderHdr
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListDeliveryHdr
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListInventoryItm
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
                        dropDown(
                            text = "Customer Orders",
                            elements = listOf(
                                ConfigViewListCustomerOrderHdr.labelUrl
                            ),
                        )
                        dropDown(
                            text = "Delivery Orders",
                            elements = listOf(
                                ConfigViewListDeliveryHdr.labelUrl
                            ),

//                                forNavbar = true
                        )
                        dropDown(
                            text = "Catalog",
                            elements = listOf(
                                ConfigViewListInventoryItm.labelUrl,
                                ConfigViewListCustomerItm.labelUrl,
                            ),
                            icon = "fas fa-rep",
//                                forNavbar = true
                        )
                        dropDown(
                            text = "Price Checker",
                            icon = "fas barcode-read",
                            elements = listOf(
                                ConfigViewPriceChecker.labelUrl,
                            ),
//                                forNavbar = true
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