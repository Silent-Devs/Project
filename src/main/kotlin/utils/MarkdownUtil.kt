package utils

import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

class MarkdownUtil {
    companion object {
        fun toHtml(markdown: String): String {
            val flavour = CommonMarkFlavourDescriptor()
            val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(markdown)
            return HtmlGenerator(markdown, parsedTree, flavour).generateHtml()
        }
    }
}