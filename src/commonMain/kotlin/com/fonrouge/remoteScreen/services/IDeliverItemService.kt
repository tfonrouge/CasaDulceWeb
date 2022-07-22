package com.fonrouge.remoteScreen.services

import io.kvision.annotations.KVService
import io.kvision.remote.RemoteOption

@KVService
interface IDeliverItemService {

    suspend fun selectDeliverItem(
        search: String?,
        initial: String?,
        state: String?
    ): List<RemoteOption>
}
