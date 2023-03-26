package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.casaDulceWeb.services.DataItemService
import com.fonrouge.casaDulceWeb.services.ShopifyApiService

object ModelDataItemService {
    val dataItemService = DataItemService()
    val shopifiApiService = ShopifyApiService()

    suspend fun updateField(_id: OId<CustomerOrderItm>, value: dynamic) {
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
