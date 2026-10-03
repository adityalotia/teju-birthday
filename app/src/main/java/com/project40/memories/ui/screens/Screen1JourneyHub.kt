package com.project40.memories.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.R
import com.project40.memories.data.model.Gift
import com.project40.memories.data.model.GiftCategory
import com.project40.memories.ui.MainUiState
import com.project40.memories.ui.theme.AccentGold
import com.project40.memories.ui.theme.BorderWarm
import com.project40.memories.ui.theme.ChampagneRose
import com.project40.memories.ui.theme.ChampagneRoseLight
import com.project40.memories.ui.theme.CrispOffWhite
import com.project40.memories.ui.theme.DustyTerracotta
import com.project40.memories.ui.theme.DustyTerracottaDark
import com.project40.memories.ui.theme.InkCharcoal
import com.project40.memories.ui.theme.InkEspresso
import com.project40.memories.ui.theme.MutedSage
import com.project40.memories.ui.theme.MutedSageDeep
import com.project40.memories.ui.theme.MutedSageLight
import com.project40.memories.ui.theme.PillShape
import com.project40.memories.ui.theme.SoftIvory
import com.project40.memories.ui.theme.SurfaceContainer
import com.project40.memories.ui.theme.SurfaceContainerHigh
import com.project40.memories.ui.theme.SurfaceVariant
import com.project40.memories.ui.theme.WarmAlabaster

@Composable
fun Screen1JourneyHub(
    state: MainUiState,
    onOpenGift: (Gift) -> Unit,
    onFilterSelect: (GiftCategory) -> Unit,
    onToggleDebugUnlockAll: () -> Unit,
    onOpenHostModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalCount = state.gifts.size
    val unlockedCount = state.unlockedGiftIds.size
    val progressFraction = if (totalCount > 0) unlockedCount.toFloat() / totalCount else 0f
    val isAllUnlocked = unlockedCount >= totalCount || state.isDebugUnlockAll
    val nextGiftId = (1..totalCount).firstOrNull { !state.unlockedGiftIds.contains(it) } ?: 41
    val currentActiveGift = state.gifts.firstOrNull { it.id == nextGiftId }

    val filteredGifts = if (isAllUnlocked) {
        when (state.selectedCategoryFilter) {
            GiftCategory.ALL -> state.gifts
            GiftCategory.PHYSICAL -> state.gifts.filter { it.category == GiftCategory.PHYSICAL }
            GiftCategory.DIGITAL -> state.gifts.filter { it.category == GiftCategory.DIGITAL }
            GiftCategory.EXPERIENCES -> state.gifts.filter { it.category == GiftCategory.EXPERIENCES || it.category == GiftCategory.COUPON }
            GiftCategory.COUPON -> state.gifts.filter { it.category == GiftCategory.COUPON }
        }
    } else {
        state.gifts
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmAlabaster)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            // Simplified, Luxury Hero Progress Header
            SimplifiedHeroHeader(
                unlockedCount = unlockedCount,
                totalCount = totalCount,
                progressFraction = progressFraction,
                isAllUnlocked = isAllUnlocked,
                onEmblemClick = onOpenHostModal
            )
        }

        // Active Gift Spotlight (Shown only during the quest phase)
        if (!isAllUnlocked && currentActiveGift != null) {
            item {
                ActiveGiftSpotlight(
                    gift = currentActiveGift,
                    countdownSeconds = state.nextUnlockSecondsLeft,
                    onOpen = { onOpenGift(currentActiveGift) }
                )
            }
        }

        // Category Filter Bar (Only shown once all are unlocked, to prevent spoilers and keep quest clean)
        if (isAllUnlocked) {
            item {
                CategoryFilterBar(
                    selected = state.selectedCategoryFilter,
                    onSelect = onFilterSelect
                )
            }
        }

        // Feed Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isAllUnlocked) "THE 40 KEEPSAKES ARCHIVE" else "THE 40 JOURNEY • STEP BY STEP",
                    style = MaterialTheme.typography.labelSmall,
                    color = InkCharcoal,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "$unlockedCount of $totalCount Unlocked",
                    style = MaterialTheme.typography.bodySmall,
                    color = DustyTerracotta,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // 40 Advent Keepsakes List
        items(filteredGifts, key = { it.id }) { gift ->
            val isUnlocked = state.unlockedGiftIds.contains(gift.id)
            val isActive = !isUnlocked && (gift.id == nextGiftId || state.isDebugUnlockAll)

            SimplifiedGiftCard(
                gift = gift,
                isUnlocked = isUnlocked,
                isActiveReady = isActive,
                onOpen = { onOpenGift(gift) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
private fun SimplifiedHeroHeader(
    unlockedCount: Int,
    totalCount: Int,
    progressFraction: Float,
    isAllUnlocked: Boolean,
    onEmblemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(22.dp), spotColor = InkEspresso.copy(alpha = 0.06f))
            .clip(RoundedCornerShape(22.dp))
            .background(SoftIvory)
            .border(1.dp, BorderWarm, RoundedCornerShape(22.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onEmblemClick() }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(DustyTerracotta)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isAllUnlocked) "THE ARCHIVE IS COMPLETE" else "MILESTONE QUEST",
                            style = MaterialTheme.typography.labelSmall,
                            color = DustyTerracotta,
                            letterSpacing = 1.2.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isAllUnlocked) "Happy 40th Birthday, Teju!" else "Teju's 40th Birthday Quest",
                        style = MaterialTheme.typography.headlineMedium,
                        color = InkEspresso
                    )
                    Text(
                        text = if (isAllUnlocked)
                            "All 40 surprises have been unsealed. Revisit every memory, letter, reason, and gift below."
                        else
                            "Follow this path to unlock 40 curated surprises one after another.",
                        style = MaterialTheme.typography.bodySmall,
                        color = InkCharcoal
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Progress Circular Indicator
                Box(
                    modifier = Modifier.size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val stroke = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                        drawCircle(
                            color = SurfaceVariant,
                            style = stroke
                        )
                        drawArc(
                            color = DustyTerracotta,
                            startAngle = -90f,
                            sweepAngle = 360f * progressFraction,
                            useCenter = false,
                            style = stroke
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$unlockedCount",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = InkEspresso
                        )
                        Text(
                            text = "/ $totalCount",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = DustyTerracotta
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Linear Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(PillShape)
                    .background(SurfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction.coerceIn(0.02f, 1f))
                        .height(8.dp)
                        .clip(PillShape)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(DustyTerracotta, AccentGold)
                            )
                        )
                )
            }
        }
    }
}

@Composable
private fun ActiveGiftSpotlight(
    gift: Gift,
    countdownSeconds: Long,
    onOpen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(20.dp), spotColor = DustyTerracotta.copy(alpha = 0.2f))
            .clip(RoundedCornerShape(20.dp))
            .background(CrispOffWhite)
            .border(1.5.dp, DustyTerracotta, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (gift.id == 1) {
                        Image(
                            painter = painterResource(id = R.drawable.img_teju_portrait),
                            contentDescription = "Teju's Portrait",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(1.dp, DustyTerracotta.copy(alpha = 0.5f), CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(DustyTerracotta),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${gift.id}",
                                style = MaterialTheme.typography.titleMedium,
                                color = CrispOffWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Stars,
                                contentDescription = null,
                                tint = DustyTerracotta,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "CURRENT ACTIVE SURPRISE",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = DustyTerracotta,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = when (gift.category) {
                                GiftCategory.PHYSICAL -> "Scavenger Mystery Ready"
                                GiftCategory.DIGITAL -> gift.title
                                GiftCategory.COUPON -> "Special Voucher Waiting"
                                else -> gift.title
                            },
                            style = MaterialTheme.typography.titleLarge,
                            color = InkEspresso
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(ChampagneRoseLight)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = gift.category.name,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = DustyTerracottaDark,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Teaser detail
            Text(
                text = if (gift.category == GiftCategory.PHYSICAL)
                    "A hidden token is waiting in your world: ${gift.clueLocation ?: "Coordinates Ready"}. Read the cipher to find it."
                else
                    gift.subtitle.ifEmpty { gift.description },
                style = MaterialTheme.typography.bodyMedium,
                color = InkCharcoal
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action Button
            Button(
                onClick = onOpen,
                shape = PillShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DustyTerracotta,
                    contentColor = CrispOffWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Redeem,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (gift.category) {
                        GiftCategory.PHYSICAL -> "Solve Riddle & Open Surprise #${gift.id}"
                        GiftCategory.DIGITAL -> "Enter Digital Milestone #${gift.id}"
                        GiftCategory.COUPON -> "Reveal & Scratch Voucher"
                        else -> "Open Surprise #${gift.id}"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SimplifiedGiftCard(
    gift: Gift,
    isUnlocked: Boolean,
    isActiveReady: Boolean,
    onOpen: () -> Unit
) {
    when {
        isUnlocked -> {
            // UNLOCKED KEEPSAKE CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(16.dp), spotColor = InkEspresso.copy(alpha = 0.04f))
                    .clip(RoundedCornerShape(16.dp))
                    .background(CrispOffWhite)
                    .border(1.dp, BorderWarm, RoundedCornerShape(16.dp))
                    .clickable { onOpen() }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (gift.id == 1) {
                            Image(
                                painter = painterResource(id = R.drawable.img_teju_portrait),
                                contentDescription = "Teju's Portrait",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, MutedSageDeep.copy(alpha = 0.4f), CircleShape)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MutedSageLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${gift.id}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MutedSageDeep,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = null,
                                    tint = MutedSage,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "UNLOCKED • ${gift.category.name}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = MutedSage,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = gift.revealedTitle ?: gift.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = InkEspresso
                            )
                            Text(
                                text = gift.subtitle.ifEmpty { gift.description },
                                style = MaterialTheme.typography.bodySmall,
                                color = InkCharcoal,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Revisit CTA Pill
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(SoftIvory)
                            .border(1.dp, BorderWarm, PillShape)
                            .clickable { onOpen() }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = when (gift.id) {
                                    1 -> "Read"
                                    8 -> "Memories"
                                    12 -> "Letters"
                                    21 -> "Reasons"
                                    40 -> "Dreams"
                                    else -> "Revisit"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = InkEspresso,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = null,
                                tint = InkEspresso,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }

        isActiveReady -> {
            // READY TO UNLOCK CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = DustyTerracotta.copy(alpha = 0.2f))
                    .clip(RoundedCornerShape(16.dp))
                    .background(CrispOffWhite)
                    .border(1.5.dp, DustyTerracotta, RoundedCornerShape(16.dp))
                    .clickable { onOpen() }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(DustyTerracotta),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${gift.id}",
                                style = MaterialTheme.typography.titleMedium,
                                color = CrispOffWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "READY TO UNLOCK",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = DustyTerracotta,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (gift.category == GiftCategory.PHYSICAL) "Mystery Scavenger Clue" else gift.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = InkEspresso
                            )
                            Text(
                                text = "Tap to open and unseal this surprise",
                                style = MaterialTheme.typography.bodySmall,
                                color = InkCharcoal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = onOpen,
                        shape = PillShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DustyTerracotta,
                            contentColor = CrispOffWhite
                        ),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(
                            text = "Open",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        else -> {
            // LOCKED SPOILER-FREE MYSTERY CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SoftIvory.copy(alpha = 0.7f))
                    .border(0.5.dp, BorderWarm, RoundedCornerShape(16.dp))
                    .clickable { onOpen() }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${gift.id}",
                                style = MaterialTheme.typography.titleMedium,
                                color = InkCharcoal.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = null,
                                    tint = InkCharcoal.copy(alpha = 0.5f),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "LOCKED • ${gift.category.name}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = InkCharcoal.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "A Mystery Keepsake",
                                style = MaterialTheme.typography.titleMedium,
                                color = InkEspresso.copy(alpha = 0.65f)
                            )
                            Text(
                                text = gift.scheduledTime?.let { "Unlocks at $it" } ?: "Unlocks after Surprise #${gift.id - 1}",
                                style = MaterialTheme.typography.bodySmall,
                                color = InkCharcoal.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Locked",
                        tint = InkCharcoal.copy(alpha = 0.3f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterBar(
    selected: GiftCategory,
    onSelect: (GiftCategory) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterPill(
            icon = Icons.Filled.AutoAwesome,
            label = "All 40",
            isSelected = selected == GiftCategory.ALL,
            onClick = { onSelect(GiftCategory.ALL) }
        )
        FilterPill(
            icon = Icons.Filled.Inventory2,
            label = "Physical",
            isSelected = selected == GiftCategory.PHYSICAL,
            onClick = { onSelect(GiftCategory.PHYSICAL) }
        )
        FilterPill(
            icon = Icons.Filled.Devices,
            label = "Digital",
            isSelected = selected == GiftCategory.DIGITAL,
            onClick = { onSelect(GiftCategory.DIGITAL) }
        )
        FilterPill(
            icon = Icons.Filled.Explore,
            label = "Experiences & Vouchers",
            isSelected = selected == GiftCategory.EXPERIENCES,
            onClick = { onSelect(GiftCategory.EXPERIENCES) }
        )
    }
}

@Composable
private fun FilterPill(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) DustyTerracotta else SoftIvory,
        label = "pillBg"
    )
    val textTint by animateColorAsState(
        targetValue = if (isSelected) CrispOffWhite else InkCharcoal,
        label = "pillText"
    )

    Row(
        modifier = Modifier
            .clip(PillShape)
            .background(bg)
            .border(1.dp, if (isSelected) DustyTerracotta else BorderWarm, PillShape)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textTint,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = textTint,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
