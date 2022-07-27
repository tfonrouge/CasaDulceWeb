package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.remoteScreen.services.DataItemService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ModelDataItemService {
    val dataItemService = DataItemService()

    suspend fun updateField(_id: String, value: LinkedHashMap<String, Any>) {
        dataItemService.customerOrderItm(
            _id = _id,
            state = StateItem(
                json = Json.encodeToString(value),
                crudAction = CrudAction.Update,
                callType = StateItem.CallType.Action,
            )
        )
    }
}
