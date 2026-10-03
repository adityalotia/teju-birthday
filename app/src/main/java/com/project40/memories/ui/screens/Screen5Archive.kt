package com.project40.memories.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.R
import com.project40.memories.data.model.BucketListItem
import com.project40.memories.data.model.Reason
import com.project40.memories.data.model.Testimonial
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
import com.project40.memories.ui.theme.SurfaceContainerLow
import com.project40.memories.ui.theme.WarmAlabaster

@Composable
fun Screen5Archive(
    state: MainUiState,
    onTabSelect: (Int) -> Unit,
    onNavigateReason: (Int) -> Unit,
    onToggleReasonHeart: (Int) -> Unit,
    onToggleBucketItem: (Int) -> Unit,
    onAddBucketItem: (title: String, note: String) -> Unit,
    onSetReasonsScrollModal: (Boolean) -> Unit,
    onBackToJourney: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmAlabaster)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
            // Section Header
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "KEEPSAKE VOLUME V",
                        style = MaterialTheme.typography.labelSmall,
                        color = DustyTerracotta,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(AccentGold))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MILESTONE 40",
                        style = MaterialTheme.typography.labelSmall,
                        color = MutedSage,
                        letterSpacing = 1.0.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Love & Testimonial Archive",
                    style = MaterialTheme.typography.headlineLarge,
                    color = InkEspresso
                )
                Text(
                    text = "Words, wishes, and 40 reasons why you light up the world.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkCharcoal
                )
            }
        }

        item {
            // Segmented Capsule Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(PillShape)
                    .background(SurfaceContainerHigh)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ArchiveSegmentTab(
                    label = "40 Reasons",
                    isSelected = state.activeArchiveTab == 0,
                    onClick = { onTabSelect(0) },
                    modifier = Modifier.weight(1f)
                )
                ArchiveSegmentTab(
                    label = "Family & Friends",
                    isSelected = state.activeArchiveTab == 1,
                    onClick = { onTabSelect(1) },
                    modifier = Modifier.weight(1f)
                )
                ArchiveSegmentTab(
                    label = "Bucket List",
                    isSelected = state.activeArchiveTab == 2,
                    onClick = { onTabSelect(2) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        when (state.activeArchiveTab) {
            0 -> {
                // TAB 1: 40 REASONS
                item {
                    ReasonsCarouselSection(
                        reasons = state.reasons,
                        currentIndex = state.currentReasonIndex,
                        favoriteReasons = state.favoriteReasonNumbers,
                        onNavigate = onNavigateReason,
                        onToggleHeart = onToggleReasonHeart,
                        onOpenScrollModal = { onSetReasonsScrollModal(true) }
                    )
                }
            }

            1 -> {
                // TAB 2: FAMILY & FRIENDS
                items(state.testimonials, key = { it.id }) { testimonial ->
                    TestimonialCardItem(testimonial = testimonial)
                }
            }

            2 -> {
                // TAB 3: BUCKET LIST
                item {
                    val completedCount = state.completedBucketListIds.size
                    val totalCount = state.bucketList.size
                    val fraction = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

                    // Bucket List Header Metric
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceContainerHigh)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "MILESTONE ASPIRATIONS",
                                style = MaterialTheme.typography.labelSmall,
                                color = MutedSage,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$completedCount of $totalCount Dreams Fulfilled",
                                style = MaterialTheme.typography.headlineSmall,
                                color = InkEspresso
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(MutedSageLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${(fraction * 100).toInt()}%",
                                style = MaterialTheme.typography.labelMedium,
                                color = MutedSageDeep,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                items(state.bucketList, key = { it.id }) { item ->
                    val isChecked = state.completedBucketListIds.contains(item.id)
                    BucketListItemRow(
                        item = item,
                        isChecked = isChecked,
                        onToggle = { onToggleBucketItem(item.id) }
                    )
                }

                item {
                    Button(
                        onClick = { showAddDialog = true },
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
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add to Our 40 Bucket List",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(84.dp)) // Space for floating bottom dock
        }
    }

    // Literary Scroll Modal Dialog
    if (state.isReasonsScrollModalOpen) {
        LiteraryScrollDialog(
            reasons = state.reasons,
            onDismiss = { onSetReasonsScrollModal(false) }
        )
    }

    // Add Bucket List Item Dialog
    if (showAddDialog) {
        AddBucketItemDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, note ->
                onAddBucketItem(title, note)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun ArchiveSegmentTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) CrispOffWhite else Color.Transparent,
        label = "tabBg"
    )
    val textTint by animateColorAsState(
        targetValue = if (isSelected) DustyTerracotta else InkCharcoal,
        label = "tabText"
    )

    Box(
        modifier = modifier
            .clip(PillShape)
            .background(bg)
            .clickable { onClick() }
            .padding(vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = textTint,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun ReasonsCarouselSection(
    reasons: List<Reason>,
    currentIndex: Int,
    favoriteReasons: Set<Int>,
    onNavigate: (Int) -> Unit,
    onToggleHeart: (Int) -> Unit,
    onOpenScrollModal: () -> Unit
) {
    val currentReason = reasons.getOrNull(currentIndex) ?: return
    val isHearted = favoriteReasons.contains(currentReason.number)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Deckled Card Stack
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background Deckled Layer 2
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(260.dp)
                    .rotate(2.5f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(SurfaceContainer.copy(alpha = 0.7f))
            )
            // Background Deckled Layer 1
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .height(270.dp)
                    .rotate(-1.5f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(SurfaceContainerHigh.copy(alpha = 0.85f))
            )
            // Top Active Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .shadow(8.dp, RoundedCornerShape(22.dp), spotColor = InkEspresso.copy(alpha = 0.08f))
                    .clip(RoundedCornerShape(22.dp))
                    .background(CrispOffWhite)
                    .border(1.dp, BorderWarm, RoundedCornerShape(22.dp))
                    .padding(22.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = DustyTerracotta,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "REASON #${currentReason.number} OF 40",
                                style = MaterialTheme.typography.labelSmall,
                                color = DustyTerracotta,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.0.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerLow)
                                .clickable { onToggleHeart(currentReason.number) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isHearted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Heart",
                                tint = if (isHearted) DustyTerracotta else InkCharcoal,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Card Quote
                    Text(
                        text = "“${currentReason.text}”",
                        style = MaterialTheme.typography.headlineMedium.copy(fontStyle = FontStyle.Italic),
                        color = InkEspresso,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "HANDPICKED MEMORY",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = InkCharcoal,
                            letterSpacing = 1.0.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Swipe or Tap",
                                style = MaterialTheme.typography.labelSmall,
                                color = DustyTerracotta,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = null,
                                tint = DustyTerracotta,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Prev / Next Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(PillShape)
                    .background(SurfaceContainerLow)
                    .border(1.dp, BorderWarm, PillShape)
                    .clickable { onNavigate(-1) }
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = "Prev",
                        tint = InkEspresso,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Prev", style = MaterialTheme.typography.labelMedium, color = InkEspresso)
                }
            }

            Text(
                text = "${currentIndex + 1} / ${reasons.size}",
                style = MaterialTheme.typography.bodySmall,
                color = InkCharcoal,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .clip(PillShape)
                    .background(SurfaceContainerLow)
                    .border(1.dp, BorderWarm, PillShape)
                    .clickable { onNavigate(1) }
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Next", style = MaterialTheme.typography.labelMedium, color = InkEspresso)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Next",
                        tint = InkEspresso,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // View All as Literary Scroll Button
        Button(
            onClick = onOpenScrollModal,
            shape = PillShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = SurfaceContainerHigh,
                contentColor = InkEspresso
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ViewAgenda,
                contentDescription = null,
                tint = ChampagneRose,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "View All 40 as Literary Scroll",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Archivist Note
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLow)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Filled.Spa,
                    contentDescription = null,
                    tint = MutedSage,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "ARCHIVIST NOTE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MutedSage,
                        letterSpacing = 1.0.sp
                    )
                    Text(
                        text = "Collected over four seasons, from whispered morning reflections to handwritten sticky notes folded into vintage envelopes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = InkCharcoal
                    )
                }
            }
        }
    }
}

@Composable
private fun TestimonialCardItem(testimonial: Testimonial) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(18.dp), spotColor = InkEspresso.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(18.dp))
            .background(CrispOffWhite)
            .border(1.dp, BorderWarm, RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(ChampagneRoseLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = testimonial.author.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = DustyTerracotta,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = testimonial.author,
                            style = MaterialTheme.typography.titleMedium,
                            color = InkEspresso
                        )
                        Text(
                            text = testimonial.relationship.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = DustyTerracotta,
                            letterSpacing = 1.0.sp
                        )
                    }
                }

                if (testimonial.isVideo) {
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(SurfaceContainer)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = testimonial.videoDuration ?: "Video",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = InkCharcoal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Video Preview or Quote Box
            if (testimonial.isVideo) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_teju_portrait),
                        contentDescription = "Video Thumbnail",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(InkEspresso.copy(alpha = 0.35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(CrispOffWhite.copy(alpha = 0.95f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play Video",
                                tint = DustyTerracotta,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Quote Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLow)
                    .padding(14.dp)
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Filled.FormatQuote,
                        contentDescription = null,
                        tint = ChampagneRose.copy(alpha = 0.5f),
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = testimonial.quote,
                        style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                        color = InkEspresso,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = testimonial.fullLetter,
                        style = MaterialTheme.typography.bodySmall,
                        color = InkCharcoal,
                        lineHeight = 19.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = testimonial.dateText,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = InkCharcoal
            )
        }
    }
}

@Composable
private fun BucketListItemRow(
    item: BucketListItem,
    isChecked: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp), spotColor = InkEspresso.copy(alpha = 0.04f))
            .clip(RoundedCornerShape(14.dp))
            .background(CrispOffWhite)
            .border(1.dp, BorderWarm, RoundedCornerShape(14.dp))
            .clickable { onToggle() }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isChecked) MutedSage else SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Completed",
                        tint = CrispOffWhite,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isChecked) InkCharcoal.copy(alpha = 0.7f) else InkEspresso,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.note,
                    style = MaterialTheme.typography.bodySmall,
                    color = InkCharcoal
                )
            }
        }
    }
}

@Composable
private fun LiteraryScrollDialog(
    reasons: List<Reason>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "COMPLETE COMPENDIUM",
                        style = MaterialTheme.typography.labelSmall,
                        color = DustyTerracotta,
                        letterSpacing = 1.0.sp
                    )
                    Text(
                        text = "The 40 Reasons",
                        style = MaterialTheme.typography.headlineSmall,
                        color = InkEspresso
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer)
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InkCharcoal, modifier = Modifier.size(16.dp))
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(reasons, key = { it.number }) { reason ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "#${reason.number}",
                                style = MaterialTheme.typography.labelSmall,
                                color = DustyTerracotta,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "“${reason.text}”",
                                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                                color = InkEspresso
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = DustyTerracotta)
            ) {
                Text(text = "Close Compendium", style = MaterialTheme.typography.labelMedium)
            }
        },
        containerColor = SoftIvory
    )
}

@Composable
private fun AddBucketItemDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, note: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add to 40 Bucket List",
                style = MaterialTheme.typography.headlineSmall,
                color = InkEspresso
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Dream / Milestone") },
                    placeholder = { Text("e.g. Stargaze in Atacama Desert") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DustyTerracotta,
                        unfocusedBorderColor = BorderWarm
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Target / Note") },
                    placeholder = { Text("e.g. With Kabir & Reyansh in 2027") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DustyTerracotta,
                        unfocusedBorderColor = BorderWarm
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank()) onAdd(title, note) },
                shape = PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = DustyTerracotta)
            ) {
                Text(text = "Save Wish", style = MaterialTheme.typography.labelMedium)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                shape = PillShape,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh, contentColor = InkEspresso)
            ) {
                Text(text = "Cancel", style = MaterialTheme.typography.labelMedium)
            }
        },
        containerColor = SoftIvory
    )
}
