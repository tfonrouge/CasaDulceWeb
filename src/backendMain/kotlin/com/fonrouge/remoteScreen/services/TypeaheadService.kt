package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.database.Tables.InventoryItmDb
import com.fonrouge.remoteScreen.model.InventoryItm
import org.litote.kmongo.gte

actual class TypeaheadService : ITypeaheadService {
    override suspend fun inventoryItem(search: String?, state: String?): List<String> {
        println("Typeahead service inventoryItem ... $search")
        return InventoryItmDb.coroutineColl.find(
            InventoryItm::name gte search
        ).toList().map { it.name }
    }
}