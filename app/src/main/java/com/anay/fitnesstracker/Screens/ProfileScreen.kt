package com.anay.fitnesstracker.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.anay.fitnesstracker.data.UserProfile
import com.anay.fitnesstracker.data.viewmodel.FitnessViewModel

private val CardBackground = Color(0xFF070708)
private val PrimaryGreen = Color(0xFF27D07F)
private val TextWhite = Color(0xFFFFFFFF)
private val AvatarColor = Color(0xFFD4D4D6)
private val SettingsItemColor = Color(0xFF5A5B5E)
private val BottomNavBg = Color(0xFF6B6E70)
private val BottomNavIconBg = Color(0xFF1E1E1E)
private val SelectedTabBg = Color(0xFF3DDC84).copy(alpha = 0.30f)

@Composable
fun ProfileScreen(
    onNavigate: (String) -> Unit,
    fitnessViewModel: FitnessViewModel
) {
    val user by fitnessViewModel.currentUser.collectAsState()

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
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Profile",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(105.dp)
                    .clip(CircleShape)
                    .background(AvatarColor)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = user?.name?.ifBlank { "User Profile" } ?: "User Profile",
                color = PrimaryGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(22.dp))

            UserInfoCard(user = user)

            Spacer(modifier = Modifier.height(26.dp))

            SettingsCard()

            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                color = Color(0xFF8E9094),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            BottomNavigationBar(onNavigate = onNavigate)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun UserInfoCard(user: UserProfile?) {
    val calculatedAge = if (user != null && user.birthYear > 0) {
        (2026 - user.birthYear).coerceAtLeast(0)
    } else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .padding(horizontal = 22.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileInfoRow(
            label = "Age",
            value = calculatedAge?.let { "$it yrs" } ?: "-"
        )

        ProfileInfoRow(
            label = "Height",
            value = user?.heightCm?.let { "${it.toInt()} cm" } ?: "-"
        )

        ProfileInfoRow(
            label = "Current Weight",
            value = user?.weightKg?.let { "${it.toInt()} kg" } ?: "-"
        )

        ProfileInfoRow(
            label = "Goal",
            value = user?.fitnessGoal ?: "-"
        )
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = value,
            color = TextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun SettingsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .padding(vertical = 22.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            color = TextWhite,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SettingsButton(label = "Personal Information")
            SettingsButton(label = "Notifications")
            SettingsButton(label = "Appearance")
            SettingsButton(label = "About")
        }
    }
}

@Composable
private fun SettingsButton(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(SettingsItemColor)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(TextWhite),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = label,
                tint = Color.Black,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
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
            selected = false,
            onClick = { onNavigate(Routes.DASHBOARD) }
        )
        BottomNavItem(
            icon = Icons.Default.FitnessCenter,
            label = "Workouts",
            selected = false,
            onClick = { onNavigate(Routes.WORKOUTS) }
        )
        BottomNavItem(
            icon = Icons.Default.Leaderboard,
            label = "Progress",
            selected = false,
            onClick = { onNavigate(Routes.PROGRESS) }
        )
        BottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = true,
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