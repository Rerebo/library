package com.rerebo.library

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rerebo.library.data.Artic
import com.rerebo.library.utils.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val id = remember { mutableIntStateOf(1) }
            val articData = remember{mutableStateOf(Artic())}
            val context = LocalContext.current

            (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                    Box(
                        Modifier
                            .alpha(0f)
                            .size(190.dp)
                            .offset(190.dp)
                            .clickable {
                                id.intValue=id.intValue+1
                                Modifier.alpha(10f)
                            }
                    )
                    Box(
                        Modifier
                            .alpha(0f)
                            .size(190.dp)
                            .offset((-200).dp)
                            .clickable(
                                onClick = {
                                    if(id.intValue>1) id.intValue=id.intValue-1
                                    Modifier.alpha(10f)
                                }

                            )
                    )
                sendResponse(id.intValue,articData)
                MyUi(articData = articData)
            }
        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    fun sendResponse(idInputed:Int,articData: MutableState<Artic>) {


        GlobalScope.launch(Dispatchers.IO) {


            val response = try {
                RetrofitInstance.api.getDataArr(idInputed)
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "app error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    articData.value = response.body()!!
                }
            }
        }
    }
}

@Composable
fun MyUi(articData: MutableState<Artic>, modifier: Modifier = Modifier) {
        Column(modifier
        .fillMaxSize()
        .padding(8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = articData.value.data[0].title,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        if(!articData.value.data[0].image_id.isNullOrBlank()){
        Image(
            painter = rememberAsyncImagePainter("https://www.artic.edu/iiif/2/${articData.value.data[0].image_id}/full/800,/0/default.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(300.dp)
            )
        }
        else{
            Text(
                text = "There is No Image", fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier =Modifier.fillMaxWidth().height(450.dp)
            )
        }
        if(articData.value.data[0].short_description!=null){
            Text(
                text = "Description:\n${articData.value.data[0].short_description!!}", fontSize = 22.sp,
                textAlign = TextAlign.Left,
                modifier =Modifier.fillMaxWidth().height(450.dp)
            )
        }
    }
        Text(
            text = "Author:\n${articData.value.data[0].artist_title}", fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .offset(0.dp,730.dp)
                .padding(8.dp)
        )
        Text(
            text = "Date of Creation:\n${articData.value.data[0].date_display}", fontSize = 16.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.offset(0.dp,730.dp).padding(8.dp)
        )
}