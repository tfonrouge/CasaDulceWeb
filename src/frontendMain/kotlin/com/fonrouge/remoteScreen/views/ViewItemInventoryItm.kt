package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.config.ConfigViewImpl
import com.fonrouge.remoteScreen.services.DataItemService
import com.fonrouge.remoteScreen.services.DataItemServiceManager
import com.fonrouge.remoteScreen.services.IDataItemService

class ViewItemInventoryItm(
    override var urlParams: UrlParams?
) : ViewItem<InventoryItm, DataItemService, String>(
    configView = ConfigViewImpl.ConfigViewItemInventoryItm,
    serverManager = DataItemServiceManager,
    function = IDataItemService::inventoryItm,
    klass = InventoryItm::class
) {
}
