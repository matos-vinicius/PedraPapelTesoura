package br.edu.ifsp.scl.sc3039056.pedrapapeltesoura.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3039056.pedrapapeltesoura.Jogada
import br.edu.ifsp.scl.sc3039056.pedrapapeltesoura.R

@Composable
fun GameControls(
    modo: Int,
    onModoChange: (Int) -> Unit,
    jogadaSelecionada: Jogada,
    onJogadaChange: (Jogada) -> Unit,
    onJogar: () -> Unit
) {

    val jogadas = Jogada.values().toList()

    Column {

        Text("Modo de jogo:")

        Row(
            modifier = Modifier.selectableGroup()
        ) {
            listOf(2, 3).forEach {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .selectable(
                            selected = modo == it,
                            role = Role.RadioButton,
                            onClick = { onModoChange(it) }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = modo == it, onClick = null)
                    Text("$it Jogadores")
                }
            }
        }

        Text("Escolha sua jogada:")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            jogadas.forEach { jogada ->
                val selected = jogada == jogadaSelecionada

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.selectable(
                        selected = selected,
                        role = Role.RadioButton,
                        onClick = { onJogadaChange(jogada) }
                    )
                ) {
                    Image(
                        painter = painterResource(id = getImagem(jogada)),
                        contentDescription = jogada.name,
                        modifier = Modifier.size(80.dp)
                    )

                    RadioButton(selected = selected, onClick = null)
                }
            }
        }

        Button(onClick = onJogar, modifier = Modifier.fillMaxWidth()) {
            Text("Jogar")
        }
    }
}

fun getImagem(jogada: Jogada): Int {
    return when (jogada) {
        Jogada.PEDRA -> R.drawable.pedra
        Jogada.PAPEL -> R.drawable.papel
        Jogada.TESOURA -> R.drawable.tesoura
    }
}