package com.trackhub.feat_network.data.remote

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.greenvenom.core_network.data.map
import com.greenvenom.core_network.supabase.util.SupabaseClient
import com.greenvenom.core_network.supabase.util.supabaseCall
import com.greenvenom.core_network.supabase.util.supabaseLiveCall
import com.trackhub.feat_network.domain.remote.RemoteDataSource
import com.trackhub.core_hub.data.remote.dto.HubDto
import com.trackhub.core_hub.data.remote.dto.HubItemDto
import com.trackhub.core_hub.data.remote.dto.toHub
import com.trackhub.core_hub.data.remote.dto.toHubDto
import com.trackhub.core_hub.data.remote.dto.toHubItem
import com.trackhub.core_hub.data.remote.dto.toHubItemDto
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@OptIn(SupabaseExperimental::class)
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
    override suspend fun addHub(hub: Hub): NetworkResult<Hub, NetworkError> {
        val userId = client.auth.currentUserOrNull()?.id as String
        val updatedHub = hub.copy(userId = userId)
        val result = supabaseCall {
            client.from("hubs")
                .insert(updatedHub.toHubDto()){
                    select()
                }.decodeSingle<HubDto>()
        }

        return result.map { returnedHub -> returnedHub.toHub()}
    }

    override suspend fun updateHub(hub: Hub): NetworkResult<Hub, NetworkError> {
        val result = supabaseCall {
            client.from("hubs").update(hub.toHubDto()) {
                select()
            }.decodeSingle<HubDto>()
        }

        return result.map { returnedHub -> returnedHub.toHub() }
    }

    override suspend fun deleteHub(hubId: String): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.from("hubs").delete {
                filter { eq("id", hubId) }
            }
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

    override suspend fun updateItem(hubItem: HubItem): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.from("items").update(hubItem)
        }
    }

    override suspend fun deleteItem(itemId: Int): NetworkResult<Unit, NetworkError> {
        return supabaseCall {
            client.from("items").delete {
                filter { eq("id", itemId) }
            }
        }
    }

    override fun getItemsFromHub(hubId: String): Flow<NetworkResult<List<HubItem>, NetworkError>> {
        return supabaseLiveCall {
            client.from("items").selectAsFlow(
                primaryKey = HubItemDto::id,
                filter = FilterOperation(
                    column = "hub_id",
                    operator = FilterOperator.EQ,
                    value = hubId
                )
            ).map { items -> items.map { it.toHubItem() } }
        }
    }
}