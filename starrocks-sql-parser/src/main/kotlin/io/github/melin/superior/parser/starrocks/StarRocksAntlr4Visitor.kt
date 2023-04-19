package io.github.melin.superior.parser.starrocks

import com.github.melin.superior.sql.parser.util.StringUtil
import io.github.melin.superior.common.*
import io.github.melin.superior.common.relational.AlterTable
import io.github.melin.superior.common.relational.StatementData
import io.github.melin.superior.common.relational.TableId
import io.github.melin.superior.common.relational.table.ColumnRel
import io.github.melin.superior.common.relational.create.CreateTable
import io.github.melin.superior.parser.starrocks.antlr4.StarRocksParserBaseVisitor
import io.github.melin.superior.parser.starrocks.antlr4.StarRocksParserParser
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode

/**
 * Created by libinsong on 2020/6/30 9:59 上午
 */
class StarRocksAntlr4Visitor: StarRocksParserBaseVisitor<StatementData>() {

    private var currentOptType: StatementType = StatementType.UNKOWN

    override fun visit(tree: ParseTree): StatementData {
        return super.visit(tree)
    }

    override fun shouldVisitNextChild(node: RuleNode, currentResult: StatementData?): Boolean {
        return if (currentResult == null) true else false
    }

    override fun visitCreateTableStatement(ctx: StarRocksParserParser.CreateTableStatementContext): StatementData {
        val tableId = parseTableName(ctx.qualifiedName())
        val comment = if (ctx.comment() != null) StringUtil.cleanQuote(ctx.comment().text) else null
        val columnRels: List<ColumnRel> = ctx.columnDesc().map { column ->
            val columnName = column.identifier().text
            val dataType = column.type().text
            val colComment = if (column.comment() != null) StringUtil.cleanQuote(column.comment().string().text) else null
            ColumnRel(columnName, dataType, colComment)
        }

        val createTable = CreateTable(tableId, comment, columnRels)
        return StatementData(StatementType.CREATE_TABLE, createTable)
    }

    override fun visitAlterTableStatement(ctx: StarRocksParserParser.AlterTableStatementContext?): StatementData {
        return StatementData(StatementType.ALTER_TABLE, AlterTable(AlterType.UNKOWN))
    }

    override fun visitAlterViewStatement(ctx: StarRocksParserParser.AlterViewStatementContext?): StatementData {
        return StatementData(StatementType.ALTER_TABLE, AlterTable(AlterType.ALTER_VIEW))
    }

    fun parseTableName(ctx: StarRocksParserParser.QualifiedNameContext): TableId {
        return if (ctx.identifier().size == 2) {
            TableId(ctx.identifier().get(0).text, ctx.identifier().get(1).text)
        } else if (ctx.identifier().size == 1) {
            TableId(ctx.identifier().get(0).text)
        } else {
            throw SQLParserException("parse qualifiedName error: " + ctx.identifier().size)
        }
    }
}
