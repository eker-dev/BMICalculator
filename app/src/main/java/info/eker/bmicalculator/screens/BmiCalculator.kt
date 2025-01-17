package info.eker.bmicalculator.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.eker.bmicalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCalculator(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "BMI Calculator")
                }
            )
        }, modifier = modifier
    ) {
        val weight = remember { mutableStateOf("") }
        val height = remember { mutableStateOf("") }
        val bmi = remember { mutableStateOf("") }
        val status = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi_logo),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            EditNumberField(
                label = "Weight (in kg)",
                keyboardType = KeyboardType.Number,
                value = weight.value,
                imeAction = ImeAction.Next,
                modifier = Modifier,
                onValueChangue = {
                    weight.value = it
                })
            Spacer(modifier = Modifier.height(10.dp))
            EditNumberField(
                label = "Height (in meter)",
                keyboardType = KeyboardType.Number,
                value = height.value,
                modifier = Modifier,
                onValueChangue = {
                    height.value = it
                },
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    bmi.value = BmiCalculate(
                        weight = weight.value.toDoubleOrNull() ?: 0.0,
                        height = height.value.toDoubleOrNull() ?: 0.0
                    )
                    status.value = getStatus(bmi.value.toDoubleOrNull() ?: 0.0)

                },
            ) {
                Text(text = "Calculate")
            }
            Spacer(modifier = Modifier.height(20.dp))
            TableContentData(modifier = Modifier, bmi = bmi.value, status = status.value)
        }
    }
}

@SuppressLint("DefaultLocale")
fun BmiCalculate(weight: Double, height: Double): String {
    val bmi = weight / (height * height)
    return String.format("%.1f", bmi)
}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onValueChangue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChangue,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
}

@Composable
fun TableContentData(
    bmi: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "BMI value: $bmi")
        Spacer(Modifier.height(20.dp))
        for (key in statusMap.keys) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
                    .background(
                        if (status == key) Color.LightGray else Color.Transparent
                    )
            ) {
                Text(text = key, fontSize = 12.sp, modifier = Modifier.weight(2f))
                Text(
                    text = statusMap[key]!!,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

fun getStatus(bmi: Double): String {
    return when (bmi) {
        in 0.0..15.9 -> BMIStatus.UNDERWEIGHT_SEVERE
        in 16.0..16.9 -> BMIStatus.UNDERWEIGHT_MODERATE
        in 17.0..18.4 -> BMIStatus.UNDERWEIGHT_MILD
        in 18.5..24.9 -> BMIStatus.NORMAL
        in 25.0..29.9 -> BMIStatus.OVERWEIGHT
        in 30.0..34.9 -> BMIStatus.OBESE_I
        in 35.0..39.9 -> BMIStatus.OBESE_II
        else -> BMIStatus.OBESE_III
    }
}

object BMIStatus {
    const val UNDERWEIGHT_SEVERE = "Underweight (Severe thinness)"
    const val UNDERWEIGHT_MODERATE = "Underweight (Moderate thinness)"
    const val UNDERWEIGHT_MILD = "Underweight (Mild thinness)"
    const val NORMAL = "Normal"
    const val OVERWEIGHT = "Overweight (Pre-Obese)"
    const val OBESE_I = "Obese (Class I)"
    const val OBESE_II = "Obese (Class II)"
    const val OBESE_III = "Obese (Class III)"

}

val statusMap = mapOf(
    BMIStatus.UNDERWEIGHT_SEVERE to "Less than 16.0",
    BMIStatus.UNDERWEIGHT_MODERATE to "16.0 - 16.9",
    BMIStatus.UNDERWEIGHT_MILD to "17.0 - 18.4",
    BMIStatus.NORMAL to "18.5 - 24.9",
    BMIStatus.OVERWEIGHT to "25.0 - 29.9",
    BMIStatus.OBESE_I to "30.0 - 34.9",
    BMIStatus.OBESE_II to "35.0 - 39.9",
    BMIStatus.OBESE_III to "40 and above",
)
