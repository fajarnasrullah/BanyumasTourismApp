@file:OptIn(ExperimentalMaterial3Api::class)

package com.jer.banyumastourismapp.presentation.orders

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.common.verySmallIcon
import com.jer.banyumastourismapp.presentation.component.AppBarCustom
import com.jer.banyumastourismapp.presentation.component.BottomBarDetail
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate


@Composable
fun OrdersForm(modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    var qty by rememberSaveable { mutableIntStateOf(0) }
    var price by rememberSaveable { mutableIntStateOf(0) }
    var date by rememberSaveable { mutableStateOf("") }
    var dateRequest by rememberSaveable { mutableStateOf(false) }
    val calendarState = rememberUseCaseState()

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var hp by rememberSaveable { mutableStateOf("") }

    var totalPrice by rememberSaveable { mutableIntStateOf(0) }
    var isFixed by rememberSaveable { mutableStateOf(false) }

    // dummy
    price = 100000
    totalPrice = price * qty
    var promoCode by rememberSaveable { mutableStateOf("") }


    Scaffold (
        topBar = { AppBarCustom(
            navigateBack = { /*TODO*/ },
            backgroundColor = MaterialTheme.colorScheme.onPrimary,
            title = "Complete Your Order",

            modifier = Modifier.fillMaxWidth()
        )
        },
        bottomBar = { BottomBarDetail(
            price = "Rp. ${totalPrice.toString() }",
            textButton = "Pay Now",
            headline = "Total Price",
            onClick = { }
        ) }
    ) { innerPadding ->

        ConstraintLayout (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            ) {

            val content = createRef()

            Column (
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top,)
                        start.linkTo(parent.start,)
                        end.linkTo(parent.end,)
                    }
                    .padding(top = 30.dp, start = 30.dp, end = 30.dp)
                    .verticalScroll(scrollState)
            ) {

                Text(
                    text = "Booking Data:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){

                    Text(
                        text = "Product Order:",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Curug Bayan",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }


                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Price:",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Rp. ${ price.toString() }",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }

                // qty
                OutlinedTextField (
                    value = if (qty == 0) "" else qty.toString(),

                    onValueChange = { if (it.isNotEmpty()) qty = it.toInt() else qty = 0 },
                    label = { Text(text = "Quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )

                //date
                OutlinedTextField (
                    value = date,
                    onValueChange = {date = it},
                    label = { Text(text = "Date") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                dateRequest = true
                                calendarState.show()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendaricon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(verySmallIcon )
                            )
                        }
                    }
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    var selectedDate by rememberSaveable { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }
                    CalendarDialog(
                        state = calendarState,
                        selection = CalendarSelection.Date(selectedDate = selectedDate) { newDate ->
                            selectedDate = newDate
                            date = selectedDate.toString()
                        }
                    )
                }

                Text(
                    text = "Booker Data:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // name
                OutlinedTextField (
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                )

                // email
                OutlinedTextField (
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                // no telp
                OutlinedTextField (
                    value = hp,
                    onValueChange = { hp = it },
                    label = { Text(text = "No Telp") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
                )
                
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Box(modifier = Modifier
                        .weight(2f)
                        .background(Color.Transparent))
                    OutlinedButton(
                        onClick = {
                            qty = 0
                            price = 0
                            date = ""
                            name = ""
                            email = ""
                            hp = ""
                        },
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(25.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                    Button(
                        onClick = {
                            isFixed = true
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(25.dp)
                        )
                    }


                }



                // Booking Data
                Card (
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {

                    Column (
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(15.dp)
                    ) {

                        Text(
                            text = "Booking Data",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        TextSection(title = "Product:", text = "Curug Bayan", isFixed = isFixed)
                        TextSection(title = "Qty:", text = qty.toString(), isFixed = isFixed)
                        TextSection(title = "Price:", text = price.toString(), isFixed = isFixed)
                        TextSection(title = "Date:", text = date, isFixed = isFixed)

                    }

                }

                // Booker Data
                Card (
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

                    Column (
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(15.dp)
                    ) {

                        Text(
                            text = "Booker Data",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        TextSection(title = "Name:", text = name, isFixed = isFixed)
                        TextSection(title = "Email:", text = email, isFixed = isFixed)
                        TextSection(title = "No Telp:", text = hp, isFixed = isFixed)

                    }

                }

                // promo textfield cuy
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(2.dp, Color.Green, RoundedCornerShape(15.dp))
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.onPrimary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = promoCode,
                        onValueChange = { promoCode = it },
                        placeholder = { Text("Enter Your Promo Code", color = MaterialTheme.colorScheme.outline) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    )

                    val context = LocalContext.current
                    Button(
                        onClick = {
                            if (promoCode.lowercase() == "qwerty") {
                                totalPrice = (totalPrice * 50/100).toInt()
                            } else {
                                Toast.makeText(context, "Invalid Promo Code", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 15.dp, bottomEnd = 15.dp),
                        modifier = Modifier

                            .fillMaxHeight()
                    ) {
                        Text("Apply", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))


            }

        }


    }
}




@Composable
fun TextSection(modifier: Modifier = Modifier, title: String, text: String, isFixed: Boolean) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){

        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        if (isFixed) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
        }  else {
            Box(modifier = Modifier.weight(1f))
        }



    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrevOrdersForm() {
    BanyumasTourismAppTheme {
        OrdersForm(

        )
    }

}