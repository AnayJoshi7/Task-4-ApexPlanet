package com.anay.fitnesstracker.data

import kotlin.math.roundToInt

object FitnessCalculator {
    const val CURRENT_YEAR = 2026

    fun generateUsername(name: String, birthYearStr: String, contactNo: String): String {
        val initials = name.trim().take(2).uppercase().padEnd(2, 'X')
        val cleanYear = birthYearStr.filter { it.isDigit() }.takeLast(4).ifEmpty { "2000" }
        val last4Digits = contactNo.filter { it.isDigit() }.takeLast(4).ifEmpty { "0000" }
        return "$initials$cleanYear$last4Digits"
    }

    fun calculateBmi(heightCm: Double, weightKg: Double): Pair<Double, String> {
        if (heightCm <= 0.0) return 0.0 to "Invalid"
        val heightM = heightCm / 100.0
        val bmi = (weightKg / (heightM * heightM) * 10.0).roundToInt() / 10.0
        val category = when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
        return bmi to category
    }

    fun calculateMaintenanceCalories(
        gender: String,
        birthYear: Int,
        heightCm: Double,
        weightKg: Double
    ): Int {
        val age = (CURRENT_YEAR - birthYear).coerceAtLeast(10)
        val bmr = if (gender.trim().equals("Female", ignoreCase = true)) {
            (10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161
        } else {
            (10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5
        }
        return (bmr * 1.4).roundToInt()
    }

    fun calculateTargets(maintenanceCalories: Int, weightKg: Double, goal: String): Pair<Int, Int> {
        return when (goal) {
            "Lose Weight" -> Pair(
                (maintenanceCalories - 500).coerceAtLeast(1200),
                (weightKg * 2.0).roundToInt()
            )
            "Gain Weight" -> Pair(
                maintenanceCalories + 400,
                (weightKg * 1.8).roundToInt()
            )
            else -> Pair(
                maintenanceCalories,
                (weightKg * 1.6).roundToInt()
            )
        }
    }

    fun getSplitForFrequency(frequency: String): String {
        return when (frequency) {
            "3 Days a Week" -> "Full Body"
            "4 Days a Week" -> "Upper Body, Lower Body"
            "5 Days a Week" -> "Upper, Lower + Push Pull Legs"
            "6 Days a Week" -> "Push Pull Legs"
            else -> "Push Pull Legs"
        }
    }
}