package com.project40.memories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.project40.memories.data.model.Gift
import com.project40.memories.ui.MainViewModel
import com.project40.memories.ui.components.AppHeader
import com.project40.memories.ui.components.ConfettiOverlay
import com.project40.memories.ui.components.FloatingBottomNavigation
import com.project40.memories.ui.components.HostControlsModal
import com.project40.memories.ui.components.Screen
import com.project40.memories.ui.screens.GiftUnlockingModal
import com.project40.memories.ui.screens.Screen0Onboarding
import com.project40.memories.ui.screens.Screen1JourneyHub
import com.project40.memories.ui.screens.Screen2MemoryLane
import com.project40.memories.ui.screens.Screen4VoucherVault
import com.project40.memories.ui.screens.Screen5Archive
import com.project40.memories.ui.theme.Project40Theme
import com.project40.memories.ui.theme.WarmAlabaster

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project40Theme {
                MainApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsState()
    var currentScreen by remember { mutableStateOf(Screen.JOURNEY) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Toast / Snackbar notification for celebrations & quest guidance
    LaunchedEffect(state.celebrationToastMessage) {
        state.celebrationToastMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearCelebrationToast()
        }
    }

    // Handle tapping on any gift in the Journey Hub
    val handleOpenGift: (Gift) -> Unit = { gift ->
        val isUnlocked = state.unlockedGiftIds.contains(gift.id)
        val nextId = (1..state.gifts.size).firstOrNull { !state.unlockedGiftIds.contains(it) } ?: (state.gifts.size + 1)
        val isReady = !isUnlocked && (gift.id == nextId || state.isDebugUnlockAll)

        if (isUnlocked) {
            // Revisit unlocked keepsake
            when (gift.id) {
                1 -> currentScreen = Screen.ONBOARDING
                8 -> currentScreen = Screen.MEMORIES
                12 -> {
                    viewModel.setArchiveTab(1) // Family & Friends
                    currentScreen = Screen.ARCHIVE
                }
                21 -> {
                    viewModel.setArchiveTab(0) // 40 Reasons
                    currentScreen = Screen.ARCHIVE
                }
                40 -> {
                    viewModel.setArchiveTab(2) // Future 40 Bucket List
                    currentScreen = Screen.ARCHIVE
                }
                3, 5, 7, 14 -> {
                    currentScreen = Screen.VOUCHERS
                }
                else -> {
                    // Physical or other reveals open modal
                    viewModel.openGiftModal(gift)
                }
            }
        } else if (isReady) {
            // Ready to open/unlock!
            when (gift.id) {
                1 -> {
                    currentScreen = Screen.ONBOARDING
                }
                8 -> {
                    viewModel.unlockGift(8)
                    currentScreen = Screen.MEMORIES
                }
                12 -> {
                    viewModel.unlockGift(12)
                    viewModel.setArchiveTab(1)
                    currentScreen = Screen.ARCHIVE
                }
                21 -> {
                    viewModel.unlockGift(21)
                    viewModel.setArchiveTab(0)
                    currentScreen = Screen.ARCHIVE
                }
                40 -> {
                    viewModel.unlockGift(40)
                    viewModel.setArchiveTab(2)
                    currentScreen = Screen.ARCHIVE
                }
                3, 5, 7, 14 -> {
                    viewModel.unlockGift(gift.id)
                    currentScreen = Screen.VOUCHERS
                }
                else -> {
                    // Physical scavenger clue opens modal
                    viewModel.openGiftModal(gift)
                }
            }
        } else {
            // Locked mystery gift
            viewModel.showToast("Unlock Surprise #$nextId first to reveal this keepsake!")
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(WarmAlabaster)) {
        Scaffold(
            topBar = {
                AppHeader(
                    currentScreen = currentScreen,
                    onBackClick = if (currentScreen != Screen.JOURNEY) {
                        { currentScreen = Screen.JOURNEY }
                    } else null,
                    onEmblemClick = {
                        // Open Kabir's Companion Host sheet
                        viewModel.openHostModal(true)
                    },
                    onProfileClick = {
                        currentScreen = Screen.ONBOARDING
                    },
                    unlockedCount = state.unlockedGiftIds.size
                )
            },
            bottomBar = {
                // Simplified experience: Bottom navigation only appears once all 40 gifts are unlocked!
                if (state.unlockedGiftIds.size >= 40 || state.isDebugUnlockAll) {
                    FloatingBottomNavigation(
                        currentScreen = currentScreen,
                        onSelectScreen = { currentScreen = it }
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = WarmAlabaster
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Crossfade(
                    targetState = currentScreen,
                    label = "screenTransition"
                ) { screen ->
                    when (screen) {
                        Screen.ONBOARDING -> {
                            Screen0Onboarding(
                                state = state,
                                onBeginJourney = {
                                    viewModel.beginJourney()
                                    currentScreen = Screen.JOURNEY
                                },
                                onBackToJourney = {
                                    currentScreen = Screen.JOURNEY
                                }
                            )
                        }

                        Screen.JOURNEY -> {
                            Screen1JourneyHub(
                                state = state,
                                onOpenGift = handleOpenGift,
                                onFilterSelect = { cat -> viewModel.setCategoryFilter(cat) },
                                onToggleDebugUnlockAll = { viewModel.toggleDebugUnlockAll() },
                                onOpenHostModal = { viewModel.openHostModal(true) }
                            )
                        }

                        Screen.MEMORIES -> {
                            Screen2MemoryLane(
                                state = state,
                                onToggleFavorite = { id -> viewModel.toggleMemoryFavorite(id) },
                                onFilterSelect = { cat -> viewModel.setMemoryFilter(cat) },
                                onBackToJourney = { currentScreen = Screen.JOURNEY }
                            )
                        }

                        Screen.VOUCHERS -> {
                            Screen4VoucherVault(
                                state = state,
                                onScratchProgress = { id, pct -> viewModel.updateVoucherScratch(id, pct) },
                                onRedeemVoucher = { id -> viewModel.redeemVoucher(id) },
                                onBackToJourney = { currentScreen = Screen.JOURNEY }
                            )
                        }

                        Screen.ARCHIVE -> {
                            Screen5Archive(
                                state = state,
                                onTabSelect = { tab -> viewModel.setArchiveTab(tab) },
                                onNavigateReason = { delta -> viewModel.navigateReason(delta) },
                                onToggleReasonHeart = { num -> viewModel.toggleReasonHeart(num) },
                                onToggleBucketItem = { id -> viewModel.toggleBucketListItem(id) },
                                onAddBucketItem = { title, note -> viewModel.addBucketListItem(title, note) },
                                onSetReasonsScrollModal = { isOpen -> viewModel.setReasonsScrollModal(isOpen) },
                                onBackToJourney = { currentScreen = Screen.JOURNEY }
                            )
                        }
                    }
                }
            }
        }

        // Active Gift Unlocking Modal Bottom Sheet
        if (state.isModalOpen && state.selectedGiftForModal != null) {
            val gift = state.selectedGiftForModal!!
            val isUnlocked = state.unlockedGiftIds.contains(gift.id)
            GiftUnlockingModal(
                gift = gift,
                isUnlocked = isUnlocked,
                onDismiss = { viewModel.closeGiftModal() },
                onUnlockConfirm = {
                    viewModel.unlockGift(gift.id)
                }
            )
        }

        // Host Companion Controls Bottom Sheet (for Kabir)
        if (state.isHostModalOpen) {
            HostControlsModal(
                state = state,
                onDismiss = { viewModel.openHostModal(false) },
                onUnlockNext = { viewModel.unlockNextGift() },
                onUnlockAll = { viewModel.unlockAllGifts() },
                onResetToBeginning = {
                    viewModel.resetToBeginning()
                    currentScreen = Screen.JOURNEY
                },
                onJumpToGift = { targetId ->
                    viewModel.jumpToGift(targetId)
                }
            )
        }

        // Celebratory Confetti Particle Overlay
        ConfettiOverlay(
            trigger = state.confettiTrigger,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
