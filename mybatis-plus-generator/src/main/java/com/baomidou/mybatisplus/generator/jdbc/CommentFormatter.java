package com.baomidou.mybatisplus.generator.jdbc;

public class CommentFormatter {
    public String formatComment(String comment) {
        // Remove leading and trailing whitespaces
        comment = comment.trim();

        // Split the comment into lines
        String[] lines = comment.split("\\r?\\n");

        // Remove leading and trailing whitespaces from each line
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }

        // Reconstruct the formatted comment
        StringBuilder formattedComment = new StringBuilder();
        formattedComment.append("/**\n");
        for (String line : lines) {
            formattedComment.append(" * ");
            formattedComment.append(line);
            formattedComment.append("\n");
        }
        formattedComment.append(" */\n");

        return formattedComment.toString();
    }
}
