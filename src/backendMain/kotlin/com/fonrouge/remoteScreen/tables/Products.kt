package com.fonrouge.remoteScreen.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable() {
    val code = varchar(name = "code", length = 10).index(customIndexName = "u_code", isUnique = true)
    val description = varchar(name = "description", length = 100)
    val unit = varchar(name = "unit", length = 10)
}
