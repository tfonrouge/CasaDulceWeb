package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.fieldName
import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListShopifyItm
import com.fonrouge.remoteScreen.model.ShopifyItm
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.html.ImageShape
import io.kvision.html.image
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.tabulator.ColumnDefinition
import io.kvision.utils.px

class ViewListShopifyItm(
    override var urlParams: UrlParams?
) : ViewList<ShopifyItm, DataListService, String>(
    configView = ConfigViewListShopifyItm
) {
    override val columnDefinitionList: List<ColumnDefinition<ShopifyItm>> = listOf(
        ColumnDefinition(
            title = "Id",
            field = fieldName(ShopifyItm::_id)
        )
    )

    override fun Container.pageListBody() {
        hPanel {
            vPanel {
                image(src = "logo1.png", shape = ImageShape.ROUNDED) {
                    width = 200.px
                    height = 200.px
                }
            }
            fsTabulator(this@ViewListShopifyItm)
        }
    }
}