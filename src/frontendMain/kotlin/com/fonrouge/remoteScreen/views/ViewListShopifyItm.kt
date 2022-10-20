package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.fieldName
import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.layout.fsTabulator2
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListShopifyItm
import com.fonrouge.remoteScreen.model.ShopifyItm
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.select.simpleSelect
import io.kvision.html.ButtonStyle
import io.kvision.html.ImageShape
import io.kvision.html.button
import io.kvision.html.image
import io.kvision.navbar.navForm
import io.kvision.navbar.navbar
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
            vPanel {
                fsTabulator2(viewList = this@ViewListShopifyItm)
                hPanel(justify = JustifyContent.CENTER) {
                    navbar {
                        navForm {
                            simpleSelect(
                                options = listOf("10", "20", "30", "50", "100", "200").map { it to it },
//                                value = pagination?.paginationSize.toString()
                            ).onEvent {
//                                change = { pagination?.paginationSize = self.value?.toIntOrNull() }
                            }
                            button(text = "", icon = "fas fa-chevron-left", style = ButtonStyle.OUTLINEPRIMARY)
                            button(text = "", icon = "fas fa-chevron-right", style = ButtonStyle.OUTLINEPRIMARY)
                        }
                    }
                }
            }
        }
    }
}
