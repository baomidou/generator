/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.jdbc;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author nieqiurong 2021/2/8.
 * @since 3.5.0
 */
public class DatabaseMetaDataWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMetaDataWrapper.class);

    private final DatabaseMetaData databaseMetaData;

    public DatabaseMetaDataWrapper(Connection connection) {
        try {
            this.databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }

    /**
     * 获取表字段信息
     *
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, ColumnsInfo> getColumnsInfo(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        Set<String> primaryKeys = new HashSet<>();
        ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schemaPattern, tableNamePattern);
        while (primaryKeysResultSet.next()) {
            String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
            primaryKeys.add(columnName);
        }
        if (primaryKeys.size() > 1) {
            logger.warn("当前表:{}，存在多主键情况！", tableNamePattern);
        }
        ResultSet resultSet = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, "%");
        Map<String, ColumnsInfo> columnsInfoMap = new HashMap<>();
        while (resultSet.next()) {
            ColumnsInfo columnsInfo = new ColumnsInfo();
            String name = resultSet.getString("COLUMN_NAME");
            columnsInfo.name = name;
            columnsInfo.primaryKey = primaryKeys.contains(name);
            columnsInfo.jdbcType = JdbcType.forCode(resultSet.getInt("DATA_TYPE"));
            columnsInfo.length = resultSet.getInt("COLUMN_SIZE");
            columnsInfo.scale = resultSet.getInt("DECIMAL_DIGITS");
            columnsInfo.remarks = formatComment(resultSet.getString("REMARKS"));
            columnsInfo.defaultValue = resultSet.getString("COLUMN_DEF");
            columnsInfo.nullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
            columnsInfo.autoIncrement = "YES".equals(resultSet.getString("IS_AUTOINCREMENT"));
            columnsInfoMap.put(name.toLowerCase(), columnsInfo);
        }
        return Collections.unmodifiableMap(columnsInfoMap);
    }

    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }

    public Table getTableInfo(String catalog, String schemaPattern, String tableNamePattern) {
        ResultSet resultSet;
        Table table = new Table();
        try {
            resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, new String[]{});
            table.name = tableNamePattern;
            while (resultSet.next()) {
                table.remarks = formatComment(resultSet.getString("REMARKS"));
                table.tableType = resultSet.getString("TABLE_TYPE");
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取表信息:" + tableNamePattern + "错误:", e);
        }
        return table;
    }

    public static class Table {

        private String name;

        private String remarks;

        private String tableType;

        public String getRemarks() {
            return remarks;
        }

        public String getTableType() {
            return tableType;
        }

        public String getName() {
            return name;
        }

        public boolean isView() {
            return "VIEW".equals(tableType);
        }

    }

    public static class ColumnsInfo {

        private boolean primaryKey;

        private boolean autoIncrement;

        private String name;

        private int length;

        private boolean nullable;

        private String remarks;

        private String defaultValue;

        private int scale;

        private JdbcType jdbcType;

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }

        public boolean isNullable() {
            return nullable;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public int getScale() {
            return scale;
        }

        public JdbcType getJdbcType() {
            return jdbcType;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public boolean isAutoIncrement() {
            return autoIncrement;
        }

    }
}
