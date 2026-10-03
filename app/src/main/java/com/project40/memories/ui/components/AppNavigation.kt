package com.project40.memories.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.R
import com.project40.memories.ui.theme.BorderWarm
import com.project40.memories.ui.theme.ChampagneRoseLight
import com.project40.memories.ui.theme.CrispOffWhite
import com.project40.memories.ui.theme.DustyTerracotta
import com.project40.memories.ui.theme.InkCharcoal
import com.project40.memories.ui.theme.InkEspresso
import com.project40.memories.ui.theme.PillShape
import com.project40.memories.ui.theme.WarmAlabaster

enum class Screen(val title: String, val subtitle: String) {
    ONBOARDING("Project 40", "Surprise #1 • The Prologue"),
    JOURNEY("Project 40", "The 40 Birthday Quest"),
    MEMORIES("Project 40", "Memory Lane • 40 Keepsakes"),
    VOUCHERS("Project 40", "The Voucher Vault"),
    ARCHIVE("Project 40", "Love & Testimonial Archive")
}

@Composable
fun AppHeader(
    currentScreen: Screen,
    onBackClick: (() -> Unit)? = null,
    onEmblemClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    unlockedCount: Int = 1,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmAlabaster.copy(alpha = 0.95f))
            .border(width = 0.5.dp, color = BorderWarm.copy(alpha = 0.5f))
            .statusBarsPadding()
            .height(58.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBackClick != null && currentScreen != Screen.JOURNEY) {
                // Return to Journey button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(PillShape)
                        .background(ChampagneRoseLight.copy(alpha = 0.5f))
                        .clickable { onBackClick() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back to Journey",
                        tint = DustyTerracotta,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Journey",
                        style = MaterialTheme.typography.labelSmall,
                        color = DustyTerracotta,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            } else {
                // Project 40 Emblem (Host controls on click)
                Image(
                    painter = painterResource(id = R.drawable.ic_project40_emblem),
                    contentDescription = "Project 40 Emblem",
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .clickable { onEmblemClick() }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Column {
                Text(
                    text = currentScreen.title.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = DustyTerracotta,
                    letterSpacing = 1.4.sp
                )
                Text(
                    text = if (currentScreen == Screen.JOURNEY && unlockedCount >= 40) "The 40 Keepsakes Vault" else currentScreen.subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = InkEspresso
                )
            }
        }

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .border(1.dp, DustyTerracotta.copy(alpha = 0.4f), CircleShape)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_teju_portrait),
                contentDescription = "Teju's Portrait",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(34.dp).clip(CircleShape)
            )
        }
    }
}

@Composable
fun FloatingBottomNavigation(
    currentScreen: Screen,
    onSelectScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(28.dp),
                    spotColor = InkEspresso.copy(alpha = 0.12f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(CrispOffWhite.copy(alpha = 0.96f))
                .border(1.dp, BorderWarm, RoundedCornerShape(28.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavButton(
                iconSelected = Icons.Filled.CardGiftcard,
                iconUnselected = Icons.Outlined.CardGiftcard,
                label = "Journey",
                isSelected = currentScreen == Screen.JOURNEY,
                onClick = { onSelectScreen(Screen.JOURNEY) }
            )
            NavButton(
                iconSelected = Icons.Filled.PhotoLibrary,
                iconUnselected = Icons.Outlined.PhotoLibrary,
                label = "Memories",
                isSelected = currentScreen == Screen.MEMORIES,
                onClick = { onSelectScreen(Screen.MEMORIES) }
            )
            NavButton(
                iconSelected = Icons.Filled.ConfirmationNumber,
                iconUnselected = Icons.Outlined.ConfirmationNumber,
                label = "Vouchers",
                isSelected = currentScreen == Screen.VOUCHERS,
                onClick = { onSelectScreen(Screen.VOUCHERS) }
            )
            NavButton(
                iconSelected = Icons.Filled.AutoStories,
                iconUnselected = Icons.Outlined.AutoStories,
                label = "Archive",
                isSelected = currentScreen == Screen.ARCHIVE,
                onClick = { onSelectScreen(Screen.ARCHIVE) }
            )
        }
    }
}

@Composable
private fun NavButton(
    iconSelected: ImageVector,
    iconUnselected: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val pillBg by animateColorAsState(
        targetValue = if (isSelected) ChampagneRoseLight.copy(alpha = 0.45f) else Color.Transparent,
        label = "pillBg"
    )
    val iconTint by animateColorAsState(
        targetValue = if (isSelected) DustyTerracotta else InkCharcoal.copy(alpha = 0.75f),
        label = "iconTint"
    )

    Column(
        modifier = Modifier
            .clip(PillShape)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(PillShape)
                .background(pillBg)
                .padding(horizontal = 14.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) iconSelected else iconUnselected,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = if (isSelected) DustyTerracotta else InkCharcoal,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
