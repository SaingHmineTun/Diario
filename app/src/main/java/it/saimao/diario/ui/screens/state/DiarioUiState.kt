package it.saimao.diario.ui.screens.state

import it.saimao.diario.data.DiarioEntity
import java.util.Date

data class DiarioUiModel(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val created: Date = Date(),
    val updated: Date = Date()
)

fun DiarioEntity.toUiModel(): DiarioUiModel {
    return DiarioUiModel(
        id = this.id,
        title = title,
        content = content,
        created = created,
        updated = updated
    )
}

fun DiarioUiModel.toEntity(): DiarioEntity {
    return DiarioEntity(
        id = this.id,
        title = title,
        content = content,
        created = created,
        updated = updated
    )
}