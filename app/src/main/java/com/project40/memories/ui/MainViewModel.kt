package com.project40.memories.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project40.memories.data.model.BucketListItem
import com.project40.memories.data.model.Gift
import com.project40.memories.data.model.GiftCategory
import com.project40.memories.data.model.Memory
import com.project40.memories.data.model.MemoryCategory
import com.project40.memories.data.model.Reason
import com.project40.memories.data.model.Testimonial
import com.project40.memories.data.model.Voucher
import com.project40.memories.data.repository.Project40DataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainUiState(
    val gifts: List<Gift> = Project40DataSource.gifts,
    val memories: List<Memory> = Project40DataSource.memories,
    val vouchers: List<Voucher> = Project40DataSource.vouchers,
    val reasons: List<Reason> = Project40DataSource.reasons,
    val testimonials: List<Testimonial> = Project40DataSource.testimonials,
    val bucketList: List<BucketListItem> = Project40DataSource.bucketList,
    
    // User progress state (Default starts at Surprise #1 for the authentic birthday quest)
    val unlockedGiftIds: Set<Int> = setOf(1),
    val favoriteMemoryIds: Set<Int> = setOf(1, 2, 3),
    val scratchedVouchers: Map<Int, Float> = mapOf(1 to 0.70f, 2 to 0.0f, 3 to 1.0f, 4 to 1.0f),
    val redeemedVoucherIds: Set<Int> = setOf(3, 4),
    val completedBucketListIds: Set<Int> = Project40DataSource.bucketList.filter { it.isInitiallyCompleted }.map { it.id }.toSet(),
    val favoriteReasonNumbers: Set<Int> = setOf(1, 14, 40),
    
    // UI selection state
    val selectedGiftForModal: Gift? = null,
    val isModalOpen: Boolean = false,
    val isHostModalOpen: Boolean = false,
    val selectedCategoryFilter: GiftCategory = GiftCategory.ALL,
    val selectedMemoryFilter: MemoryCategory = MemoryCategory.ALL,
    val activeArchiveTab: Int = 0,
    val currentReasonIndex: Int = 13, // Reason #14 by default (0-indexed 13)
    val isReasonsScrollModalOpen: Boolean = false,
    
    // Live ticker & Debug mode
    val nextUnlockSecondsLeft: Long = 1 * 3600 + 42 * 60 + 18,
    val isDebugUnlockAll: Boolean = false,
    val confettiTrigger: Int = 0,
    val celebrationToastMessage: String? = null
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(
        MainUiState(
            gifts = Project40DataSource.loadGifts(application)
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        startCountdownTicker()
    }

    private fun startCountdownTicker() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { state ->
                    val nextSec = if (state.nextUnlockSecondsLeft > 0) state.nextUnlockSecondsLeft - 1 else 0
                    state.copy(nextUnlockSecondsLeft = nextSec)
                }
            }
        }
    }

    fun openGiftModal(gift: Gift) {
        _uiState.update { it.copy(selectedGiftForModal = gift, isModalOpen = true) }
    }

    fun closeGiftModal() {
        _uiState.update { it.copy(isModalOpen = false, selectedGiftForModal = null) }
    }

    fun unlockGift(giftId: Int) {
        _uiState.update { state ->
            val updated = state.unlockedGiftIds + giftId
            state.copy(
                unlockedGiftIds = updated,
                confettiTrigger = state.confettiTrigger + 1,
                celebrationToastMessage = "Surprise #$giftId Unlocked & Added to Keepsakes!"
            )
        }
    }

    fun beginJourney() {
        // Unlock Gift #1 immediately
        unlockGift(1)
    }

    fun toggleMemoryFavorite(memoryId: Int) {
        _uiState.update { state ->
            val current = state.favoriteMemoryIds
            val updated = if (current.contains(memoryId)) current - memoryId else current + memoryId
            state.copy(favoriteMemoryIds = updated)
        }
    }

    fun updateVoucherScratch(voucherId: Int, percentage: Float) {
        _uiState.update { state ->
            val current = state.scratchedVouchers.toMutableMap()
            val newPercent = maxOf(current[voucherId] ?: 0f, percentage)
            current[voucherId] = newPercent
            state.copy(scratchedVouchers = current)
        }
    }

    fun redeemVoucher(voucherId: Int) {
        _uiState.update { state ->
            state.copy(
                redeemedVoucherIds = state.redeemedVoucherIds + voucherId,
                confettiTrigger = state.confettiTrigger + 1,
                celebrationToastMessage = "Voucher Redeemed! Kabir is on duty."
            )
        }
    }

    fun toggleBucketListItem(itemId: Int) {
        _uiState.update { state ->
            val current = state.completedBucketListIds
            val updated = if (current.contains(itemId)) current - itemId else current + itemId
            state.copy(completedBucketListIds = updated)
        }
    }

    fun addBucketListItem(title: String, note: String) {
        if (title.isBlank()) return
        val newItem = BucketListItem(
            id = (_uiState.value.bucketList.maxOfOrNull { it.id } ?: 40) + 1,
            title = title.trim(),
            note = note.trim().ifEmpty { "Shared Decade Dream" },
            isInitiallyCompleted = false
        )
        _uiState.update { it.copy(bucketList = it.bucketList + newItem) }
    }

    fun toggleReasonHeart(reasonNumber: Int) {
        _uiState.update { state ->
            val current = state.favoriteReasonNumbers
            val updated = if (current.contains(reasonNumber)) current - reasonNumber else current + reasonNumber
            state.copy(favoriteReasonNumbers = updated)
        }
    }

    fun navigateReason(delta: Int) {
        _uiState.update { state ->
            val count = state.reasons.size
            if (count == 0) return@update state
            val nextIdx = (state.currentReasonIndex + delta + count) % count
            state.copy(currentReasonIndex = nextIdx)
        }
    }

    fun setArchiveTab(tabIndex: Int) {
        _uiState.update { it.copy(activeArchiveTab = tabIndex) }
    }

    fun setCategoryFilter(category: GiftCategory) {
        _uiState.update { it.copy(selectedCategoryFilter = category) }
    }

    fun setMemoryFilter(category: MemoryCategory) {
        _uiState.update { it.copy(selectedMemoryFilter = category) }
    }

    fun setReasonsScrollModal(isOpen: Boolean) {
        _uiState.update { it.copy(isReasonsScrollModalOpen = isOpen) }
    }

    fun openHostModal(open: Boolean) {
        _uiState.update { it.copy(isHostModalOpen = open) }
    }

    fun unlockNextGift() {
        val nextId = (1..40).firstOrNull { !_uiState.value.unlockedGiftIds.contains(it) } ?: return
        unlockGift(nextId)
    }

    fun resetToBeginning() {
        _uiState.update { state ->
            state.copy(
                unlockedGiftIds = setOf(1),
                isDebugUnlockAll = false,
                isHostModalOpen = false,
                celebrationToastMessage = "Reset: Quest starts at Surprise #1"
            )
        }
    }

    fun unlockAllGifts() {
        _uiState.update { state ->
            state.copy(
                unlockedGiftIds = state.gifts.map { it.id }.toSet(),
                isDebugUnlockAll = true,
                isHostModalOpen = false,
                confettiTrigger = state.confettiTrigger + 1,
                celebrationToastMessage = "All 40 Keepsakes Unlocked! Full Archive Open."
            )
        }
    }

    fun jumpToGift(targetId: Int) {
        val ids = (1..targetId).toSet()
        _uiState.update { state ->
            state.copy(
                unlockedGiftIds = ids,
                isHostModalOpen = false,
                celebrationToastMessage = "Advanced to Surprise #$targetId"
            )
        }
    }

    fun showToast(message: String) {
        _uiState.update { it.copy(celebrationToastMessage = message) }
    }

    fun toggleDebugUnlockAll() {
        _uiState.update { state ->
            val debugNow = !state.isDebugUnlockAll
            val allIds = if (debugNow) state.gifts.map { it.id }.toSet() else setOf(1)
            state.copy(
                isDebugUnlockAll = debugNow,
                unlockedGiftIds = allIds,
                celebrationToastMessage = if (debugNow) "All 40 Gifts Unlocked!" else "Reset to Default Quest"
            )
        }
    }

    fun clearCelebrationToast() {
        _uiState.update { it.copy(celebrationToastMessage = null) }
    }
}
