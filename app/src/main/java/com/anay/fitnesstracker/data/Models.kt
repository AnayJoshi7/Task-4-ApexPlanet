package com.anay.fitnesstracker.data

data class MealItem(
    val id: String = "",
    val name: String = "",
    val calories: Int = 0,
    val protein: Int = 0
)

data class UserProfile(
    val username: String = "",
    val name: String = "",
    val gender: String = "Male",
    val birthYear: Int = 2000,
    val contactNo: String = "",
    val heightCm: Double = 175.0,
    val weightKg: Double = 70.0,
    val bmi: Double = 0.0,
    val bmiCategory: String = "Normal",
    val maintenanceCalories: Int = 2000,
    val fitnessGoal: String = "Maintain Weight",
    val workoutFrequency: String = "6 Days a Week",
    val workoutSplit: String = "Push Pull Legs",
    val dailyCalorieGoal: Int = 2000,
    val dailyProteinGoal: Int = 120,
    val dailyStepGoal: Int = 10000,
    val meals: List<MealItem> = emptyList()
)