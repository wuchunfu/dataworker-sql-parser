package com.github.melin.superior.sql.parser.util

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNodeImpl
import org.apache.commons.lang3.StringUtils

/** Created by libinsong on 2017/4/10. */
object CommonUtils {

    val KEYWORD_REGEX = "'([A-Z_]+)'".toRegex()

    fun findShowStatementKeyWord(keyWords: ArrayList<String>, node: ParseTree) {
        if (node is TerminalNodeImpl) {
            val count = node.parent.childCount
            for (i in 0 until count) {
                val child = node.parent.getChild(i)
                if (child is TerminalNodeImpl) {
                    keyWords.add(child.text.uppercase())
                }
            }
        } else {
            findShowStatementKeyWord(keyWords, node.getChild(0))
        }
    }

    fun subsql(sql: String?, context: ParserRuleContext): String {
        val currSql = StringUtils.substring(sql, context.start.startIndex, context.stop.stopIndex + 1)
        return cleanLastSemi(currSql)
    }

    fun subsql(sql: String?, start: Token, stop: Token): String {
        val currSql = StringUtils.substring(sql, start.stopIndex + 1, stop.stopIndex + 1)
        return cleanLastSemi(currSql)
    }

    private fun cleanLastSemi(text: String): String {
        if (StringUtils.endsWith(text, ";")) {
            return StringUtils.substring(text, 0, text.length - 1)
        }

        return text
    }

    fun cleanQuote(value: String): String {
        if (StringUtils.isBlank(value)) {
            return value
        }

        var result = value
        if (StringUtils.startsWith(result, "'") && StringUtils.endsWith(result, "'")) {
            result = StringUtils.substring(result, 1, -1)
        }

        if (StringUtils.startsWith(result, "\"") && StringUtils.endsWith(result, "\"")) {
            result = StringUtils.substring(result, 1, -1)
        }

        if (StringUtils.startsWith(value, "`") && StringUtils.endsWith(value, "`")) {
            return StringUtils.substring(value, 1, -1)
        }

        return StringUtils.trim(result)
    }
}
