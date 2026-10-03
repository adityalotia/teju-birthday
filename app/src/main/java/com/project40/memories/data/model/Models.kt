package com.project40.memories.data.model

enum class GiftCategory {
    ALL,
    PHYSICAL,
    DIGITAL,
    EXPERIENCES,
    COUPON
}

enum class UnlockType {
    IMMEDIATE,
    TIMED,
    SCAVENGER,
    CHAINED
}

data class Gift(
    val id: Int,
    val title: String,
    val category: GiftCategory,
    val unlockType: UnlockType,
    val scheduledTime: String? = null,
    val subtitle: String = "",
    val description: String = "",
    val clueLocation: String? = null,
    val riddle: String? = null,
    val hint: String? = null,
    val revealedTitle: String? = null,
    val revealedDescription: String? = null,
    val revealedNote: String? = null,
    val badgeText: String = "",
    val isUnlockedInitially: Boolean = false
)

enum class MemoryCategory {
    ALL,
    FAVORITES,
    EARLY_DAYS,
    ADVENTURES,
    MOTHERHOOD,
    FAMILY
}

enum class MemoryCardStyle {
    POLAROID,
    SCRAPBOOK,
    TERRACOTTA
}

data class Memory(
    val id: Int,
    val title: String,
    val year: String,
    val dateText: String,
    val location: String,
    val category: MemoryCategory,
    val caption: String,
    val quote: String? = null,
    val cardStyle: MemoryCardStyle = MemoryCardStyle.POLAROID,
    val audioTitle: String? = null,
    val audioDuration: String? = null,
    val hasVoiceNote: Boolean = false,
    val imageUrl: String? = null
)

data class Voucher(
    val id: Int,
    val giftNumber: Int,
    val title: String,
    val headline: String,
    val detail: String,
    val condition: String,
    val iconName: String = "stars",
    val bearer: String = "Teju (Beloved Birthday Queen)"
)

data class Reason(
    val number: Int,
    val text: String,
    val archivalNote: String? = null
)

data class BucketListItem(
    val id: Int,
    val title: String,
    val note: String,
    val isInitiallyCompleted: Boolean = false
)

data class Testimonial(
    val id: Int,
    val author: String,
    val relationship: String,
    val quote: String,
    val fullLetter: String,
    val dateText: String,
    val isVideo: Boolean = false,
    val videoDuration: String? = null
)
