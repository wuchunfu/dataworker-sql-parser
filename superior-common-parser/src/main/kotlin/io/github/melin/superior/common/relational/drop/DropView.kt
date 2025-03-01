package io.github.melin.superior.common.relational.drop

import io.github.melin.superior.common.PrivilegeType
import io.github.melin.superior.common.SqlType
import io.github.melin.superior.common.StatementType
import io.github.melin.superior.common.relational.TableId
import io.github.melin.superior.common.relational.abs.AbsTableStatement

data class DropView(override val tableId: TableId, var ifExists: Boolean = false) : AbsTableStatement() {
    override val statementType = StatementType.DROP_VIEW
    override val privilegeType = PrivilegeType.DROP
    override val sqlType = SqlType.DDL

    val tableIds: ArrayList<TableId> = arrayListOf()
}
