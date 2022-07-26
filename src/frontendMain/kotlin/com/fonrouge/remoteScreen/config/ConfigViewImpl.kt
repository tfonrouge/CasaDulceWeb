package com.fonrouge.remoteScreen.config

import com.fonrouge.fsLib.apiLib.IConfigView
import com.fonrouge.fsLib.config.ConfigViewItem
import com.fonrouge.fsLib.config.ConfigViewList
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import com.fonrouge.remoteScreen.views.*

class ConfigViewImpl : IConfigView {
    companion object {
        val ConfigViewItemCustomerOrderHdr = object : ConfigViewItem<CustomerOrderHdr, ViewItemCustomerOrderHdr>(
            name = CustomerOrderHdr::class.simpleName!!,
            label = "Customer Order Header",
            viewFunc = ::ViewItemCustomerOrderHdr
        ) {}
        val ConfigViewItemCustomerOrderItm = object : ConfigViewItem<CustomerOrderItm, ViewItemCustomerOrderItm>(
            name = CustomerOrderItm::class.simpleName!!,
            label = "Customer Order Item",
            viewFunc = ::ViewItemCustomerOrderItm
        ) {}
        val ConfigViewItemInventoryItm = object : ConfigViewItem<InventoryItm, ViewItemInventoryItm>(
            name = InventoryItm::class.simpleName!!,
            label = "Inventory Item",
            viewFunc = ::ViewItemInventoryItm
        ) {}

        /* list */
        val ConfigViewListCustomerOrderHdr = object : ConfigViewList<CustomerOrderHdr, ViewListCustomerOrderHdr>(
            name = CustomerOrderHdr::class.simpleName!!,
            label = "Customer Order Header List",
            viewFunc = ::ViewListCustomerOrderHdr
        ) {}
        val ConfigViewListCustomerOrderItm = object : ConfigViewList<CustomerOrderItm, ViewListCustomerOrderItm>(
            name = CustomerOrderItm::class.simpleName!!,
            label = "Customer Order Item List",
            viewFunc = ::ViewListCustomerOrderItm
        ) {}
        val ConfigViewListCustomerOrderItmByCustomerOrderHdr =
            object : ConfigViewList<CustomerOrderItm, ViewListCustomerOrderItmByCustomerOrder>(
                name = CustomerOrderItm::class.simpleName!!,
                label = "Customer Order Item List",
                viewFunc = ::ViewListCustomerOrderItmByCustomerOrder
            ) {}
        val ConfigViewListCustomerItm = object : ConfigViewList<CustomerItm, ViewListCustomerItm>(
            name = CustomerItm::class.simpleName!!,
            label = "Customer List",
            viewFunc = ::ViewListCustomerItm
        ) {}
        val ConfigViewListInventoryItm = object : ConfigViewList<InventoryItm, ViewListInventoryItm>(
            name = InventoryItm::class.simpleName!!,
            label = "Inventory List",
            viewFunc = ::ViewListInventoryItm
        ) {}
    }
}
