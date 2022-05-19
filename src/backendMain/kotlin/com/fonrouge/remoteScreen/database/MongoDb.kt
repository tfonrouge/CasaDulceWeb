package com.fonrouge.remoteScreen.database

import com.mongodb.client.model.Collation
import com.mongodb.client.model.CollationStrength
import io.ktor.server.application.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

//private var database: String? = "test"
private var plugin: MongoDbPluginConfiguration = MongoDbPluginConfiguration()

var locale = "en"

val mongoClient by lazy {
    plugin.let { KMongo.createClient(it.connectionString).coroutine }
}

@Suppress("unused")
val mongoDatabase: CoroutineDatabase by lazy {
    mongoClient.getDatabase(plugin.database)
}

@Suppress("unused")
val collation: Collation by lazy {
    Collation.builder().locale(locale).collationStrength(CollationStrength.PRIMARY).build()
}

@Suppress("unused")
val MongoDbPlugin = createApplicationPlugin(
    name = "MongoDbPlugin",
    createConfiguration = ::MongoDbPluginConfiguration
) {
//    database = pluginConfig.database
    plugin = pluginConfig
}

class MongoDbPluginConfiguration {
    var serverUrl: String? = "localhost"
    var serverPort: Int = 27017
    var authSource: String? = null
    var user: String? = null
    var password: String? = null
    var database: String = "test"

    val connectionString
        get() = "mongodb://" + if (user != null || password != null) {
            "$user:$password@"
        } else {
            ""
        }.let {
            "$it$serverUrl:$serverPort" + if (authSource != null) "/?authSource=$authSource" else ""
        }

}
