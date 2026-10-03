package com.project40.memories.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.ui.MainUiState
import com.project40.memories.ui.theme.AccentGold
import com.project40.memories.ui.theme.BorderWarm
import com.project40.memories.ui.theme.ChampagneRoseLight
import com.project40.memories.ui.theme.CrispOffWhite
import com.project40.memories.ui.theme.DustyTerracotta
import com.project40.memories.ui.theme.InkCharcoal
import com.project40.memories.ui.theme.InkEspresso
import com.project40.memories.ui.theme.MutedSageDeep
import com.project40.memories.ui.theme.MutedSageLight
import com.project40.memories.ui.theme.PillShape
import com.project40.memories.ui.theme.SoftIvory
import com.project40.memories.ui.theme.SurfaceContainer
import com.project40.memories.ui.theme.SurfaceContainerHigh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostControlsModal(
    state: MainUiState,
    onDismiss: () -> Unit,
    onUnlockNext: () -> Unit,
    onUnlockAll: () -> Unit,
    onResetToBeginning: () -> Unit,
    onJumpToGift: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val unlockedCount = state.unlockedGiftIds.size
    val nextGiftId = (1..40).firstOrNull { !state.unlockedGiftIds.contains(it) } ?: 41
    val nextGift = state.gifts.firstOrNull { it.id == nextGiftId }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SoftIvory,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(ChampagneRoseLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VpnKey,
                            contentDescription = null,
                            tint = DustyTerracotta,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "COMPANION CONTROLS",
                            style = MaterialTheme.typography.labelSmall,
                            color = DustyTerracotta,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Host Mode (Kabir)",
                            style = MaterialTheme.typography.titleMedium,
                            color = InkEspresso
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer)
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = InkCharcoal,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Current Progress Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CrispOffWhite)
                    .border(1.dp, BorderWarm, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "QUEST STATUS",
                            style = MaterialTheme.typography.labelSmall,
                            color = InkCharcoal,
                            letterSpacing = 1.0.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(if (unlockedCount >= 40) MutedSageLight else ChampagneRoseLight)
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "$unlockedCount / 40 Unlocked",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (unlockedCount >= 40) MutedSageDeep else DustyTerracotta,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (nextGift != null) "Next in Line: Gift #$nextGiftId • ${nextGift.title}" else "All 40 Surprises are Unlocked!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkEspresso,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (nextGift != null) {
                        Text(
                            text = "Type: ${nextGift.category.name} • ${nextGift.subtitle.ifEmpty { nextGift.description }}",
                            style = MaterialTheme.typography.bodySmall,
                            color = InkCharcoal,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action: Unlock Next
            if (nextGiftId <= 40) {
                Button(
                    onClick = onUnlockNext,
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
                        imageVector = Icons.Filled.LockOpen,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Unlock Next Surprise (#$nextGiftId)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            // Secondary Action: Unlock All
            Button(
                onClick = onUnlockAll,
                shape = PillShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceContainerHigh,
                    contentColor = InkEspresso
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Unlock All 40 Keepsakes (Preview Mode)",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Reset Button
            OutlinedButton(
                onClick = onResetToBeginning,
                shape = PillShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.RestartAlt,
                    contentDescription = null,
                    tint = InkCharcoal,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Reset to Beginning (Gift #1 Only)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkCharcoal
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Jump Row
            Text(
                text = "JUMP TO MILESTONE",
                style = MaterialTheme.typography.labelSmall,
                color = InkCharcoal,
                letterSpacing = 1.0.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(5, 8, 12, 15, 20, 21, 25, 30, 35, 40).forEach { milestone ->
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(if (unlockedCount >= milestone) ChampagneRoseLight else SurfaceContainer)
                            .border(1.dp, BorderWarm, PillShape)
                            .clickable { onJumpToGift(milestone) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Gift #$milestone",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (unlockedCount >= milestone) DustyTerracotta else InkCharcoal,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
