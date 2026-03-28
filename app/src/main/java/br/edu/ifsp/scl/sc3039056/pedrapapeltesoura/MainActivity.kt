package br.edu.ifsp.scl.sc3039056.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3039056.pedrapapeltesoura.ui.composables.GameControls
import br.edu.ifsp.scl.sc3039056.pedrapapeltesoura.ui.theme.PedraPapelTesouraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PedraPapelTesouraTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PedraPapelTesoura()
                }
            }
        }
    }
}

enum class Jogada {
    PEDRA, PAPEL, TESOURA
}

@Composable
fun PedraPapelTesoura() {

    var modo by rememberSaveable { mutableStateOf(2) }
    var jogadaUsuario by rememberSaveable { mutableStateOf(Jogada.PEDRA) }

    var bot1 by remember { mutableStateOf(Jogada.PEDRA) }
    var bot2 by remember { mutableStateOf(Jogada.PEDRA) }

    var resultado by rememberSaveable { mutableStateOf("Clique em Jogar") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        GameControls(
            modo = modo,
            onModoChange = { modo = it },
            jogadaSelecionada = jogadaUsuario,
            onJogadaChange = { jogadaUsuario = it },
            onJogar = {
                bot1 = Jogada.values().random()
                bot2 = Jogada.values().random()

                resultado = if (modo == 2) {
                    resultado2(jogadaUsuario, bot1)
                } else {
                    resultado3(jogadaUsuario, bot1, bot2)
                }
            }
        )

        Text("Bot 1: $bot1")

        if (modo == 3) {
            Text("Bot 2: $bot2")
        }

        Text("Resultado: $resultado")

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            resultado = "Clique em Jogar"
        }) {
            Text("Reset")
        }
    }
}

fun resultado2(user: Jogada, bot: Jogada): String {
    return if (user == bot) {
        "Empate"
    } else if (
        (user == Jogada.PEDRA && bot == Jogada.TESOURA) ||
        (user == Jogada.TESOURA && bot == Jogada.PAPEL) ||
        (user == Jogada.PAPEL && bot == Jogada.PEDRA)
    ) {
        "Vitória"
    } else {
        "Derrota"
    }
}

fun resultado3(user: Jogada, bot1: Jogada, bot2: Jogada): String {
    val jogadas = listOf(user, bot1, bot2).toSet()

    if (jogadas.size == 1) return "Empate"
    if (jogadas.size == 3) return "Empate"

    return when {
        jogadas.containsAll(listOf(Jogada.PEDRA, Jogada.TESOURA)) ->
            if (user == Jogada.PEDRA) "Vitória" else "Derrota"

        jogadas.containsAll(listOf(Jogada.TESOURA, Jogada.PAPEL)) ->
            if (user == Jogada.TESOURA) "Vitória" else "Derrota"

        jogadas.containsAll(listOf(Jogada.PAPEL, Jogada.PEDRA)) ->
            if (user == Jogada.PAPEL) "Vitória" else "Derrota"

        else -> "Empate"
    }
}

@Preview(showBackground = true)
@Composable
fun PedraPapelTesouraPreview() {
    PedraPapelTesouraTheme {
        PedraPapelTesoura()
    }
}