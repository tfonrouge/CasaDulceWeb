package com.fonrouge.remoteScreen.database

import kotlinx.serialization.Contextual
import org.litote.kmongo.Id
import java.util.*

@kotlinx.serialization.Serializable
class UserItm(
    @Contextual
    val _id: Id<UserItm>,
    val userName: String,
    val fullName: String,
    val password: String,
    @Contextual
    val lastAccess: Date?,
)
