package com.project40.memories.ui.screens

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.R
import com.project40.memories.data.model.Memory
import com.project40.memories.data.model.MemoryCardStyle
import com.project40.memories.data.model.MemoryCategory
import com.project40.memories.ui.MainUiState
import com.project40.memories.ui.theme.AccentGold
import com.project40.memories.ui.theme.BorderWarm
import com.project40.memories.ui.theme.ChampagneRose
import com.project40.memories.ui.theme.ChampagneRoseLight
import com.project40.memories.ui.theme.CrispOffWhite
import com.project40.memories.ui.theme.DustyTerracotta
import com.project40.memories.ui.theme.InkCharcoal
import com.project40.memories.ui.theme.InkEspresso
import com.project40.memories.ui.theme.MutedSage
import com.project40.memories.ui.theme.MutedSageLight
import com.project40.memories.ui.theme.PillShape
import com.project40.memories.ui.theme.SoftIvory
import com.project40.memories.ui.theme.SurfaceContainer
import com.project40.memories.ui.theme.SurfaceContainerHigh
import com.project40.memories.ui.theme.SurfaceContainerLow
import com.project40.memories.ui.theme.WarmAlabaster

@Composable
fun Screen2MemoryLane(
    state: MainUiState,
    onToggleFavorite: (Int) -> Unit,
    onFilterSelect: (MemoryCategory) -> Unit,
    onBackToJourney: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val filteredMemories = when (state.selectedMemoryFilter) {
        MemoryCategory.ALL -> state.memories
        MemoryCategory.FAVORITES -> state.memories.filter { state.favoriteMemoryIds.contains(it.id) }
        MemoryCategory.EARLY_DAYS -> state.memories.filter { it.category == MemoryCategory.EARLY_DAYS }
        MemoryCategory.ADVENTURES -> state.memories.filter { it.category == MemoryCategory.ADVENTURES }
        MemoryCategory.MOTHERHOOD -> state.memories.filter { it.category == MemoryCategory.MOTHERHOOD }
        MemoryCategory.FAMILY -> state.memories.filter { it.category == MemoryCategory.FAMILY }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmAlabaster)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
                Row(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(ChampagneRoseLight.copy(alpha = 0.45f))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoStories,
                        contentDescription = null,
                        tint = DustyTerracotta,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MILESTONE MEMOIR",
                        style = MaterialTheme.typography.labelSmall,
                        color = DustyTerracotta,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "40 Memories Across Time",
                    style = MaterialTheme.typography.headlineLarge,
                    color = InkEspresso
                )
                Text(
                    text = "A curated chronicle of laughter, milestones & quiet moments.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkCharcoal
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Indicator Strip
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
                            tint = AccentGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Memory Ledger Unlocked",
                            style = MaterialTheme.typography.labelMedium,
                            color = InkEspresso
                        )
                    }
                    Text(
                        text = "${state.memories.size} / 40",
                        style = MaterialTheme.typography.titleMedium,
                        color = DustyTerracotta,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            // Memory Category Filter Bar
            MemoryFilterBar(
                selected = state.selectedMemoryFilter,
                favoritesCount = state.favoriteMemoryIds.size,
                onSelect = onFilterSelect
            )
        }

        // Timeline Feed with Vertical Spine Line
        items(filteredMemories, key = { it.id }) { memory ->
            val isFavorite = state.favoriteMemoryIds.contains(memory.id)
            TimelineMemoryItem(
                memory = memory,
                isFavorite = isFavorite,
                onToggleFavorite = { onToggleFavorite(memory.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(84.dp)) // Clearance for floating bottom dock
        }
    }
}

@Composable
private fun MemoryFilterBar(
    selected: MemoryCategory,
    favoritesCount: Int,
    onSelect: (MemoryCategory) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MemoryFilterChip(
            label = "All Memories",
            count = "40",
            isSelected = selected == MemoryCategory.ALL,
            onClick = { onSelect(MemoryCategory.ALL) }
        )
        MemoryFilterChip(
            label = "Favorites",
            count = "$favoritesCount",
            icon = Icons.Filled.Favorite,
            iconTint = DustyTerracotta,
            isSelected = selected == MemoryCategory.FAVORITES,
            onClick = { onSelect(MemoryCategory.FAVORITES) }
        )
        MemoryFilterChip(
            label = "Early Days",
            isSelected = selected == MemoryCategory.EARLY_DAYS,
            onClick = { onSelect(MemoryCategory.EARLY_DAYS) }
        )
        MemoryFilterChip(
            label = "Adventures",
            isSelected = selected == MemoryCategory.ADVENTURES,
            onClick = { onSelect(MemoryCategory.ADVENTURES) }
        )
        MemoryFilterChip(
            label = "Motherhood",
            isSelected = selected == MemoryCategory.MOTHERHOOD,
            onClick = { onSelect(MemoryCategory.MOTHERHOOD) }
        )
    }
}

@Composable
private fun MemoryFilterChip(
    label: String,
    count: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    iconTint: Color = InkCharcoal,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) DustyTerracotta else SurfaceContainer,
        label = "chipBg"
    )
    val textTint by animateColorAsState(
        targetValue = if (isSelected) CrispOffWhite else InkCharcoal,
        label = "chipText"
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
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) CrispOffWhite else iconTint,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = textTint,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
        if (count != null) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .clip(PillShape)
                    .background(if (isSelected) CrispOffWhite.copy(alpha = 0.25f) else BorderWarm)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = count,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = textTint,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TimelineMemoryItem(
    memory: Memory,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Spine Line & Milestone Node Marker
        Box(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Node Indicator Dot
            val dotColor = when (memory.cardStyle) {
                MemoryCardStyle.POLAROID -> AccentGold
                MemoryCardStyle.SCRAPBOOK -> MutedSage
                MemoryCardStyle.TERRACOTTA -> DustyTerracotta
            }
            Box(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(dotColor)
                    .border(3.dp, WarmAlabaster, CircleShape)
                    .shadow(3.dp, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Tactile Card Content
        Box(modifier = Modifier.weight(1f)) {
            when (memory.cardStyle) {
                MemoryCardStyle.POLAROID -> PolaroidMemoryCard(memory, isFavorite, onToggleFavorite)
                MemoryCardStyle.SCRAPBOOK -> ScrapbookMemoryCard(memory, isFavorite, onToggleFavorite)
                MemoryCardStyle.TERRACOTTA -> TerracottaMemoryCard(memory, isFavorite, onToggleFavorite)
            }
        }
    }
}

@Composable
private fun PolaroidMemoryCard(
    memory: Memory,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .rotate(-1.2f)
            .shadow(6.dp, RoundedCornerShape(12.dp), spotColor = InkEspresso.copy(alpha = 0.08f))
            .clip(RoundedCornerShape(12.dp))
            .background(CrispOffWhite)
            .border(1.dp, BorderWarm, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            // Photo Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainer)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_teju_portrait),
                    contentDescription = memory.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Favorite Button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(CrispOffWhite.copy(alpha = 0.9f))
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) DustyTerracotta else InkCharcoal,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Number & Location Pill
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(PillShape)
                        .background(InkEspresso.copy(alpha = 0.75f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "#${String.format("%02d", memory.id)} • ${memory.year}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = CrispOffWhite,
                        letterSpacing = 1.0.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Handwritten-feel Caption
            Text(
                text = memory.title,
                style = MaterialTheme.typography.titleMedium,
                color = InkEspresso
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = DustyTerracotta,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = memory.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = InkCharcoal
                )
            }

            if (memory.quote != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = memory.quote,
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    color = DustyTerracotta
                )
            }

            // Audio Player Bar
            if (memory.hasVoiceNote) {
                Spacer(modifier = Modifier.height(10.dp))
                AudioPlayerSnippet(
                    title = memory.audioTitle ?: "Voice Note",
                    duration = memory.audioDuration ?: "0:42",
                    isPlaying = isPlaying,
                    onTogglePlay = { isPlaying = !isPlaying }
                )
            }
        }
    }
}

@Composable
private fun ScrapbookMemoryCard(
    memory: Memory,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = InkEspresso.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(16.dp))
            .background(SoftIvory)
            .border(1.dp, BorderWarm, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(MutedSageLight.copy(alpha = 0.6f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "#${String.format("%02d", memory.id)} • ${memory.category.name}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = com.project40.memories.ui.theme.MutedSageDeep,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(CrispOffWhite.copy(alpha = 0.9f))
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) DustyTerracotta else InkCharcoal,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Photo Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainer)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_teju_portrait),
                    contentDescription = memory.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = memory.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = InkCharcoal
                )
                Text(
                    text = memory.dateText,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = ChampagneRose,
                    letterSpacing = 1.0.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = memory.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp),
                color = InkEspresso
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = memory.caption,
                style = MaterialTheme.typography.bodyMedium,
                color = InkCharcoal
            )

            if (memory.quote != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = memory.quote,
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    color = DustyTerracotta
                )
            }
        }
    }
}

@Composable
private fun TerracottaMemoryCard(
    memory: Memory,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(ChampagneRoseLight.copy(alpha = 0.25f))
            .border(1.dp, ChampagneRose.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = DustyTerracotta,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "#${String.format("%02d", memory.id)} • LIFE UNFOLDING",
                        style = MaterialTheme.typography.labelSmall,
                        color = DustyTerracotta,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.0.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(CrispOffWhite.copy(alpha = 0.9f))
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) DustyTerracotta else InkCharcoal,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = memory.dateText,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = InkCharcoal,
                letterSpacing = 1.0.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = memory.title,
                style = MaterialTheme.typography.headlineSmall,
                color = InkEspresso
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = memory.caption,
                style = MaterialTheme.typography.bodyMedium,
                color = InkCharcoal
            )

            // Audio Snippet Player (Lullaby)
            if (memory.hasVoiceNote) {
                Spacer(modifier = Modifier.height(12.dp))
                AudioPlayerSnippet(
                    title = memory.audioTitle ?: "Lullaby Audio",
                    duration = memory.audioDuration ?: "1:15",
                    isPlaying = isPlaying,
                    onTogglePlay = { isPlaying = !isPlaying }
                )
            }
        }
    }
}

@Composable
private fun AudioPlayerSnippet(
    title: String,
    duration: String,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CrispOffWhite.copy(alpha = 0.95f))
            .border(1.dp, BorderWarm, RoundedCornerShape(12.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(DustyTerracotta)
                .clickable { onTogglePlay() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "Play/Pause",
                tint = CrispOffWhite,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = InkEspresso
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = DustyTerracotta,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Audio Waveform Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val heights = listOf(8, 14, 18, 10, 6, 16, 12, 18, 6, 14, 18, 8, 16, 12, 6, 14, 10, 6)
                heights.forEachIndexed { i, h ->
                    val barColor = if (isPlaying) {
                        if (i % 2 == 0) DustyTerracotta else ChampagneRose
                    } else {
                        if (i < 8) DustyTerracotta else SurfaceContainerHigh
                    }
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(h.dp)
                            .clip(PillShape)
                            .background(barColor)
                    )
                }
            }
        }
    }
}
