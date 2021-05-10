package com.antoniosj.composerecipeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.antoniosj.composerecipeapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// obs: a ordem dos modifiers importa O_o

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // interop with jetpack
        ViewTreeLifecycleOwner.set(window.decorView, this)


//        setContent {
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//                    .background(color = Color(0xFFF2F2F2))
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.happy_meal_small),
//                    contentDescription = "happy meal",
//                    modifier = Modifier.height(300.dp),
//                    contentScale= ContentScale.Crop
//                )
//
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Row(modifier = Modifier.fillMaxSize(),
//                        horizontalArrangement = Arrangement.SpaceBetween) {
//                        Text(
//                            text = "Happy Meal",
//                            style = TextStyle(
//                                fontSize = 26.sp
//                            ),
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
//
//                        Text(
//                            text = "$5.99",
//                            style = TextStyle(
//                                fontSize = 17.sp,
//                                color = Color(0xFF85bb65)
//                            )
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.padding(top = 10.dp))
//                    Text(
//                        text = "800 calories",
//                        style = TextStyle(
//                            fontSize = 17.sp
//                        )
//                    )
//                    Spacer(modifier = Modifier.padding(top = 10.dp))
//                    Button(
//                        onClick = { /*TODO*/ },
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    ) {
//                        Text(text = "Order Now")
//                    }
//                }
//            }
//        }
    }
}