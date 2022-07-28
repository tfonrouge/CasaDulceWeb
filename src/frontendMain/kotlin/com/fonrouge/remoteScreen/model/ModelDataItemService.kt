package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.remoteScreen.services.DataItemService

object ModelDataItemService {
    val dataItemService = DataItemService()

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
}
