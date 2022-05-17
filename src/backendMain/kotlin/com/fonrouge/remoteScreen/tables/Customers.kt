package com.fonrouge.remoteScreen.tables

import org.jetbrains.exposed.dao.id.IntIdTable

class bCustomers : IntIdTable() {
    val name = varchar("name", 50)
    val address = varchar("address", 50)
    val phone1 = varchar("phone1", 10)
    val email = varchar("email", 50)
}
