package com.trackhub.feat_hub.presentation.models

import com.trackhub.core_hub.domain.models.Hub
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class HubUI(
    val id: String = "",
    val userId: String,
    val name: String,
    val description: String? = null,
    val createdAt: String = "",
)

fun Hub.toHubUI(): HubUI {
    return HubUI(
        id = this.id,
        userId = this.userId,
        name = this.name,
        description = this.description,
        createdAt = formatHubDate(this.createdAt),
    )
}

fun formatHubDate(timestamp: String): String {
    // Parse the Supabase timestamp
    val instant = Instant.parse(timestamp)

    // Create a formatter for date-only display
    val dateFormatter = DateTimeFormatter
        .ofPattern("MMM d, yyyy")  // e.g., "Mar 5, 2024"
        .withZone(ZoneId.systemDefault())

    // Return the formatted date
    return dateFormatter.format(instant)
}
