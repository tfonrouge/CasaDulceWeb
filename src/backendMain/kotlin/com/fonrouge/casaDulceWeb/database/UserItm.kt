package com.fonrouge.casaDulceWeb.database

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
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
) : BaseDoc<Id<UserItm>>
