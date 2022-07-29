package com.fonrouge.remoteScreen.config

import com.fonrouge.fsLib.apiLib.IConfigView
import com.fonrouge.fsLib.config.ConfigViewItem
import com.fonrouge.fsLib.config.ConfigViewList
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import com.fonrouge.remoteScreen.services.DataItemService
import com.fonrouge.remoteScreen.services.DataItemServiceManager
import com.fonrouge.remoteScreen.services.IDataItemService
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
            object : ConfigViewList<CustomerOrderHdr, ViewListCustomerOrderHdr, String>(
                name = CustomerOrderHdr::class.simpleName!!,
                label = "Customer Order Header List",
                viewFunc = ::ViewListCustomerOrderHdr
            ) {}
        val ConfigViewListCustomerOrderItm =
            object : ConfigViewList<CustomerOrderItm, ViewListCustomerOrderItm, String>(
                name = CustomerOrderItm::class.simpleName!!,
                label = "Customer Order Item List",
                viewFunc = ::ViewListCustomerOrderItm
            ) {}
        val ConfigViewListCustomerItm = object : ConfigViewList<CustomerItm, ViewListCustomerItm, String>(
            name = CustomerItm::class.simpleName!!,
            label = "Customer List",
            viewFunc = ::ViewListCustomerItm
        ) {}
        val ConfigViewListInventoryItm = object : ConfigViewList<InventoryItm, ViewListInventoryItm, String>(
            name = InventoryItm::class.simpleName!!,
            label = "Inventory List",
            viewFunc = ::ViewListInventoryItm
        ) {}
    }
}
