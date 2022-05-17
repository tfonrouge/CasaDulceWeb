package com.fonrouge.remoteScreen.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object CustomerOrders : IntIdTable() {
    val productId = (integer("product_id").references(Products.id)).nullable() // Column<Int?>
    val qty = integer("qty")
    val unit = varchar(name = "unit", 10)
    val created = varchar(name = "created", 12)
}
