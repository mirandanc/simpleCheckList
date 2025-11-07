package com.example.todo.showlist.domain.use_case

import javax.inject.Inject

data class CheckListUseCases @Inject constructor(
    val getCheckLists: GetCheckListsUseCase,
    val deleteCheckList: DeleteCheckListUseCase,
    val updateCheckState: UpdateCheckStateUseCase
){
}