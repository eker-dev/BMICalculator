package info.eker.bmicalculator.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {
    var weight by mutableStateOf("")
    var height by mutableStateOf("")
    var bmi by mutableStateOf("")
    var status by mutableStateOf("")

    @SuppressLint("DefaultLocale")
    fun calculateBmi() {
        val weight = weight.toDoubleOrNull() ?: 0.0
        val height = height.toDoubleOrNull() ?: 0.0
        val bmiDouble = weight / (height * height)

        status = getStatus(bmiDouble)
        bmi = String.format("%.1f", bmiDouble)
    }

    private fun getStatus(bmi: Double): String {
        return when (bmi) {
            in 0.0..15.9 -> UNDERWEIGHT_SEVERE
            in 16.0..16.9 -> UNDERWEIGHT_MODERATE
            in 17.0..18.4 -> UNDERWEIGHT_MILD
            in 18.5..24.9 -> NORMAL
            in 25.0..29.9 -> OVERWEIGHT
            in 30.0..34.9 -> OBESE_I
            in 35.0..39.9 -> OBESE_II
            else -> OBESE_III
        }
    }

    companion object BMIStatus {
        const val UNDERWEIGHT_SEVERE = "Underweight (Severe thinness)"
        const val UNDERWEIGHT_MODERATE = "Underweight (Moderate thinness)"
        const val UNDERWEIGHT_MILD = "Underweight (Mild thinness)"
        const val NORMAL = "Normal"
        const val OVERWEIGHT = "Overweight (Pre-Obese)"
        const val OBESE_I = "Obese (Class I)"
        const val OBESE_II = "Obese (Class II)"
        const val OBESE_III = "Obese (Class III)"

        val statusMap = mapOf(
            UNDERWEIGHT_SEVERE to "Less than 16.0",
            UNDERWEIGHT_MODERATE to "16.0 - 16.9",
            UNDERWEIGHT_MILD to "17.0 - 18.4",
            NORMAL to "18.5 - 24.9",
            OVERWEIGHT to "25.0 - 29.9",
            OBESE_I to "30.0 - 34.9",
            OBESE_II to "35.0 - 39.9",
            OBESE_III to "40 and above",
        )
    }


}