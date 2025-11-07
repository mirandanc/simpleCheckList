package com.example.todo.showlist.presentation.lists_screen

import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import com.example.todo.showlist.presentation.lists_screen.components.ListCard
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import com.example.todo.showlist.presentation.util.components.LoadingDialog
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import com.example.todo.showlist.presentation.util.components.TopBar
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.todo.showlist.domain.model.CheckList
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.showlist.domain.model.Check
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material.icons.Icons
import androidx.navigation.NavBackStackEntry
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.UUID

import androidx.compose.runtime.setValue
import com.example.todo.showlist.presentation.util.components.ConfirmationDialog

@Composable
internal fun CheckListScreen(
    viewModel: CheckListViewModel = hiltViewModel(),
    onAddCheckList: () -> Unit,
    onEditCheckList: (String) -> Unit,
    navBackStackEntry: NavBackStackEntry
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyStaggeredGridState()

    LaunchedEffect(navBackStackEntry.savedStateHandle) {
        if (navBackStackEntry.savedStateHandle.get<Boolean>("listWasEdited") == true) {
            viewModel.loadChecklists()
            navBackStackEntry.savedStateHandle.remove<Boolean>("listWasEdited")
        }

//        SCROLLS TO THE BOTTOM
//        if (navBackStackEntry.savedStateHandle.get<Boolean>("newListAdded") == true) {
//            gridState.animateScrollToItem(state.checkLists.lastIndex)
//            navBackStackEntry.savedStateHandle.remove<Boolean>("newListAdded")
//        }
    }

    CheckListContent(
        state = state,
        onCheckChanged = viewModel::onCheckChanged,
        onAddCheckList = onAddCheckList,
        onDeleteList = viewModel::deleteList,
        onEditList = onEditCheckList,
        gridState = gridState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListContent(
    state: CheckListViewState,
    onCheckChanged: (CheckList, Check) -> Unit,
    onAddCheckList: () -> Unit,
    onDeleteList: (UUID) -> Unit,
    onEditList: (String) -> Unit,
    gridState: LazyStaggeredGridState
) {

    var showDialog by remember { mutableStateOf(false) }
    var listToDelete by remember { mutableStateOf<CheckList?>(null) }

    if (showDialog && listToDelete != null) {
        ConfirmationDialog(
            dialogTitle = "Delete List",
            dialogText = "Tem certeza que deseja excluir a lista ${listToDelete!!.title}",
            onConfirmation = {
                onDeleteList(listToDelete!!.id)
                showDialog = false
                listToDelete = null
            },
            onDismissRequest = {
                showDialog = false
                listToDelete = null
            }
        )
    }

    LoadingDialog(isLoading = state.isLoading)
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = "Lists",
                colors = androidx . compose . material3 . TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCheckList) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Checklist")
            }
        }
    ){ paddingValues ->
        LazyVerticalStaggeredGrid(
            state = gridState,
            contentPadding = paddingValues,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            columns = StaggeredGridCells.Fixed(1),
            verticalItemSpacing = 10.dp
        ) {
            items(state.checkLists){ checkList ->
                ListCard(
                    checkList = checkList,
                    onCheckChanged = { cl, c -> onCheckChanged(cl, c) },
                    onDelete = {
                        listToDelete = checkList
                        showDialog = true
                    },
                    onEdit = {
                        onEditList(checkList.id.toString())
                    }
                )
            }
        }
    }
}