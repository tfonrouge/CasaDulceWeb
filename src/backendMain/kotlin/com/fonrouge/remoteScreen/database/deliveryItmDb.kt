package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.DeliveryOrderItm

val DeliveryItmDb = object : CTableDb<DeliveryOrderItm, String>(
    klass = DeliveryOrderItm::class
) {}
