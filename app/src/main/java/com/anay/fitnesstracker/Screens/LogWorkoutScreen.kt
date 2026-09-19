package com.anay.fitnesstracker.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anay.fitnesstracker.R
import com.anay.fitnesstracker.data.repository.ExerciseRepository
import com.anay.fitnesstracker.data.WorkoutSet
import com.anay.fitnesstracker.data.viewmodel.FitnessViewModel

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogWorkoutScreen(
    viewModel: FitnessViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val user by viewModel.currentUser.collectAsState()
    val loggedList = user?.loggedWorkouts ?: emptyList()

    var selectedExercise by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    // Dynamic sets state
    var currentSets by remember {
        mutableStateOf(
            listOf(
                WorkoutSet(setNumber = 1, weightKg = "", reps = ""),
                WorkoutSet(setNumber = 2, weightKg = "", reps = "")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGradient)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Back Header
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Log Your Workout",
            color = PrimaryGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Add Exercise", color = PrimaryGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        // Main Exercise Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(CardBackground)
                .padding(20.dp)
        ) {
            Text(text = "Name of the exercise", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown selection container
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = it }
            ) {
                TextField(
                    value = selectedExercise,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select exercise", color = Color.Gray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier.background(Color(0xFF1E1E20))
                ) {
                    ExerciseRepository.allExercises.forEach { exercise ->
                        DropdownMenuItem(
                            text = { Text(exercise, color = Color.White) },
                            onClick = {
                                selectedExercise = exercise
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Render all sets
            currentSets.forEachIndexed { index, setItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Set Number Pill
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Set", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${setItem.setNumber}", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Weight Input
                    Column(modifier = Modifier.weight(1.3f)) {
                        Text("Weight", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextField(
                                    value = setItem.weightKg,
                                    onValueChange = { newVal ->
                                        currentSets = currentSets.toMutableList().also {
                                            it[index] = it[index].copy(weightKg = newVal)
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                Text("kg", color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Reps Input
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Reps", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextField(
                            value = setItem.reps,
                            onValueChange = { newVal ->
                                currentSets = currentSets.toMutableList().also {
                                    it[index] = it[index].copy(reps = newVal)
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(14.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Row: Add Set & Submit Checkmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PrimaryGreen)
                        .clickable {
                            currentSets = currentSets + WorkoutSet(
                                setNumber = currentSets.size + 1,
                                weightKg = "",
                                reps = ""
                            )
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add Set", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.Add, contentDescription = "Add Set", tint = Color.Black, modifier = Modifier.size(16.dp))
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen)
                        .clickable {
                            if (selectedExercise.isBlank()) {
                                Toast.makeText(context, "Select an exercise first", Toast.LENGTH_SHORT).show()
                                return@clickable
                            }
                            val validSets = currentSets.filter { it.weightKg.isNotBlank() && it.reps.isNotBlank() }
                            if (validSets.isEmpty()) {
                                Toast.makeText(context, "Please fill in weight and reps for at least one set", Toast.LENGTH_SHORT).show()
                                return@clickable
                            }

                            viewModel.logExercise(selectedExercise, validSets)
                            selectedExercise = ""
                            currentSets = listOf(
                                WorkoutSet(1, "", ""),
                                WorkoutSet(2, "", "")
                            )
                            Toast.makeText(context, "Workout logged successfully!", Toast.LENGTH_SHORT).show()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Submit", tint = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Other Exercises Section
        Text(text = "Other Exercises", color = PrimaryGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        if (loggedList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(CardBackground)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No logged exercises yet.", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            loggedList.forEach { exercise ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .background(CardBackground)
                        .padding(18.dp)
                ) {
                    Text(text = exercise.exerciseName, color = TextWhite, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    exercise.sets.forEach { setItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Set : ${setItem.setNumber}", color = TextWhite, fontSize = 14.sp)
                            Text("Weight : ${setItem.weightKg} kg", color = TextWhite, fontSize = 14.sp)
                            Text("Reps : ${setItem.reps}", color = TextWhite, fontSize = 14.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}