package com.fonrouge.remoteScreen.config

import com.fonrouge.fsLib.apiLib.IConfigView
import com.fonrouge.fsLib.config.ConfigViewItem
import com.fonrouge.fsLib.config.ConfigViewList
import com.fonrouge.remoteScreen.model.*
import com.fonrouge.remoteScreen.services.*
import com.fonrouge.remoteScreen.views.*

class ConfigViewImpl : IConfigView {
    companion object {
        val ConfigViewItemCustomerOrderHdr =
            object : ConfigViewItem<CustomerOrderHdr, ViewItemCustomerOrderHdr, DataItemService, String>(
                klass = CustomerOrderHdr::class,
                label = "Customer Order Header",
                viewFunc = ::ViewItemCustomerOrderHdr,
                serverManager = DataItemServiceManager,
                function = IDataItemService::customerOrderHdr,
            ) {}
        val ConfigViewItemCustomerOrderItm =
            object : ConfigViewItem<CustomerOrderItm, ViewItemCustomerOrderItm, DataItemService, String>(
                klass = CustomerOrderItm::class,
                label = "Customer Order Item",
                viewFunc = ::ViewItemCustomerOrderItm,
                serverManager = DataItemServiceManager,
                function = IDataItemService::customerOrderItm,
            ) {}
        val ConfigViewItemInventoryItm =
            object : ConfigViewItem<InventoryItm, ViewItemInventoryItm, DataItemService, String>(
                klass = InventoryItm::class,
                label = "Inventory Item",
                viewFunc = ::ViewItemInventoryItm,
                serverManager = DataItemServiceManager,
                function = IDataItemService::inventoryItm,
            ) {}

        /* list */
        val ConfigViewListCustomerOrderHdr =
            object : ConfigViewList<CustomerOrderHdr, ViewListCustomerOrderHdr, DataListService, String>(
                name = CustomerOrderHdr::class.simpleName!!,
                label = "Customer Order Header List",
                viewFunc = ::ViewListCustomerOrderHdr,
                serverManager = DataListServiceManager,
                function = DataListService::customerOrderHdr
            ) {}
        val ConfigViewListCustomerOrderItm =
            object : ConfigViewList<CustomerOrderItm, ViewListCustomerOrderItm, DataListService, String>(
                name = CustomerOrderItm::class.simpleName!!,
                label = "Customer Order Item List",
                viewFunc = ::ViewListCustomerOrderItm,
                serverManager = DataListServiceManager,
                function = DataListService::customerOrderItm
            ) {}
        val ConfigViewListCustomerItm =
            object : ConfigViewList<CustomerItm, ViewListCustomerItm, DataListService, String>(
                name = CustomerItm::class.simpleName!!,
                label = "Customer List",
                viewFunc = ::ViewListCustomerItm,
                serverManager = DataListServiceManager,
                function = DataListService::customerItm
            ) {}
        val ConfigViewListInventoryItm =
            object : ConfigViewList<InventoryItm, ViewListInventoryItm, DataListService, String>(
                name = InventoryItm::class.simpleName!!,
                label = "Inventory List",
                viewFunc = ::ViewListInventoryItm,
                serverManager = DataListServiceManager,
                function = DataListService::inventoryItm
            ) {}
        val ConfigViewDeliverList =
            object : ConfigViewList<DeliveryOrderItm, ViewListDeliveryItm, DataListService, String>(
                name = DeliveryOrderItm::class.simpleName!!,
                label = "Delivery List",
                viewFunc = ::ViewListDeliveryItm,
                serverManager = DataListServiceManager,
                function = DataListService::deliverList
            ) {}
        val ConfigViewListDeliveryHdr =
            object : ConfigViewList<DeliveryOrderItm, ViewListDeliveryHdr, DataListService, String>(
                name = DeliveryOrderItm::class.simpleName!!,
                label = "Delivery Hdr",
                viewFunc = ::ViewListDeliveryHdr,
                serverManager = DataListServiceManager,
                function = DataListService::deliverList
            ) {}
       }
    }
