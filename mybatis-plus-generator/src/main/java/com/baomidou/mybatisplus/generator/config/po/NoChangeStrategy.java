package com.baomidou.mybatisplus.generator.config.po;

public class NoChangeStrategy implements ColumnNameStrategy {
    @Override
    public String convert(String columnName) {
        return columnName;
    }
}
