package com.anay.fitnesstracker.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.*
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
import com.anay.fitnesstracker.components.LogWorkoutPillButton
import com.anay.fitnesstracker.data.WorkoutScheduleHelper
import com.anay.fitnesstracker.data.viewmodel.FitnessViewModel

private val CardBackground = Color(0xFF070708)
private val PrimaryGreen = Color(0xFF27D07F)
private val TextWhite = Color(0xFFFFFFFF)
private val TextMuted = Color(0xFF9E9E9E)
private val BottomNavBg = Color(0xFF6B6E70)
private val BottomNavIconBg = Color(0xFF1E1E1E)
private val SelectedTabBg = Color(0xFF3DDC84).copy(alpha = 0.30f)

data class WorkoutCardData(
    val title: String,
    val muscles: String,
    val exercises: String,
    val route: String
)

@Composable
fun WorkoutScreen(
    onNavigate: (String) -> Unit,
    fitnessViewModel: FitnessViewModel
) {
    val user by fitnessViewModel.currentUser.collectAsState()
    val todayWorkout = WorkoutScheduleHelper.getTodayWorkout(
        workoutFrequency = user?.workoutFrequency ?: "6 Days a Week",
        splitName = user?.workoutSplit ?: "Push Pull Legs"
    )

    // Complete list of workouts according to user's weekly split frequency
    val allPossibleWorkouts = when (user?.workoutFrequency) {
        "3 Days a Week" -> listOf(
            WorkoutCardData("Full Body", "Compound full body", "9 Exercises", Routes.WORKOUT_FULL_BODY)
        )
        "4 Days a Week" -> listOf(
            WorkoutCardData("Upper Body", "Chest, Back, Arms", "9 Exercises", Routes.WORKOUT_UPPER_BODY),
            WorkoutCardData("Lower Body", "Legs, Abs, Glutes", "7 Exercises", Routes.WORKOUT_LOWER_BODY)
        )
        "5 Days a Week" -> listOf(
            WorkoutCardData("Upper Body", "Chest, Back, Arms", "9 Exercises", Routes.WORKOUT_UPPER_BODY),
            WorkoutCardData("Lower Body", "Legs, Core", "7 Exercises", Routes.WORKOUT_LOWER_BODY),
            WorkoutCardData("Push Day", "Chest, Shoulder, Triceps", "7 Exercises", Routes.WORKOUT_PUSH_DAY),
            WorkoutCardData("Pull Day", "Back, Biceps", "8 Exercises", Routes.WORKOUT_PULL_DAY),
            WorkoutCardData("Leg Day", "Legs, Abs", "8 Exercises", Routes.WORKOUT_LEG_DAY)
        )
        else -> listOf(
            WorkoutCardData("Push Day", "Chest, Shoulder, Triceps", "7 Exercises", Routes.WORKOUT_PUSH_DAY),
            WorkoutCardData("Pull Day", "Back, Biceps", "8 Exercises", Routes.WORKOUT_PULL_DAY),
            WorkoutCardData("Leg Day", "Legs, Abs", "8 Exercises", Routes.WORKOUT_LEG_DAY)
        )
    }

    // Filters out the workout currently active in "Today's Workout"
    val otherWorkouts = allPossibleWorkouts.filter { it.title != todayWorkout.title }

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
        // Scrollable content area
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
                text = "Workouts",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Today’s Workout",
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryGreen,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            WorkoutDayClickableCard(
                title = todayWorkout.title,
                muscles = todayWorkout.muscleGroups,
                exercises = todayWorkout.exerciseCountText,
                onClick = { todayWorkout.route?.let { onNavigate(it) } }
            )

            if (otherWorkouts.isNotEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Other Workouts",
                    modifier = Modifier.fillMaxWidth(),
                    color = PrimaryGreen,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                otherWorkouts.forEach { workout ->
                    WorkoutDayClickableCard(
                        title = workout.title,
                        muscles = workout.muscles,
                        exercises = workout.exercises,
                        onClick = { onNavigate(workout.route) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Fixed bottom dock
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogWorkoutPillButton(
                onClick = { onNavigate(Routes.LOG_WORKOUT) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                color = Color(0xFF8E9094),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            WorkoutsBottomNavigationBar(onNavigate = onNavigate)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun WorkoutDayClickableCard(
    title: String,
    muscles: String,
    exercises: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(CardBackground)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = muscles,
                color = TextWhite,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = exercises,
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open $title",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun WorkoutsBottomNavigationBar(
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
        WorkoutsBottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = false,
            onClick = { onNavigate(Routes.DASHBOARD) }
        )
        WorkoutsBottomNavItem(
            icon = Icons.Default.FitnessCenter,
            label = "Workouts",
            selected = true,
            onClick = { onNavigate(Routes.WORKOUTS) }
        )
        WorkoutsBottomNavItem(
            icon = Icons.Default.Leaderboard,
            label = "Progress",
            selected = false,
            onClick = { onNavigate(Routes.PROGRESS) }
        )
        WorkoutsBottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = false,
            onClick = { onNavigate(Routes.PROFILE) }
        )
    }
}

@Composable
private fun RowScope.WorkoutsBottomNavItem(
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