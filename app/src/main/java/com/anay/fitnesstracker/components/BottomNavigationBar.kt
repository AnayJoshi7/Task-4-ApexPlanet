package com.anay.fitnesstracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anay.fitnesstracker.Routes

private val BottomNavBg = Color(0xFF6B6E70)
private val BottomNavIconBg = Color(0xFF1E1E1E)
private val SelectedTabBg = Color(0xFF3DDC84).copy(alpha = 0.30f)

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(BottomNavBg)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = selectedTab == "Home",
            onClick = { onNavigate(Routes.DASHBOARD) }
        )
        BottomNavItem(
            icon = Icons.Default.FitnessCenter,
            label = "Workouts",
            selected = selectedTab == "Workouts",
            onClick = { onNavigate(Routes.WORKOUTS) }
        )
        BottomNavItem(
            icon = Icons.Default.Leaderboard,
            label = "Progress",
            selected = selectedTab == "Progress",
            onClick = { onNavigate(Routes.PROGRESS) }
        )
        BottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = selectedTab == "Profile",
            onClick = { onNavigate(Routes.PROFILE) }
        )
    }
}

@Composable
private fun RowScope.BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) SelectedTabBg else Color.Transparent)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BottomNavIconBg,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = BottomNavIconBg,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}