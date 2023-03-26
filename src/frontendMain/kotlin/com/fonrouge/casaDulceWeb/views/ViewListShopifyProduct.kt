package com.fonrouge.casaDulceWeb.views

import com.fonrouge.fsLib.fieldName
import com.fonrouge.fsLib.layout.fsTabulator
import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.ViewList
import com.fonrouge.casaDulceWeb.config.ConfigViewImpl.Companion.ConfigViewListShopifyProduct
import com.fonrouge.casaDulceWeb.model.QuickbooksProduct
import com.fonrouge.casaDulceWeb.model.ShopifyProduct
import com.fonrouge.casaDulceWeb.model.ShopifyVariant
import com.fonrouge.casaDulceWeb.services.DataListService
import com.fonrouge.casaDulceWeb.services.ShopifyApiService
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Editor
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
    override val columnDefinitionList: List<ColumnDefinition<ShopifyProduct>> = listOf(
        ColumnDefinition(
            title = "Barcode",
            field = fieldName(ShopifyProduct::barcode),
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "Handle",
            field = fieldName(ShopifyProduct::handle),
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "Sku",
            field = fieldName(ShopifyProduct::variant0, ShopifyVariant::sku),
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "title",
            field = fieldName(ShopifyProduct::title),
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "bodyHtml",
            field = fieldName(ShopifyProduct::bodyHtml),
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "cost",
            field = fieldName(ShopifyProduct::productQuickbooks, QuickbooksProduct::cost),
            headerFilter = Editor.INPUT,
        ),
        ColumnDefinition(
            title = "createdAt",
            field = fieldName(ShopifyProduct::createdAt)
        ),
        ColumnDefinition(
            title = "tags",
            field = fieldName(ShopifyProduct::tags),
            headerFilter = Editor.INPUT,
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
        ),
        ColumnDefinition(
            title = "images",
            fieldName(ShopifyProduct::images),
            formatterComponentFunction = { _, _, span ->
                span.images.let { src ->
                    Image(src.toString()) {
                        width = 35.px
                        height = 35.px
                    }
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
                )
                button("Refresh data from Shopify ...").onClick {
                    AppScope.launch {
                        ShopifyApiService().syncFromShopifyApi()
                    }
                }
            }
        }
    }

    init {
        AppScope.launch {
            ShopifyApiService().syncFromShopifyApi()
        }
    }
}
