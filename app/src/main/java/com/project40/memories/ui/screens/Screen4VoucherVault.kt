package com.project40.memories.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.data.model.Voucher
import com.project40.memories.ui.MainUiState
import com.project40.memories.ui.components.ScratchCard
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
import com.project40.memories.ui.theme.SurfaceContainerLow
import com.project40.memories.ui.theme.WarmAlabaster

@Composable
fun Screen4VoucherVault(
    state: MainUiState,
    onScratchProgress: (voucherId: Int, progress: Float) -> Unit,
    onRedeemVoucher: (voucherId: Int) -> Unit,
    onBackToJourney: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val visibleVouchers = state.vouchers.filter {
        state.unlockedGiftIds.contains(it.giftNumber) || state.isDebugUnlockAll
    }
    val unscratchedCount = visibleVouchers.count { (state.scratchedVouchers[it.id] ?: 0f) < 0.5f }
    val availableCount = visibleVouchers.count {
        val pct = state.scratchedVouchers[it.id] ?: 0f
        pct >= 0.5f && !state.redeemedVoucherIds.contains(it.id)
    }
    val redeemedCount = visibleVouchers.count { state.redeemedVoucherIds.contains(it.id) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmAlabaster)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            if (onBackToJourney != null) {
                Row(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(SoftIvory)
                        .border(1.dp, BorderWarm, PillShape)
                        .clickable { onBackToJourney() }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = InkEspresso,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Back to 40 Journey",
                        style = MaterialTheme.typography.labelSmall,
                        color = InkEspresso,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            // Header Section
            Column {
                Text(
                    text = "SECRET KEEPSAKES • 40TH EDITION",
                    style = MaterialTheme.typography.labelSmall,
                    color = DustyTerracotta,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "The Voucher Vault",
                    style = MaterialTheme.typography.displayMedium,
                    color = InkEspresso
                )
                Text(
                    text = "Touch & rub to scratch away the golden foil and reveal your passes.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkCharcoal
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Vault Metrics Pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow)
                        .border(1.dp, BorderWarm, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Stars,
                            contentDescription = null,
                            tint = DustyTerracotta,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$unscratchedCount Unscratched",
                            style = MaterialTheme.typography.labelMedium,
                            color = InkEspresso
                        )
                    }
                    Text(text = "•", color = BorderWarm)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Redeem,
                            contentDescription = null,
                            tint = MutedSage,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$availableCount Available",
                            style = MaterialTheme.typography.labelMedium,
                            color = InkEspresso
                        )
                    }
                    Text(text = "•", color = BorderWarm)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Inventory2,
                            contentDescription = null,
                            tint = InkCharcoal,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$redeemedCount Redeemed",
                            style = MaterialTheme.typography.labelMedium,
                            color = InkCharcoal
                        )
                    }
                }
            }
        }

        if (visibleVouchers.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SoftIvory)
                        .border(1.dp, BorderWarm, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = DustyTerracotta,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No Vouchers Unlocked Yet",
                            style = MaterialTheme.typography.titleMedium,
                            color = InkEspresso
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Continue your 40 Journey to discover and unseal secret coupon passes!",
                            style = MaterialTheme.typography.bodySmall,
                            color = InkCharcoal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            // Voucher Cards
            items(visibleVouchers, key = { it.id }) { voucher ->
                val progress = state.scratchedVouchers[voucher.id] ?: 0f
                val isScratchedRevealed = progress >= 0.55f
                val isRedeemed = state.redeemedVoucherIds.contains(voucher.id)

                VoucherCardItem(
                    voucher = voucher,
                    progress = progress,
                    isRevealed = isScratchedRevealed,
                    isRedeemed = isRedeemed,
                    onProgressChange = { pct -> onScratchProgress(voucher.id, pct) },
                    onRedeem = { onRedeemVoucher(voucher.id) }
                )
            }
        }

        // Voucher Promise Note
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(16.dp), spotColor = InkEspresso.copy(alpha = 0.04f))
                    .clip(RoundedCornerShape(16.dp))
                    .background(SoftIvory)
                    .border(1.dp, BorderWarm, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ChampagneRoseLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = DustyTerracotta,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Voucher Promise",
                            style = MaterialTheme.typography.labelLarge,
                            color = InkEspresso
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Every scratch pass is an irrevocable covenant crafted with deep love. Can be claimed sequentially or stockpiled for lazy rain afternoons.",
                            style = MaterialTheme.typography.bodySmall,
                            color = InkCharcoal,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(84.dp)) // Space for floating bottom dock
        }
    }
}

@Composable
private fun VoucherCardItem(
    voucher: Voucher,
    progress: Float,
    isRevealed: Boolean,
    isRedeemed: Boolean,
    onProgressChange: (Float) -> Unit,
    onRedeem: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp), spotColor = InkEspresso.copy(alpha = 0.08f))
            .clip(RoundedCornerShape(20.dp))
            .background(if (isRedeemed) SurfaceContainerLow else CrispOffWhite)
            .border(1.dp, BorderWarm, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column {
            // Card Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(ChampagneRoseLight)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "GIFT #${voucher.giftNumber}",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = DustyTerracotta,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = voucher.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = InkEspresso
                    )
                }

                // Scratch progress pill
                Box(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(if (isRevealed) MutedSageLight else SurfaceContainer)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isRevealed) Icons.Filled.CheckCircle else Icons.Filled.TouchApp,
                            contentDescription = null,
                            tint = if (isRevealed) MutedSageDeep else DustyTerracotta,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when {
                                isRedeemed -> "Redeemed"
                                isRevealed -> "Unsealed!"
                                progress > 0f -> "${(progress * 100).toInt()}% Scratched"
                                else -> "Rub to Scratch"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = if (isRevealed) MutedSageDeep else DustyTerracotta,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Interactive Scratch Card Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SoftIvory)
                    .border(1.dp, BorderWarm, RoundedCornerShape(14.dp))
            ) {
                ScratchCard(
                    isRevealed = isRevealed || isRedeemed,
                    onProgress = onProgressChange,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Revealed Secret Reward Layout
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SoftIvory)
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = getVoucherIcon(voucher.iconName),
                                contentDescription = null,
                                tint = DustyTerracotta,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = voucher.headline,
                                style = MaterialTheme.typography.titleMedium,
                                color = InkEspresso,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = voucher.detail,
                                style = MaterialTheme.typography.bodySmall,
                                color = InkCharcoal,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Unscratched Prompt Watermark if untouched
                if (!isRevealed && !isRedeemed && progress < 0.1f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(CrispOffWhite.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AutoFixHigh,
                                    contentDescription = null,
                                    tint = InkEspresso,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Rub surface to scratch & reveal",
                                style = MaterialTheme.typography.titleSmall,
                                color = InkEspresso,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "SECRET GOLDEN FOIL SEALED",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = InkCharcoal,
                                letterSpacing = 1.0.sp
                            )
                        }
                    }
                }

                // Redeemed Stamp Watermark
                if (isRedeemed) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(InkEspresso.copy(alpha = 0.05f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .rotate(-8f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(DustyTerracottaDark)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "REDEEMED & COMPLETED",
                                style = MaterialTheme.typography.labelMedium,
                                color = CrispOffWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.4.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Card Footer & Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "PERK CONDITION",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = InkCharcoal,
                        letterSpacing = 1.0.sp
                    )
                    Text(
                        text = voucher.condition,
                        style = MaterialTheme.typography.bodySmall,
                        color = InkEspresso
                    )
                }

                when {
                    isRedeemed -> {
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(SurfaceContainer)
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Completed",
                                style = MaterialTheme.typography.labelMedium,
                                color = InkCharcoal
                            )
                        }
                    }

                    isRevealed -> {
                        Button(
                            onClick = onRedeem,
                            shape = PillShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DustyTerracotta,
                                contentColor = CrispOffWhite
                            ),
                            modifier = Modifier.height(42.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Redeem Now",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = null,
                                    tint = InkCharcoal,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Scratch 60%",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = InkCharcoal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getVoucherIcon(name: String): ImageVector {
    return when (name) {
        "bedtime" -> Icons.Filled.Bedtime
        "spa" -> Icons.Filled.Spa
        "favorite" -> Icons.Filled.Favorite
        "coffee" -> Icons.Filled.Coffee
        "dinner_dining" -> Icons.Filled.DinnerDining
        else -> Icons.Filled.Stars
    }
}
