package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.mongoDbCollection
import com.fonrouge.remoteScreen.model.CustomerItm

val customerItmDb = mongoDbCollection<CustomerItm, String>()
