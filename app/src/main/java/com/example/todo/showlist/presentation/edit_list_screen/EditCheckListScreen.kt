package com.example.todo.showlist.presentation.edit_list_screen

import androidx.compose.material.icons.filled.DragIndicator
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

import com.example.todo.showlist.presentation.util.components.TopBar
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditChecklistScreen(
    navController: NavController,
    listId: String?,
    viewModel: EditCheckListViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = listId) {
        if (listId != null) {
            viewModel.loadChecklist(UUID.fromString(listId))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateUp.collect {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("listWasEdited", true)
                navController.popBackStack()
        }
    }

    Scaffold (
        topBar = {
            TopBar(
                title = "Lists",
                colors = androidx . compose . material3 . TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            OutlinedTextField(
                value = viewModel.title.value,
                onValueChange = { viewModel.title.value = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(
                onClick = { viewModel.addNewItem() },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("Add Item")
            }

            LazyColumn(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                itemsIndexed(viewModel.checklistItems, key = { _, item -> item.check.id }) { index, item ->
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DragIndicator,
                                contentDescription = "Drag to reorder",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )

                            OutlinedTextField(
                                value = item.check.description,
                                onValueChange = { newText ->
                                    viewModel.updateItem(index, newDescription = newText)
                                },
                                label = { Text("Checklist Item") },
                                modifier = Modifier.weight(1f)
                            )
                            IconButton (onClick = { viewModel.deleteItem(index)}) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Delete Item"
                                )
                            }
                        }
                    }

            }

            Button(
                onClick = { viewModel.saveChanges() },
                enabled = viewModel.title.value.isNotBlank(),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Save Changes")
            }
        }
    }
}