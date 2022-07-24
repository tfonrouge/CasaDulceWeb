package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.apiLib.AppScope
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewHome
import com.fonrouge.remoteScreen.config.ConfigViewCasaDulceHome
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListCustomerOrderHdr
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListInventoryItm
import com.fonrouge.remoteScreen.model.Model
import com.fonrouge.remoteScreen.model.Model.userProfile
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

    override fun displayPage(container: Container) {
        container.apply {

            routing.updatePageLinks()

            pageBanner()

            div().bind(userProfile) { userProfile ->
                if (!userProfile.name.isNullOrEmpty()) {
                    navbar {
                        nav {
                            dropDown(
                                text = "Customer Orders",
                                elements = listOf(
                                    ConfigViewListCustomerOrderHdr.labelUrl
                                ),
                                forNavbar = true
                            )
                            dropDown(
                                text = "Catalog",
                                elements = listOf(
                                    ConfigViewListInventoryItm.labelUrl,
                                    ConfigViewListCustomerItm.labelUrl,
                                ),
                                icon = "fas fa-rep",
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
}
