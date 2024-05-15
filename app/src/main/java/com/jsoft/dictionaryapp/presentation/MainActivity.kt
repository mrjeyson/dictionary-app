package com.jsoft.dictionaryapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jsoft.dictionaryapp.R
import com.jsoft.dictionaryapp.presentation.component.BarColor
import com.jsoft.dictionaryapp.presentation.component.WordResult
import com.jsoft.dictionaryapp.presentation.event.MainUiEvents
import com.jsoft.dictionaryapp.presentation.state.MainState
import com.jsoft.dictionaryapp.presentation.viewmodel.MainViewModel
import com.jsoft.dictionaryapp.ui.theme.DictionaryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryAppTheme {
                BarColor()

                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainState by mainViewModel.mainState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = mainState.searchWord,
                        onValueChange = {
                            mainViewModel.onEvent(
                                MainUiEvents.OnSearchWordChange(it)
                            )
                        },
                        singleLine = true,
                        trailingIcon = {
                            Icon(imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(R.string.search_a_word),
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        mainViewModel.onEvent(
                                            MainUiEvents.OnSearchClick
                                        )
                                    })
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.search_a_word),
                                fontSize = 15.sp,
                                modifier = Modifier.alpha(0.7f),

                                )
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground, fontSize = 19.5.sp,
                        )
                    )
                }) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding())
                    ) {
                        MainScreen(mainState)
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        mainState: MainState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 30.dp)
            ) {
                mainState.wordItem?.let { wordItem ->
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = wordItem.word,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = wordItem.phonetic,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 110.dp)
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topEnd = 50.dp, topStart = 50.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.7f))
            ) {
                if (mainState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    mainState.wordItem?.let { wordItem ->
                        WordResult(wordItem)
                    }
                }
            }
        }
    }
}


