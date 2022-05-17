package com.fonrouge.remoteScreen.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable() {
    val description = varchar(name = "description", 100)
    val unit = varchar("unit", 10)
}
