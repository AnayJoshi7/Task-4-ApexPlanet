package com.anay.fitnesstracker.Screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.anay.fitnesstracker.components.NextPillButton
import com.anay.fitnesstracker.data.viewmodel.FitnessViewModel
import kotlinx.coroutines.launch
import com.anay.fitnesstracker.components.SplashBottomIcons
import com.anay.fitnesstracker.components.SplashTopIcons

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

@Composable
fun OnboardingScreen(
    viewModel: FitnessViewModel,
    onNext: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthYearStr by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var heightStr by remember { mutableStateOf("") }
    var weightStr by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGradient)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding() // Automatically pushes the screen content above the keyboard
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Let’s Get You\nOnboarded!",
                color = PrimaryGreen,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            ScrollAwareOnboardingField(value = name, onValueChange = { name = it }, placeholder = "Name")
            Spacer(modifier = Modifier.height(14.dp))
            ScrollAwareOnboardingField(value = gender, onValueChange = { gender = it }, placeholder = "Gender (Male/Female)")
            Spacer(modifier = Modifier.height(14.dp))
            ScrollAwareOnboardingField(value = birthYearStr, onValueChange = { birthYearStr = it }, placeholder = "Birth Year (e.g. 2004)")
            Spacer(modifier = Modifier.height(14.dp))
            ScrollAwareOnboardingField(value = contact, onValueChange = { contact = it }, placeholder = "Contact No.")
            Spacer(modifier = Modifier.height(14.dp))
            ScrollAwareOnboardingField(value = heightStr, onValueChange = { heightStr = it }, placeholder = "Height", suffix = "CM")
            Spacer(modifier = Modifier.height(14.dp))
            ScrollAwareOnboardingField(value = weightStr, onValueChange = { weightStr = it }, placeholder = "Weight", suffix = "KG")


            Spacer(modifier = Modifier.height(28.dp))
            SplashTopIcons()
            Spacer(modifier = Modifier.height(16.dp))
            SplashBottomIcons()
        }

        NextPillButton(
            onClick = {
                val year = birthYearStr.toIntOrNull()
                val height = heightStr.toDoubleOrNull()
                val weight = weightStr.toDoubleOrNull()

                if (name.isBlank() || year == null || contact.isBlank() || height == null || weight == null) {
                    Toast.makeText(context, "Please enter all valid details", Toast.LENGTH_SHORT).show()
                    return@NextPillButton
                }

                viewModel.updateInitialDetails(
                    name = name,
                    gender = gender.ifBlank { "Male" },
                    dobYear = year,
                    contact = contact,
                    heightCm = height,
                    weightKg = weight
                )
                onNext()
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScrollAwareOnboardingField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    suffix: String? = null
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
            .clip(RoundedCornerShape(27.dp))
            .background(CardBackground)
            .padding(horizontal = 22.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = Color.Gray, fontSize = 16.sp) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
            )

            if (suffix != null) {
                Text(text = suffix, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}