package com.roman.pr401

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                Surface(
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Menu()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu() {
    var nota by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf(FloatArray(10)) }
    var mensaje by remember { mutableStateOf("") }
    var posicion by remember { mutableStateOf(0) }
    var borrar by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nota,
            onValueChange = {
                nota = it
            },
            label = { Text("Inserta la nota") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    try {
                        if (nota.isNotEmpty()) {
                            if (posicion < notas.size) {
                                notas[posicion] = nota.toFloat()
                                posicion++
                                mensaje = "Nota $nota añadida correctamente."
                                nota = ""
                            } else {
                                mensaje = "Ya has ingresado todas las notas."
                            }
                        } else {
                            mensaje = "Ingrese una nota válida."
                        }
                    } catch (e: NumberFormatException) {
                        mensaje = "Error: Ingresa un número válido."
                    }
                }
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(mensaje, color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                try {
                    ordenarNotasBurbuja(notas)
                    mensaje = "Notas ordenadas correctamente."
                } catch (e: Exception) {
                    mensaje = "Error al ordenar las notas."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Ordenar Notas")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

                try {
                    val mediaCalculada = media(notas)
                    mensaje = "La media de las notas es: $mediaCalculada"
                } catch (e: Exception) {
                    mensaje = "Error al calcular la media."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Media")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (posicion > 0) {
            Text(
                text = "La nota más alta es: ${notas.last()}"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = borrar,
            onValueChange = {
                borrar = it
            },
            label = { Text("Inserta la nota a borrar") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    try {
                        if (borrar.isNotEmpty()) {
                            borarnota(notas, borrar.toFloat())
                        } else {
                            mensaje = "Ingrese la nota a borrar"
                        }
                    } catch (e: NumberFormatException) {
                        mensaje = "Error: Ingresa un número válido para borrar."
                    }
                }
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        Button(
            onClick = {
                borrararray(notas)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Borar todo")
        }
        Text(notas.joinToString())
    }
}

fun ordenarNotasBurbuja(notas: FloatArray) {
    val tarray = notas.size
    for (i in 0 until tarray - 1) {
        for (j in 0 until tarray - 1 - i) {
            if (notas[j] > notas[j + 1]) {
                // Intercambiar elementos si están en el orden incorrecto
                val temp = notas[j]
                notas[j] = notas[j + 1]
                notas[j + 1] = temp
            }
        }
    }
}

fun media(notas: FloatArray): Float {
    var med = 0.0f
    val siz = notas.size
    var divisor = 0.0f
    for (i in 0 until siz) {
        if (notas[i] != 0.0f) {
            med += notas[i]
            divisor++
        }
    }
    return med / divisor
}

fun borarnota(notas: FloatArray, nota: Float) {
    val siz = notas.size
    for (i in 0 until siz) {
        if (notas[i] == nota) {
            notas[i] = 0.0f
        }
    }
}

fun borrararray(notas: FloatArray) {
    for (i in notas.indices) {
        notas[i] = 0.0f
    }
}

