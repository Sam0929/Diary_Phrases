package com.example.anymes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anymes.model.Anime_Phrases
import com.example.anymes.ui.theme.AnymesTheme
import com.example.anymes.ui.theme.Blue_Light
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainActivity : ComponentActivity() { //Projeto é inicializado aqui, dentro de main activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnymesTheme {
                App_Root()
            }
        }
    }
}

fun loadPhrases(context: Context): List<Anime_Phrases> {
    val arrayDeStrings = context.resources.getStringArray(R.array.frases_animes_array)

    return arrayDeStrings.map { item ->
        val partes = item.split("|", limit = 3).map { it.trim() }

        Anime_Phrases(
            anime = partes.getOrNull(0) ?: "Desconhecido",
            personagem = partes.getOrNull(1) ?: "Desconhecido",
            texto = partes.getOrNull(2) ?: item
        )
    }
}
@Composable
fun App_Root() {
    // [START android_compose_layout_material_modal_drawer]
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val activity = LocalActivity.current as Activity

    ModalNavigationDrawer(
        scrimColor = Blue_Light.copy(alpha = 0.3f),
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet(drawerContainerColor = Color.Black) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo do Aplicativo",
                            alignment = AbsoluteAlignment.CenterRight,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Column {
                            Text(
                                "Anymes",
                                modifier = Modifier.padding(16.dp),
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                            )
                        }
                    }
                    HorizontalDivider()

                    Text(
                        text = "Abra os olhos e veja o futuro",
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                    )
                    NavigationDrawerItem(
                        label = { Text("Funcionalidade 1") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Funcionalidade 2") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    NavigationDrawerItem(
                        label = { Text("Configurações") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Text("20") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Sair") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.ExitToApp,
                                contentDescription = null
                            )
                        },
                        onClick = { activity.finish() },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                IconButton(
                    modifier = Modifier.padding(vertical = 30.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(29.dp),
                        painter = painterResource(R.drawable.baseline_dehaze_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

        ) { innerPadding ->
            MainScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun App_Root_Preview() {
    AnymesTheme {
        App_Root()
    }
}


@Composable  //Funções de layout
fun MainScreen(modifier: Modifier = Modifier) {
    var actualPhrase by remember { mutableStateOf(
        Anime_Phrases(
            anime = "Meu anime",
            personagem = "Mestre",
            texto = "Tome seu conhecimento diários através do Po-Po-Po-Poder!!"

        )
    )
    }
    val context = LocalContext.current
    val phrasesArray = loadPhrases(context)
//Primeiro passo, dizer qual o espaço/container que será exibido o conteúdo
    Surface(modifier = Modifier.fillMaxSize()){ //Superficie com tamanho máximo possível do dispositivo
        Image(
            painter = painterResource(R.drawable.app_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            //Modificando a posição da coluna, eixo x e y
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(16.dp)
            ){
                Text( // Modifier é um modificar que mudará caractéristicas de um determinado componente, caracteristicas universais
                    text = "\"" + actualPhrase.texto + "\"",
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier

                        .padding(all = 10.dp) //unidade de densidade de pontos/pixels

                ) // personagem
                if(actualPhrase.anime.isNotEmpty()){
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "- " + actualPhrase.personagem,
                        color = Color.Yellow,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,


                        )
                    Text(  // anime
                        text = actualPhrase.anime,
                        color = Color.LightGray,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                }
            }

            Button(
                onClick = {
                    val indice = Random.nextInt(phrasesArray.size)
                    actualPhrase = phrasesArray[indice]
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Obtenha o PoDeR ILIMITADO!!",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,

                    )
            }

        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    AnymesTheme {
//        MainScreen()
//    }
//}

@Composable
fun DrawerDemo() {
    // [START android_compose_layout_material_modal_drawer]
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                // ...other drawer items
            }
        }
    ) {
        //ScreenContent
    }

}
//@Preview(name = "DrawerDemo")
//@Composable
//fun PreviewDrawer(){
//    AnymesTheme {
//        DrawerDemo()
//    }
//}



