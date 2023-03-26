package com.fonrouge.casaDulceWeb.config

import com.fonrouge.fsLib.config.ConfigView
import com.fonrouge.fsLib.config.ConfigViewItem
import com.fonrouge.fsLib.config.ConfigViewList
import com.fonrouge.fsLib.config.IConfigView
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.casaDulceWeb.model.*
import com.fonrouge.casaDulceWeb.services.*
import com.fonrouge.casaDulceWeb.views.*

class ConfigViewImpl : IConfigView {
    companion object {
        val ConfigViewPriceChecker =
            object : ConfigView<ViewPriceCheck>(
                name = "priceChecker",
                label = "Price Checker",
                viewFunc = ViewPriceCheck::class
            ) {}

        /* Item */
        val ConfigViewItemCustomerOrderHdr =
            object : ConfigViewItem<CustomerOrderHdr, ViewItemCustomerOrderHdr, DataItemService, OId<CustomerOrderHdr>>(
                itemKClass = CustomerOrderHdr::class,
                label = "Customer Order",
                viewFunc = ViewItemCustomerOrderHdr::class,
                serviceManager = DataItemServiceManager,
                function = IDataItemService::customerOrderHdr,
                labelIdFunc = { customerOrderHdr ->
                    customerOrderHdr?.numId?.toString() ?: "?"
                }
            ) {}
        val ConfigViewItemCustomerOrderItm =
            object : ConfigViewItem<CustomerOrderItm, ViewItemCustomerOrderItm, DataItemService, OId<CustomerOrderItm>>(
                itemKClass = CustomerOrderItm::class,
                label = "Customer Order Item",
                viewFunc = ViewItemCustomerOrderItm::class,
                serviceManager = DataItemServiceManager,
                function = IDataItemService::customerOrderItm,
            ) {}
        val ConfigViewItemInventoryItm =
            object : ConfigViewItem<InventoryItm, ViewItemInventoryItm, DataItemService, String>(
                itemKClass = InventoryItm::class,
                label = "Inventory Item",
                viewFunc = ViewItemInventoryItm::class,
                serviceManager = DataItemServiceManager,
                function = IDataItemService::inventoryItm,
            ) {}

        /* list */
        val ConfigViewListCustomerOrderHdr =
            object : ConfigViewList<CustomerOrderHdr, ViewListCustomerOrderHdr, DataListService, OId<CustomerOrderHdr>>(
                itemKClass = CustomerOrderHdr::class,
                label = "Customer Orders",
                viewFunc = ViewListCustomerOrderHdr::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::customerOrderHdr
            ) {}
        val ConfigViewListCustomerOrderItm =
            object : ConfigViewList<CustomerOrderItm, ViewListCustomerOrderItm, DataListService, OId<CustomerOrderItm>>(
                itemKClass = CustomerOrderItm::class,
                label = "Customer Order Items",
                viewFunc = ViewListCustomerOrderItm::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::customerOrderItm
            ) {}
        val ConfigViewListCustomerItm =
            object : ConfigViewList<CustomerItm, ViewListCustomerItm, DataListService, String>(
                itemKClass = CustomerItm::class,
                label = "Customer List",
                viewFunc = ViewListCustomerItm::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::customerItm
            ) {}
        val ConfigViewListInventoryItm =
            object : ConfigViewList<InventoryItm, ViewListInventoryItm, DataListService, String>(
                itemKClass = InventoryItm::class,
                label = "Inventory List",
                viewFunc = ViewListInventoryItm::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::inventoryItm
            ) {}
        val ConfigViewDeliverList =
            object : ConfigViewList<DeliveryOrderItm, ViewListDeliveryItm, DataListService, OId<DeliveryOrderItm>>(
                itemKClass = DeliveryOrderItm::class,
                label = "Delivery List",
                viewFunc = ViewListDeliveryItm::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::deliverList
            ) {}
        val ConfigViewListDeliveryHdr =
            object : ConfigViewList<DeliveryOrderHdr, ViewListDeliveryHdr, DataListService, String>(
                itemKClass = DeliveryOrderHdr::class,
                label = "Delivery Hdr",
                viewFunc = ViewListDeliveryHdr::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::deliveryHdr
            ) {}
        val ConfigViewListQuickbooksProduct =
            object : ConfigViewList<QuickbooksProduct, ViewListQuickbooksItm, DataListService, String>(
                itemKClass = QuickbooksProduct::class,
                label = "Quickbooks Items",
                viewFunc = ViewListQuickbooksItm::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::quickbooksItm
            ) {}
        val ConfigViewListShopifyProduct =
            object : ConfigViewList<ShopifyProduct, ViewListShopifyProduct, DataListService, Long>(
                itemKClass = ShopifyProduct::class,
                label = "Shopify Items",
                viewFunc = ViewListShopifyProduct::class,
                serviceManager = DataListServiceManager,
                function = IDataListService::shopifyItm
            ) {}
    }
}
