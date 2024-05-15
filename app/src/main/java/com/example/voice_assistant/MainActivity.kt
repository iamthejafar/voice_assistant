package com.example.voice_assistant

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.voice_assistant.ui.theme.Voice_assistantTheme

class MainActivity : ComponentActivity() {


    private lateinit var startForResult : ActivityResultLauncher<Intent>
    private var speakerText by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK && it.data != null){

                val resultData = it.data
                val resultArray = resultData?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)

                speakerText = resultArray?.get(0).toString()
            }
        }

        setContent {
            Voice_assistantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    SpeechToText()
                }
            }
        }
    }

    @Composable
    fun SpeechToText() {




        val context = androidx.compose.ui.platform.LocalContext.current

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(onClick = {

                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.getDefault())
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now")
                    context.startActivity(intent)

                    startForResult.launch(intent)
                }) {
                    Icon(Icons.Rounded.Mic, contentDescription =null )
                }

                androidx.compose.material3.Text(text = speakerText)

            }
        }

    }
}

