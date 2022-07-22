package com.fonrouge.remoteScreen.services

import io.kvision.remote.RemoteOption

actual class DeliverItemService : IDeliverItemService{
    override suspend fun selectDeliverItem(search: String?, initial: String?, state: String?): List<RemoteOption> {
        TODO("Not yet implemented")
    }
}
