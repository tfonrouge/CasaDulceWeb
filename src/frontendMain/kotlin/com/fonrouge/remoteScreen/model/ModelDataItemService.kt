package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.remoteScreen.services.DataItemService
import com.fonrouge.remoteScreen.services.ShopifyApiService

object ModelDataItemService {
    val dataItemService = DataItemService()
    val shopifiApiService = ShopifyApiService()

    suspend fun updateField(_id: String, value: dynamic) {
        dataItemService.customerOrderItm(
            _id = _id,
            state = StateItem(
                json = JSON.stringify(value),
                crudAction = CrudAction.Update,
                callType = StateItem.CallType.Action,
            )
        )
    }

    suspend fun getImageSrc(barcode: String) : String {
        return shopifiApiService.getImageSrc(barcode)
    }
}
