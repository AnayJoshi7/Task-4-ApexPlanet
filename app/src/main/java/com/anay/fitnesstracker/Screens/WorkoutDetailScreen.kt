package com.anay.fitnesstracker.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anay.fitnesstracker.R
import com.anay.fitnesstracker.components.LogWorkoutPillButton

private val BgGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF1B1C1E),
        Color(0xFF131416),
        Color(0xFF2C2E31),
        Color(0xFF111214)
    )
)
private val CardBackground = Color(0xFF070708)
private val PrimaryGreen = Color(0xFF27D07F)
private val TextWhite = Color(0xFFFFFFFF)

data class MuscleGroupExercise(
    val groupName: String,
    val exercises: List<String>
)

@Composable
fun WorkoutDetailScreen(
    screenTitle: String,
    muscleSections: List<MuscleGroupExercise>,
    guidelineText: String = "Aim for 8-12 reps and 3 sets with moderate weight for best results and progressive overload.",
    onBack: () -> Unit,
    onNavigateLogWorkout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGradient)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBack() }
                    .padding(vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Back", color = PrimaryGreen, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = screenTitle, color = PrimaryGreen, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                muscleSections.forEach { section ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .background(CardBackground)
                            .padding(20.dp)
                    ) {
                        Text(text = section.groupName, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Exercises", color = TextWhite, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        section.exercises.forEachIndexed { index, exercise ->
                            Text(
                                text = "${index + 1}. $exercise",
                                color = TextWhite,
                                fontSize = 14.sp,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(CardBackground)
                        .padding(16.dp)
                ) {
                    Text(
                        text = guidelineText,
                        color = TextWhite,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Fixed Log Workout Pill
        LogWorkoutPillButton(
            onClick = onNavigateLogWorkout,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun PushDayScreen(onBack: () -> Unit, onNavigateLogWorkout: () -> Unit) {
    WorkoutDetailScreen(
        screenTitle = "Push Day",
        muscleSections = listOf(
            MuscleGroupExercise("Chest", listOf("Incline barbell/dumbbell/machine press", "Flat barbell/dumbbell press", "Chest fly", "Push Ups")),
            MuscleGroupExercise("Shoulder", listOf("Dumbbell/barbell/machine overhead press", "Dumbbell lateral raises")),
            MuscleGroupExercise("Triceps", listOf("Rope/Straight bar push down", "Overhead triceps extension"))
        ),
        onBack = onBack,
        onNavigateLogWorkout = onNavigateLogWorkout
    )
}

@Composable
fun PullDayScreen(onBack: () -> Unit, onNavigateLogWorkout: () -> Unit) {
    WorkoutDetailScreen(
        screenTitle = "Pull Day",
        muscleSections = listOf(
            MuscleGroupExercise("Back", listOf("Lat Pulldown", "Close grip rowing", "Wide grip rowing", "Hyper extensions")),
            MuscleGroupExercise("Biceps", listOf("Standing dumbbell/barbell curls", "Preacher curls", "Hammer curls")),
            MuscleGroupExercise("Forearms", listOf("Reverse dumbbell/barbell curls", "Wrist curls"))
        ),
        onBack = onBack,
        onNavigateLogWorkout = onNavigateLogWorkout
    )
}

@Composable
fun LegDayScreen(onBack: () -> Unit, onNavigateLogWorkout: () -> Unit) {
    WorkoutDetailScreen(
        screenTitle = "Leg Day",
        muscleSections = listOf(
            MuscleGroupExercise("Legs", listOf("Barbell squats", "Leg curls", "Leg extensions", "Calf raises", "Hip thrusts")),
            MuscleGroupExercise("Abs", listOf("Half crunches", "Full crunches", "Leg raises"))
        ),
        onBack = onBack,
        onNavigateLogWorkout = onNavigateLogWorkout
    )
}

@Composable
fun UpperBodyScreen(onBack: () -> Unit, onNavigateLogWorkout: () -> Unit) {
    WorkoutDetailScreen(
        screenTitle = "Upper Body",
        muscleSections = listOf(
            MuscleGroupExercise("Exercises", listOf(
                "Lat pull down", "Close grip rowing", "Incline chest press", "Chest fly",
                "Dumbbell lateral raises", "Arnold press", "Incline dumbbell curl", "Triceps dips",
                "Reverse dumbbell/barbell curls"
            ))
        ),
        guidelineText = "Aim for 8-12 reps and 2-3 sets with moderate weight for best results and progressive overload. Avoid junk volume.",
        onBack = onBack,
        onNavigateLogWorkout = onNavigateLogWorkout
    )
}

@Composable
fun LowerBodyScreen(onBack: () -> Unit, onNavigateLogWorkout: () -> Unit) {
    WorkoutDetailScreen(
        screenTitle = "Lower Body",
        muscleSections = listOf(
            MuscleGroupExercise("Exercises", listOf(
                "Squats", "Leg press", "Dead lift", "Calf raises", "Hip abduction", "Leg raises", "Walking lunges"
            ))
        ),
        guidelineText = "Aim for 8-12 reps and 3 sets with moderate weight for best results and progressive overload. Avoid junk volume.",
        onBack = onBack,
        onNavigateLogWorkout = onNavigateLogWorkout
    )
}

@Composable
fun FullBodyScreen(onBack: () -> Unit, onNavigateLogWorkout: () -> Unit) {
    WorkoutDetailScreen(
        screenTitle = "Full Body",
        muscleSections = listOf(
            MuscleGroupExercise("Exercises", listOf(
                "Lat pull down", "Incline chest press", "T-bar rows", "Dumbbell lateral raises",
                "Leg press", "Leg curls", "Incline dumbbell curl", "Rope push down", "Leg raises"
            ))
        ),
        guidelineText = "Aim for 8-12 reps and 2-3 sets with moderate weight for best results and progressive overload. Avoid junk volume.",
        onBack = onBack,
        onNavigateLogWorkout = onNavigateLogWorkout
    )
}