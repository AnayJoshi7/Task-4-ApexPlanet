package com.anay.fitnesstracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anay.fitnesstracker.Routes


private val CardBackground = Color(0xFF070708)
private val PrimaryGreen = Color(0xFF27D07F)
private val TextWhite = Color(0xFFFFFFFF)
private val BottomNavBg = Color(0xFF6B6E70)
private val BottomNavIconBg = Color(0xFF1E1E1E)

@Composable
fun ProgressScreen(
    onNavigate: (String) -> Unit
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1B1C1E),
            Color(0xFF131416),
            Color(0xFF2C2E31),
            Color(0xFF111214)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        // Screen Title
        Text(
            text = "Progress",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Section Title
        Text(
            text = "Weekly Report",
            modifier = Modifier.fillMaxWidth(),
            color = PrimaryGreen,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Bar Chart Card
        WeeklyChartCard()

        Spacer(modifier = Modifier.height(28.dp))

        // Weekly Summary Card
        ThisWeekSummaryCard()

        Spacer(modifier = Modifier.weight(1f))

        // Divider
        HorizontalDivider(
            color = Color(0xFF8E9094),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Bottom Navigation Bar
        BottomNavigationBar(onNavigate)

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun WeeklyChartCard() {
    // Days and relative bar heights in dp matching the mockup
    val weeklyData = listOf(
        Pair("M", 28.dp),
        Pair("T", 60.dp),
        Pair("W", 16.dp),
        Pair("T", 46.dp),
        Pair("F", 34.dp),
        Pair("S", 34.dp),
        Pair("S", 46.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .padding(vertical = 32.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bars
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            weeklyData.forEach { (_, height) ->
                Box(
                    modifier = Modifier
                        .width(18.dp)
                        .height(height)
                        .clip(RoundedCornerShape(3.dp))
                        .background(TextWhite)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Day Labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weeklyData.forEach { (day, _) ->
                Text(
                    text = day,
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.width(18.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ThisWeekSummaryCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .padding(vertical = 26.dp, horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This Week",
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Aligned Metrics Table
        Column(
            modifier = Modifier.fillMaxWidth(0.85f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            MetricRow(label = "Workouts", value = "4")
            MetricRow(label = "Calories", value = "2430")
            MetricRow(label = "Active Time", value = "4h 38m")
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Goal Completion",
            color = TextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 78% Pill Progress Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = 160.dp, height = 22.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF4A4A4D))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.78f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFA67171))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "78%",
                color = TextWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextWhite,
            fontSize = 17.sp,
            modifier = Modifier.weight(1.3f)
        )
        Text(
            text = ":",
            color = TextWhite,
            fontSize = 17.sp,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = value,
            color = TextWhite,
            fontSize = 17.sp,
            modifier = Modifier.weight(0.9f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

@Composable
private fun BottomNavigationBar(
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(BottomNavBg)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = true,
            onClick = {
                onNavigate(Routes.DASHBOARD)
            }
        )
        BottomNavItem(
            icon = Icons.Default.FitnessCenter,
            label = "Workouts",
            selected = false,
            onClick = {
                onNavigate(Routes.WORKOUTS)
            }
        )
        BottomNavItem(
            icon = Icons.Default.Leaderboard,
            label = "Progress",
            selected = false,
            onClick = {
                onNavigate(Routes.PROGRESS)
            }
        )
        BottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = false,
            onClick = {
                onNavigate(Routes.PROFILE)
            }
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.clickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BottomNavIconBg,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            color = BottomNavIconBg,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}