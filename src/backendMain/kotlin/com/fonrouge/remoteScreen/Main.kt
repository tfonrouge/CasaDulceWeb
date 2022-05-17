package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.ProductCatalogServiceManager
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit
import org.jetbrains.exposed.sql.Database

@Suppress("unused")
fun Application.main() {
    DatabaseFactory.init()
    install(Compression)
    routing {
        applyRoutes(ProductCatalogServiceManager)
    }
    kvisionInit()
}
