package com.fonrouge.casaDulceWeb.services

import com.fonrouge.casaDulceWeb.database.Tables.InventoryItmDb
import com.fonrouge.casaDulceWeb.model.InventoryItm
import org.litote.kmongo.gte

actual class TypeaheadService : ITypeaheadService {
    override suspend fun inventoryItem(search: String?, state: String?): List<String> {
        println("Typeahead service inventoryItem ... $search")
        return InventoryItmDb.coroutineColl.find(
            InventoryItm::name gte search
        ).toList().map { it.name }
    }
}