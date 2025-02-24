package com.trackhub.data.datasource.remote

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.data.map
import com.trackhub.domain.datasource.remote.RemoteDataSource
import com.greenvenom.networking.supabase.util.SupabaseClient
import com.greenvenom.networking.supabase.util.supabaseCall
import com.trackhub.hub.data.remote.dto.HubDto
import com.trackhub.hub.data.remote.dto.HubItemDto
import com.trackhub.hub.data.remote.mappers.toHub
import com.trackhub.hub.data.remote.mappers.toHubItem
import com.trackhub.hub.domain.Hub
import com.trackhub.hub.domain.HubItem
import com.trackhub.hub.domain.mappers.toHubDto
import com.trackhub.hub.domain.mappers.toHubItemDto
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseDataSource(
    supabaseClient: SupabaseClient
): RemoteDataSource {
    private val client = supabaseClient.getClient()

    // Authentication
    override suspend fun registerUser(
        displayName: String,
        email: String,
        password: String
    ): NetworkResult<UserInfo?, NetworkError> {
        return supabaseCall<UserInfo?> {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject { put("display_name", displayName) }
            }
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, otp)
        }
    }

    override suspend fun sendResetPasswordEmail(email: String): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.auth.resetPasswordForEmail(email = email)
        }
    }

    override suspend fun updatePassword(password: String): NetworkResult<UserInfo, NetworkError> {
        return supabaseCall {
            client.auth.updateUser {
                this.password = password
            }
        }
    }

    override suspend fun loginUser(email: String, password: String): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun logoutUser(): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.auth.signOut()
        }
    }

    // Hubs
    override suspend fun addNewHub(hub: Hub): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.from("hubs").insert(hub.toHubDto())
        }
    }

    override suspend fun getOwnHubs(): NetworkResult<List<Hub>, NetworkError> {
        return supabaseCall {
            client.from("hubs").select().decodeList<HubDto>()
        }.map { response ->
            response.map { it.toHub() }
        }
    }

    override suspend fun getSharedHubs(): NetworkResult<List<Hub>, NetworkError> {
        return supabaseCall {
            client.postgrest.rpc(function = "get_shared_hubs").decodeList<HubDto>()
        }.map { response ->
            response.map { it.toHub() }
        }
    }

    override suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.from("items").insert(hubItem.toHubItemDto())
        }
    }

    override suspend fun getItemsFromHub(hubId: Int): NetworkResult<List<HubItem>, NetworkError> {
        return supabaseCall {
            client.from("items").select() {
                filter { HubItemDto::hubId eq hubId }
            }.decodeList<HubItemDto>()
        }.map { response ->
            response.map{ it.toHubItem() }
        }
    }
}