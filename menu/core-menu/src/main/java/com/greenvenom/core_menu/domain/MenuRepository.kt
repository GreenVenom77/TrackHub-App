package com.greenvenom.core_menu.domain

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult

interface MenuRepository {
    suspend fun logout(): NetworkResult<Unit, NetworkError>
}