package com.baomidou.mybatisplus.generator.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

/**
 * @author caol64@gmail.com 2022/2/27.
 */
public class KeyWordsUtils {

    private KeyWordsUtils() {
    }

    private static final Set<String> JAVA_KEYWORDS = unmodifiableSet(new HashSet<>(asList(
        "abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "double",
        "do",
        "else",
        "enum",
        "extends",
        "false",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "interface",
        "int",
        "long",
        "native",
        "new",
        "null",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "true",
        "try",
        "void",
        "volatile",
        "while")));

    @NotNull
    public static String filterJavaKeyWords(@NotNull String propertyName) {
        return JAVA_KEYWORDS.contains(propertyName) ? propertyName + "_" : propertyName;
    }
}
