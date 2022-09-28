package com.fonrouge.remoteScreen

import com.fonrouge.fsLib.mongoDb.MongoDbPlugin
import com.fonrouge.fsLib.mongoDb.collation
import com.fonrouge.remoteScreen.database.UserItm
import com.fonrouge.remoteScreen.database.UserItmDb
import com.fonrouge.remoteScreen.services.*
import com.fonrouge.remoteScreen.upload.uploadsRoute
import com.toxicbakery.bcrypt.Bcrypt
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

const val uploadsDir = "uploads"

@Suppress("unused")
fun Application.main() {
    install(MongoDbPlugin) {
//        serverUrl = "casadulce.fonrouge.com"
        serverUrl = "localhost"
        serverPort = 27017
        database = "CasaDulce"
        authSource = "CasaDulce"
        user = "user1"
        password = "fb513d2033"
    }
    install(Compression)
    install(DefaultHeaders)
//    install(CallLogging)
    install(Sessions) {
        cookie<User>("KTSESSION", storage = SessionStorageMemory()) {
            cookie.path = "/"
            cookie.extensions["SameSite"] = "strict"
        }
    }
    install(Authentication) {
        form {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                println("credentials = $credentials")
                UserItmDb.coroutineColl.find(
                    UserItm::userName eq credentials.name,
                ).collation(collation = collation()).first()?.let {
                    if (Bcrypt.verify(credentials.password, it.password.encodeToByteArray())) {
                        UserItmDb.coroutineColl.updateOne(
                            filter = UserItm::_id eq it._id, update = set(UserItm::lastAccess setTo Date())
                        )
                        UserIdPrincipal(credentials.name)
                    } else null
                }
            }
            skipWhen { call -> call.sessions.get<User>() != null }
        }
    }
    routing {
        if (!Files.isDirectory(Path(uploadsDir))) {
            Files.createDirectory(Path(uploadsDir))
        }
        authenticate {
            post("login") {
                val result = call.principal<UserIdPrincipal>()?.let { userIdPrincipal ->
                    UserItmDb.coroutineColl.find(UserItm::userName eq userIdPrincipal.name).collation(collation())
                        .first()
                        ?.let { user ->
                            val profile = User(
                                id = user._id.toString(),
                                name = user.fullName,
                                username = user.userName,
                                password = null,
                            )
                            call.sessions.set(profile)
                            HttpStatusCode.OK
                        }
                }
                if (result == null) {
                    call.sessions.clear<User>()
                }
                call.respond(result ?: HttpStatusCode.Unauthorized)
            }
            get("logout") {
                call.sessions.clear<User>()
                call.respondRedirect("/")
            }
            applyRoutes(UserServiceManager)
            applyRoutes(CasaDulceServiceManager)
            applyRoutes(DataItemServiceManager)
            applyRoutes(DataListServiceManager)
            applyRoutes(SelectServiceManager)
            applyRoutes(TypeaheadServiceManager)
            uploadsRoute()
        }
    }
    kvisionInit()
}
