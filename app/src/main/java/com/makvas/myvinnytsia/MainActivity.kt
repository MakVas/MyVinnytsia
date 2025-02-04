package com.makvas.myvinnytsia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.makvas.myvinnytsia.ui.MyVinnytsia
import com.makvas.myvinnytsia.ui.theme.MyVinnytsiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyVinnytsiaTheme {
                MyVinnytsia()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyVinnytsiaPreview() {
    MyVinnytsiaTheme {
        MyVinnytsia()
    }
}