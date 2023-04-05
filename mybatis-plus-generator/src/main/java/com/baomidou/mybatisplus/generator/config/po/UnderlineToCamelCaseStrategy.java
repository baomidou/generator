package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class UnderlineToCamelCaseStrategy implements ColumnNameStrategy {
    @Override
    public String convert(String columnName) {
        return NamingStrategy.underlineToCamel(columnName);
    }
}
