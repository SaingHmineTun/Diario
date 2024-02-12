package it.saimao.diario.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.saimao.diario.R
import it.saimao.diario.data.DiarioEntity
import it.saimao.diario.ui.screens.state.DiarioUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClick: (Int) -> Unit,
    viewModel: DiarioViewModel = viewModel(factory = DiarioViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onClick.invoke(-1)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add New Item")
            }
        }
    ) {
        DiarioList(onClick, uiState, it)
    }
}

@Composable
fun DiarioList(onClick: (Int) -> Unit, list: List<DiarioUiModel>, paddingValues: PaddingValues) {
    LazyColumn(contentPadding = paddingValues, modifier = Modifier.padding(horizontal = 8.dp)) {
        items(list) {
            DiarioItem(onClickCardView = onClick, diarioUiModel = it, Modifier.padding(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarioItem(
    onClickCardView: (Int) -> Unit,
    diarioUiModel: DiarioUiModel,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth(), onClick = {
        Log.d("Kham", "Title: ${diarioUiModel.title}")
        Log.d("Kham", "ID: ${diarioUiModel.id}")
        onClickCardView.invoke(diarioUiModel.id)
    }) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = diarioUiModel.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = diarioUiModel.content,
                minLines = 2,
                maxLines = 2,
                softWrap = true,
                fontWeight = FontWeight.Thin,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Created at:  ${formatDate(diarioUiModel.created)}",
//                style = MaterialTheme.typography.bodySmall,
//                fontWeight = FontWeight.ExtraBold,
//                color = MaterialTheme.colorScheme.primary
//            )
            Text(
                text = "Updated at: ${formatDate(diarioUiModel.updated)}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.error
            )

        }
    }
}

fun formatDate(date: Date): String {
    return SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(date)
}

@Preview
@Composable
fun DiarioItemPreview() {
    DiarioItem(
        onClickCardView = {},
        DiarioUiModel(
            1,
            "Math for Software Developer",
            "The amount",
            Date(),
            Date(),
        )
    )
}