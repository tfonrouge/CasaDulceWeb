package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.fieldName
import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.remoteScreen.config.ConfigViewImpl.Companion.ConfigViewListShopifyProduct
import com.fonrouge.remoteScreen.model.ShopifyProduct
import com.fonrouge.remoteScreen.services.DataListService
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.select.simpleSelect
import io.kvision.html.*
import io.kvision.navbar.navForm
import io.kvision.navbar.navbar
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.js.Tabulator
import io.kvision.utils.px
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.w3c.dom.events.Event

class ViewListShopifyProduct(
    override var urlParams: UrlParams?
) : ViewList<ShopifyProduct, DataListService, Long>(
    configView = ConfigViewListShopifyProduct
) {
    private var tabSize = 10

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
        ),
        ColumnDefinition(
            title = "bodyHtml",
            field = fieldName(ShopifyProduct::bodyHtml)
        ),
        ColumnDefinition(
            title = "createdAt",
            field = fieldName(ShopifyProduct::createdAt)
        ),
        ColumnDefinition(
            title = "tags",
            field = fieldName(ShopifyProduct::tags)

        ),
        ColumnDefinition(
            title = "image",
            field = fieldName(ShopifyProduct::image),
            formatterComponentFunction = { _, _, data ->
                data.image?.src?.let { src ->
                    Image(src) {
                        width = 35.px
                        height = 35.px
                    }
                } ?: Div("no image")
            },
            tooltip = { event: Event, cell: Tabulator.CellComponent, onRendered: () -> Unit ->
                val shopifyProduct = Json.decodeFromDynamic<ShopifyProduct>(cell.getData())
                shopifyProduct.image?.src?.let { src ->
                    """<img class="rounded" src="$src" style="width: 500px; height: 500px;">"""
                }
            }
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
            vPanel(className = "flex-grow-1 p-2") {
                fsTabulator(
                    viewList = this@ViewListShopifyProduct,
                    pagination = null,
                    contextDataUrlUpdate = {
                        tabSize = this@ViewListShopifyProduct.tabSize
                    },
                    onResult = this@ViewListShopifyProduct::onResult,
                )
                hPanel(justify = JustifyContent.CENTER) {
                    navbar {
                        navForm {
                            simpleSelect(
                                options = listOf("10", "20", "30", "50", "100", "200").map { it to it },
                                value = tabSize.toString()
                            ).onEvent {
                                change = {
                                    tabSize = self.value?.toIntOrNull() ?: 10
                                    AppScope.launch {
                                        this@ViewListShopifyProduct.dataUpdate()
                                    }
                                }
                            }
                            button(text = "", icon = "fas fa-chevron-left", style = ButtonStyle.OUTLINEPRIMARY)
                            button(text = "", icon = "fas fa-chevron-right", style = ButtonStyle.OUTLINEPRIMARY)
                        }
                    }
                }
            }
        }
    }

    private fun onResult(result: dynamic) {
        console.warn("onResult ->", result)
    }
}
