package io.github.melin.superior.common.relational.dml

import io.github.melin.superior.common.PrivilegeType
import io.github.melin.superior.common.SqlType
import io.github.melin.superior.common.relational.Statement
import io.github.melin.superior.common.relational.TableId

data class SingleInsertStmt(
    val mode: InsertMode,
    override val tableId: TableId?,
    val path: String?,
): Statement() {
    override val privilegeType: PrivilegeType = PrivilegeType.WRITE
    override val sqlType: SqlType = SqlType.DML

    constructor(mode: InsertMode, tableId: TableId?): this(mode, tableId, null)

    constructor(mode: InsertMode, path: String?): this(mode, null, path)

    var properties: Map<String, String>? = null
    var tableProvider: String? = null
    var partitionVals: LinkedHashMap<String, String>? = null
    var querySql: String? = null
    var rows: ArrayList<List<String>>? = null

    var inputTables: ArrayList<TableId> = arrayListOf()
    var functionNames: HashSet<String> = hashSetOf()
    var cteTempTables: List<String> = listOf()
}