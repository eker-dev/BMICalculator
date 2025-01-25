package info.eker.bmicalculator.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.eker.bmicalculator.R
import info.eker.bmicalculator.viewmodels.BmiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCalculator(modifier: Modifier = Modifier) {
    val bmiViewModel = BmiViewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "BMI Calculator")
                }
            )
        }, modifier = modifier
    ) {
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
                value = bmiViewModel.weight,
                imeAction = ImeAction.Next,
                modifier = Modifier,
                onValueChange = {
                    bmiViewModel.weight = it
                })
            Spacer(modifier = Modifier.height(10.dp))
            EditNumberField(
                label = "Height (in meter)",
                keyboardType = KeyboardType.Number,
                value = bmiViewModel.height,
                modifier = Modifier,
                onValueChange = {
                    bmiViewModel.height = it
                },
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    bmiViewModel.calculateBmi()
                },
            ) {
                Text(text = "Calculate")
            }
            Spacer(modifier = Modifier.height(20.dp))
            TableContentData(
                modifier = Modifier,
                bmi = bmiViewModel.bmi,
                status = bmiViewModel.status,
            )
        }
    }
}


@Composable
fun EditNumberField(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
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
        for (key in BmiViewModel.statusMap.keys) {
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
                    text = BmiViewModel.statusMap[key]!!,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

