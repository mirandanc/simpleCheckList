package com.example.todo.showlist.presentation.add_checklist

import com.example.todo.showlist.presentation.add_new_list_screen.AddChecklistViewModel
import com.example.todo.showlist.presentation.util.components.TopBar
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ChecklistItem(var text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChecklistScreen(
    navController: NavController,
    addChecklistViewModel: AddChecklistViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    val checklistItems = remember { mutableStateListOf<ChecklistItem>() }

    LaunchedEffect(Unit) {
        addChecklistViewModel.onSaveSuccess.collect { wasSuccessful ->
            if (wasSuccessful) {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("newListAdded", true)
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Lists",
                colors = androidx . compose . material3 . TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(
                onClick = { checklistItems.add(ChecklistItem("")) },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("Add Item")
            }

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(checklistItems) { item ->
                    OutlinedTextField(
                        value = item.text,
                        onValueChange = { newText ->
                            val index = checklistItems.indexOf(item)
                            if (index != -1) {
                                checklistItems[index] = item.copy(text = newText)
                            }
                        },
                        label = { Text("Checklist Item") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }

            Button(
                onClick = {
                    addChecklistViewModel.saveChecklist(title, checklistItems)
                },
                enabled = title.isNotBlank(),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Save Checklist")
            }
        }
    }
}