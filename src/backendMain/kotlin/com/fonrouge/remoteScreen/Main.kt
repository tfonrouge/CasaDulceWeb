package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.database.MongoDbPlugin
import com.fonrouge.remoteScreen.services.InventoryItmServiceManager
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit

@Suppress("unused")
fun Application.main() {
    install(MongoDbPlugin) {
        serverUrl = "dulceserver.dulcesdulcemaria.com"
        serverPort = 27017
        authSource = "CasaDulce"
        user = "user1"
        password = "fb513d2033"
        database = "CasaDulce"
    }
    install(Compression)
    routing {
        applyRoutes(InventoryItmServiceManager)
    }
    kvisionInit()
}
