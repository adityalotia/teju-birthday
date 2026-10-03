package com.project40.memories.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project40.memories.R
import com.project40.memories.ui.MainUiState
import com.project40.memories.ui.theme.AccentGold
import com.project40.memories.ui.theme.BorderWarm
import com.project40.memories.ui.theme.ChampagneRoseLight
import com.project40.memories.ui.theme.CrispOffWhite
import com.project40.memories.ui.theme.DustyTerracotta
import com.project40.memories.ui.theme.DustyTerracottaDark
import com.project40.memories.ui.theme.InkCharcoal
import com.project40.memories.ui.theme.InkEspresso
import com.project40.memories.ui.theme.MutedSage
import com.project40.memories.ui.theme.PillShape
import com.project40.memories.ui.theme.SoftIvory
import com.project40.memories.ui.theme.SurfaceContainerLow
import com.project40.memories.ui.theme.WarmAlabaster

@Composable
fun Screen0Onboarding(
    state: MainUiState,
    onBeginJourney: () -> Unit,
    onBackToJourney: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmAlabaster)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (onBackToJourney != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
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
            }
        }
        // Decorative Monogram Header
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(DustyTerracotta, DustyTerracottaDark, InkEspresso)
                    )
                )
                .border(2.dp, AccentGold.copy(alpha = 0.5f), CircleShape)
                .shadow(12.dp, CircleShape, spotColor = DustyTerracotta),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "40",
                    fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold,
                    lineHeight = 32.sp
                )
                Text(
                    text = "MONOGRAM",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                    color = CrispOffWhite.copy(alpha = 0.8f),
                    letterSpacing = 1.6.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Milestone Pill
        Row(
            modifier = Modifier
                .clip(PillShape)
                .background(ChampagneRoseLight.copy(alpha = 0.45f))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(DustyTerracotta)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Surprise 01 of 40 • The Adventure Begins",
                style = MaterialTheme.typography.labelSmall,
                color = DustyTerracotta,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Hero Typography
        Text(
            text = "A milestone written in golden light",
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            color = DustyTerracotta
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Happy 40th Birthday, Teju.",
            style = MaterialTheme.typography.displayMedium,
            color = InkEspresso,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(2.dp)
                .clip(PillShape)
                .background(AccentGold.copy(alpha = 0.7f))
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Archival Letter Parchment Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = InkEspresso.copy(alpha = 0.08f))
                .clip(RoundedCornerShape(20.dp))
                .background(SoftIvory)
                .border(1.dp, BorderWarm, RoundedCornerShape(20.dp))
                .padding(22.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PROLOGUE & ARCHIVE",
                        style = MaterialTheme.typography.labelSmall,
                        color = DustyTerracotta,
                        letterSpacing = 1.2.sp
                    )
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Hero Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLow)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_teju_portrait),
                        contentDescription = "Chapter I: The Celebration",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, InkEspresso.copy(alpha = 0.65f))
                                )
                            )
                    )
                    Text(
                        text = "Chapter I: The Celebration",
                        style = MaterialTheme.typography.labelMedium,
                        color = CrispOffWhite,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Narrative Letter
                Text(
                    text = "Today is yours. You always craft effortless magic for everyone around you—today, the universe pauses to honor you. Follow this trail of 40 milestones curated with all my heart: memories to cherish, treasures to unwrap, and quiet moments made just for you.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = InkEspresso,
                    lineHeight = 25.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Devotion Sign-off
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Always, with all my love & devotion ♥",
                        style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic),
                        color = DustyTerracotta
                    )
                    Text(
                        text = "Written from the heart • Your Adu",
                        style = MaterialTheme.typography.bodySmall,
                        color = InkCharcoal
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = null,
                            tint = InkCharcoal.copy(alpha = 0.7f),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Unsealed at sunrise",
                            style = MaterialTheme.typography.bodySmall,
                            color = InkCharcoal.copy(alpha = 0.8f)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Forever",
                            style = MaterialTheme.typography.bodySmall,
                            color = InkEspresso,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // CTA Button
        Button(
            onClick = onBeginJourney,
            shape = PillShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = DustyTerracotta,
                contentColor = CrispOffWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .shadow(12.dp, PillShape, spotColor = DustyTerracotta.copy(alpha = 0.4f))
        ) {
            Text(
                text = "Begin the 40 Journey",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Metrics Banner
        val unlockedCount = state.unlockedGiftIds.size
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, BorderWarm, RoundedCornerShape(16.dp))
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MetricItem(number = "40", label = "Surprises", color = DustyTerracotta)
            Box(modifier = Modifier.width(1.dp).height(24.dp).background(BorderWarm))
            MetricItem(number = String.format("%02d", unlockedCount), label = "Unlocked", color = MutedSage)
            Box(modifier = Modifier.width(1.dp).height(24.dp).background(BorderWarm))
            MetricItem(number = String.format("%02d", (40 - unlockedCount).coerceAtLeast(0)), label = "Awaiting", color = AccentGold)
        }

        Spacer(modifier = Modifier.height(80.dp)) // Space for floating bottom dock
    }
}

@Composable
private fun MetricItem(number: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = InkCharcoal
        )
    }
}
