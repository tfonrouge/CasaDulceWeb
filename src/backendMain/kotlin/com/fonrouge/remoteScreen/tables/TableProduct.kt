package com.fonrouge.remoteScreen.tables

import com.fonrouge.remoteScreen.IProduct
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TableProduct(id: EntityID<Int>) : IntEntity(id), IProduct {
    companion object : IntEntityClass<TableProduct>(Products)

    override var code: String by Products.code
    override var description by Products.description
    override var unit by Products.unit
}
