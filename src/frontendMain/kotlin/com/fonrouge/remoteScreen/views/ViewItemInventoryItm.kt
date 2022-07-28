package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.config.ConfigViewImpl
import com.fonrouge.remoteScreen.model.InventoryItm

class ViewItemInventoryItm(
    override var urlParams: UrlParams?
) : ViewItem<InventoryItm, String>(
    configView = ConfigViewImpl.ConfigViewItemInventoryItm,
)
