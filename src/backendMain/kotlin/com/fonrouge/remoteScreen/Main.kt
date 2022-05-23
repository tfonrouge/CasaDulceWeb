package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.database.MongoDbPlugin
import com.fonrouge.remoteScreen.services.CasaDulceServiceManager
import com.fonrouge.remoteScreen.upload.uploadsRoute
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit
import java.nio.file.Files
import kotlin.io.path.Path

const val uploadsDir = "uploads"

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
    install(DefaultHeaders)
    install(CallLogging)
    install(Sessions) {
//        cookie<Profile>("KTSESSION", storage = SessionStorageMemory()) {
//            cookie.path = "/"
//            cookie.extensions["SameSite"] = "strict"
//        }
    }
    routing {
        if (!Files.isDirectory(Path(uploadsDir))) {
            Files.createDirectory(Path(uploadsDir))
        }
        uploadsRoute()
        applyRoutes(CasaDulceServiceManager)
    }
    kvisionInit()
}
