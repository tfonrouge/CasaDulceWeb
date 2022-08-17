package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.remoteScreen.model.CustomerItm

val CustomerItmDb = object : CTableDb<CustomerItm, String>(
    klass = CustomerItm::class
) {}
