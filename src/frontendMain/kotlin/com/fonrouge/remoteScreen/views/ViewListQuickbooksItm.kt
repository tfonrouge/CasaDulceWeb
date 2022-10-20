package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.fieldName
import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListQuickbooksItm
import com.fonrouge.remoteScreen.model.QuickbooksItm
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.html.ImageShape
import io.kvision.html.image
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.tabulator.ColumnDefinition
import io.kvision.utils.px

class ViewListQuickbooksItm(
    override var urlParams: UrlParams?
) : ViewList<QuickbooksItm, DataListService, String>(
    configView = ConfigViewListQuickbooksItm
) {
    override val columnDefinitionList: List<ColumnDefinition<QuickbooksItm>> = listOf(
        ColumnDefinition(
            title = "Id",
            field = fieldName(QuickbooksItm::_id)
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
            fsTabulator(this@ViewListQuickbooksItm)
        }
    }
}