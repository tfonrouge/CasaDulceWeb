package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.fieldName
import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListShopifyProduct
import com.fonrouge.remoteScreen.model.ShopifyProduct
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.core.FlexWrap
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.select.simpleSelect
import io.kvision.html.*
import io.kvision.navbar.navForm
import io.kvision.navbar.navbar
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.tabulator.ColumnDefinition
import io.kvision.utils.px

class ViewListShopifyItm(
    override var urlParams: UrlParams?
) : ViewList<ShopifyProduct, DataListService, Long>(
    configView = ConfigViewListShopifyProduct
) {
    override val columnDefinitionList: List<ColumnDefinition<ShopifyProduct>> = listOf(
        ColumnDefinition(
            title = "Id",
            field = fieldName(ShopifyProduct::_id),
            formatterComponentFunction = { _, _, data ->
                Span(data._id.toString())
            }
        ),
        ColumnDefinition(
            title = "Handle",
            field = fieldName(ShopifyProduct::handle),
        ),
        ColumnDefinition(
            title = "title",
            field = fieldName(ShopifyProduct::title)
        )
    )

    override fun Container.pageListBody() {
        hPanel(spacing = 10) {
            vPanel {
                image(src = "logo1.png", shape = ImageShape.ROUNDED) {
                    width = 200.px
                    height = 200.px
                }
            }
            vPanel(className = "flex-grow-1 bd-highlight") {
                fsTabulator(viewList = this@ViewListShopifyItm)
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
