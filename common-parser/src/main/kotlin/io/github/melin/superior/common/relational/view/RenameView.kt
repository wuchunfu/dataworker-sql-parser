package io.github.melin.superior.common.relational.view

import io.github.melin.superior.common.Statement
import io.github.melin.superior.common.relational.TableId

data class RenameView(
    val tableId: TableId,
    val newName: String
) : Statement()