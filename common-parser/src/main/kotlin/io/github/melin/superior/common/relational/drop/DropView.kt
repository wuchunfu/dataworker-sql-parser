package io.github.melin.superior.common.relational.drop

import io.github.melin.superior.common.PrivilegeType
import io.github.melin.superior.common.relational.Statement
import io.github.melin.superior.common.relational.TableId

data class DropView(
    val tableId: TableId,
    var ifExists: Boolean = false
) : Statement() {
    override val privilegeType: PrivilegeType = PrivilegeType.DROP
}