package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseModel
import kotlinx.serialization.Contextual
import org.litote.kmongo.Id
import java.util.*

@kotlinx.serialization.Serializable
@Collection("userItms")
class UserItm(
    @Contextual
    override val _id: Id<UserItm>,
    val userName: String,
    val fullName: String,
    val password: String,
    @Contextual
    val lastAccess: Date?,
) : BaseModel<Id<UserItm>>
