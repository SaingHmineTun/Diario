package it.saimao.diario.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.saimao.diario.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
) {
    val state = viewModel.uiState
    // Text(text = "id: ${state.id}\ncontent: ${state.content}")
    DetailCompose(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        title = state.diarioUiModel.title,
        content = state.diarioUiModel.content,
        updateTitle = {
            viewModel.updateValue(
                state.diarioUiModel.copy(
                    title = it
                )
            )
        },
        updateContent = {
            viewModel.updateValue(
                state.diarioUiModel.copy(
                    content = it
                )
            )
        },
        updateDiario = {
            viewModel.addOrUpdate()
            navigateBack()
        },
        deleteDiario = {
            viewModel.deleteDiario()
            navigateBack()
        },
        isUpdated = state.isUpdated
    )
}

@Composable
fun DetailCompose(
    title: String,
    content: String,
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit,
    isUpdated: Boolean,
    updateDiario: () -> Unit,
    deleteDiario: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = title, label = {
                Text(text = "Title")
            }, onValueChange = updateTitle, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text(text = "Content")
            },
            value = content,
            onValueChange = updateContent,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            singleLine = false
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    4.dp
                ), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                updateDiario()
            }, modifier = Modifier.weight(0.5F)) {
                Text(
                    text = if (isUpdated) stringResource(id = R.string.update) else stringResource(
                        id = R.string.add
                    )
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                onClick = { deleteDiario() },
                modifier = Modifier.weight(0.5F),
            ) {
                Text(text = "Delete")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailComposePreview() {

    DetailCompose(title = "Love Kham",
        content = "I love you, Nang Kham Hom",
        updateTitle = {},
        updateContent = {},
        isUpdated = false,
        deleteDiario = {},
        updateDiario = {})

}