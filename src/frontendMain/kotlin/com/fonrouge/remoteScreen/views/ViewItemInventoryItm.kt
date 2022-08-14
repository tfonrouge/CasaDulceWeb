package com.fonrouge.remoteScreen.views

import com.fonrouge.fsLib.lib.UrlParams
import com.fonrouge.fsLib.view.ViewItem
import com.fonrouge.remoteScreen.config.ConfigViewImpl
import com.fonrouge.remoteScreen.model.InventoryItm
import io.kvision.core.Container
import io.kvision.form.FormPanel

class ViewItemInventoryItm(
    override var urlParams: UrlParams?
) : ViewItem<InventoryItm, String>(
    configView = ConfigViewImpl.ConfigViewItemInventoryItm,
) {
    override fun Container.pageItemBody(): FormPanel<InventoryItm>? {
        TODO("Not yet implemented")
    }
}
