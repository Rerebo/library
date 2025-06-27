package com.rerebo.library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rerebo.library.ui.theme.LibraryTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleScreen()
        }
    }
}

@Composable
fun SampleScreen() {

    // save initial state
    val counter = remember { mutableIntStateOf(0) }
    var number by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = counter.intValue.toString(), fontSize = 50.sp)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = number,
            onValueChange = {newText ->
                number = newText
            }
            )


        CreateButton(text = "Add ${number.text}") {
            if (isNumeric(number.text) and number.text.isNotBlank())
            counter.intValue += number.text.toInt()
            else{
                counter.intValue += 0
            }
        }
    }
}
@Composable
fun CreateButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Green)
    ) {
        Text(text = text, color = Color.White)
    }
}
fun isNumeric(toCheck: String): Boolean {
    var ans = false
    if(toCheck[0]=='-'){
        for(i in toCheck.length-1 downTo 1){
            if(toCheck[i].isDigit()){
                ans = true
            }
            else{
                ans = false
                break
            }
        }
    }
    else{ans = toCheck.all { char -> char.isDigit() }}
    return  ans
}