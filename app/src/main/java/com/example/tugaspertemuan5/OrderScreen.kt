package com.example.tugaspertemuan5

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun OrderScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = colorResource(R.color.screen_background)
        ) { innerPadding ->
            Image(
                painter = painterResource(R.drawable.snoopy),
                contentDescription = stringResource(R.string.banner_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.banner_height))
            )
        }
        OrderForm(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun OrderForm(modifier: Modifier = Modifier) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var orderDate by rememberSaveable { mutableStateOf("") }
    var foodType by rememberSaveable { mutableStateOf("") }
    var payment by rememberSaveable { mutableStateOf("") }

    val paymentOptions = stringArrayResource(R.array.payment_options)
    val isComplete = firstName.isNotBlank() && lastName.isNotBlank() &&
            email.isNotBlank() && orderDate.isNotBlank() &&
            foodType.isNotBlank() && payment.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.form_corner),
                    topEnd = dimensionResource(R.dimen.form_corner)
                )
            )
            .background(colorResource(R.color.form_background))
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.form_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.section_spacing))
    ) {
        Text(
            text = stringResource(R.string.form_title),
            fontSize = dimensionResource(R.dimen.title_size).value.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary_blue)
        )
        Text(
            text = stringResource(R.string.form_subtitle),
            fontSize = dimensionResource(R.dimen.subtitle_size).value.sp,
            color = colorResource(R.color.text_hint)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.field_spacing))) {
            FormField(R.string.first_name, firstName, { firstName = it }, Modifier.weight(1f))
            FormField(R.string.last_name, lastName, { lastName = it }, Modifier.weight(1f))
        }

        FormField(
            label = R.string.email,
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Email
        )

        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.field_spacing))) {
            DateField(R.string.order_date, orderDate, { orderDate = it }, Modifier.weight(1f))
            DropdownField(
                label = R.string.food_type,
                value = foodType,
                options = stringArrayResource(R.array.food_types).toList(),
                onSelected = { foodType = it },
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = stringResource(R.string.payment_title),
            fontSize = dimensionResource(R.dimen.label_size).value.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text_dark),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            paymentOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { payment = option }
                ) {
                    RadioButton(
                        selected = payment == option,
                        onClick = { payment = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.primary_blue),
                            unselectedColor = colorResource(R.color.field_border)
                        )
                    )
                    Text(
                        text = option,
                        fontSize = dimensionResource(R.dimen.option_size).value.sp,
                        color = colorResource(R.color.text_dark)
                    )
                }
            }
        }

        Button(
            onClick = { },
            enabled = isComplete,
            shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary_blue),
                contentColor = colorResource(R.color.button_text),
                disabledContainerColor = colorResource(R.color.button_disabled),
                disabledContentColor = colorResource(R.color.button_disabled_text)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_height))
        ) {
            Text(
                text = stringResource(R.string.proceed),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = colorResource(R.color.primary_blue),
    unfocusedBorderColor = colorResource(R.color.field_border),
    focusedLabelColor = colorResource(R.color.primary_blue),
    unfocusedLabelColor = colorResource(R.color.text_hint),
    focusedTextColor = colorResource(R.color.text_dark),
    unfocusedTextColor = colorResource(R.color.text_dark),
    cursorColor = colorResource(R.color.primary_blue)
)
