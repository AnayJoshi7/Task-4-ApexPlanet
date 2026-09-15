package com.anay.fitnesstracker.Screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anay.fitnesstracker.data.viewmodel.FitnessViewModel
import kotlinx.coroutines.launch

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
private val TextMuted = Color(0xFF9E9E9E)
private val InputFieldBg = Color(0xFFFFFFFF)

@Composable
fun MealLogScreen(
    viewModel: FitnessViewModel,
    onBack: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()
    val meals = user?.meals ?: emptyList()

    val totalCalories = meals.sumOf { it.calories }
    val totalProtein = meals.sumOf { it.protein }
    val targetCalories = user?.dailyCalorieGoal ?: 2200
    val targetProtein = user?.dailyProteinGoal ?: 120

    var mealName by remember { mutableStateOf("") }
    var caloriesText by remember { mutableStateOf("") }
    var proteinText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGradient)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding() // Ensures bottom inputs aren't obscured
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onBack() }
                .padding(vertical = 12.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryGreen)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Back", color = PrimaryGreen, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Text(
            text = "Track Your Food",
            color = PrimaryGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Current Stats Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(CardBackground)
                .padding(20.dp)
        ) {
            Text("Current Stats", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Calories Consumed", color = TextWhite, fontSize = 15.sp)
            Text("$totalCalories / $targetCalories kcals", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Protein", color = TextWhite, fontSize = 15.sp)
            Text("$totalProtein / $targetProtein grams", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Add Meal", color = PrimaryGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))

        // Add Meal Card with auto-bring-into-view
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(CardBackground)
                .padding(20.dp)
        ) {
            Text("Name of the meal", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            ScrollAwareMealInput(value = mealName, onValueChange = { mealName = it }, placeholder = "e.g. Oats & Eggs")

            Spacer(modifier = Modifier.height(12.dp))

            Text("Calories", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            ScrollAwareMealInput(value = caloriesText, onValueChange = { caloriesText = it }, placeholder = "kcal", widthFraction = 0.5f)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Protein", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    ScrollAwareMealInput(value = proteinText, onValueChange = { proteinText = it }, placeholder = "grams", widthFraction = 0.5f)
                }

                IconButton(
                    onClick = {
                        val cals = caloriesText.toIntOrNull()
                        val prot = proteinText.toIntOrNull()
                        if (mealName.isBlank() || cals == null || prot == null) {
                            Toast.makeText(context, "Enter name, calories & protein numbers", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        viewModel.addMeal(mealName, cals, prot)
                        mealName = ""
                        caloriesText = ""
                        proteinText = ""
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Meal", tint = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Previous Meals", color = PrimaryGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))

        if (meals.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(CardBackground)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No meals logged yet.", color = TextMuted, fontSize = 14.sp)
            }
        } else {
            meals.forEach { meal ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(CardBackground)
                        .padding(16.dp)
                ) {
                    Text(meal.name, color = TextWhite, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Calories - ${meal.calories} Kcals", color = TextWhite, fontSize = 14.sp)
                    Text("Protein - ${meal.protein} grams", color = TextWhite, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScrollAwareMealInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    widthFraction: Float = 1f
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray, fontSize = 14.sp) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = InputFieldBg,
            unfocusedContainerColor = InputFieldBg,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .height(48.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
    )
}