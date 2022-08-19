package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.View
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewPriceChecker
import io.kvision.core.Container
import io.kvision.html.div

class ViewPriceCheck(
    override var urlParams: UrlParams?
) : View(
    configView = ConfigViewPriceChecker
) {
    override fun Container.displayPage() {
        div("PRICE CHECKER !")
    }
}
