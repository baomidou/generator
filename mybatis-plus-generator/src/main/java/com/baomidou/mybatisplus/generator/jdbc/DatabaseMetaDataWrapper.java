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

import org.apache.ibatis.type.JdbcType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nieqiurong 2021/2/8.
 * @since 3.5.0
 */
public class DatabaseMetaDataWrapper {

    private final DatabaseMetaData databaseMetaData;

    public DatabaseMetaDataWrapper(Connection connection) throws SQLException {
        this.databaseMetaData = connection.getMetaData();
    }

    /**
     * 获取表字段信息
     *
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, ColumnsInfo> getColumnsInfo(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        ResultSet resultSet = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, "%");
        Map<String, ColumnsInfo> columnsInfoMap = new HashMap<>();
        while (resultSet.next()) {
            ColumnsInfo columnsInfo = new ColumnsInfo();
            String name = resultSet.getString("COLUMN_NAME");
            columnsInfo.name = name;
            columnsInfo.jdbcType = JdbcType.forCode(resultSet.getInt("DATA_TYPE"));
            columnsInfo.length = resultSet.getInt("COLUMN_SIZE");
            columnsInfo.scale = resultSet.getInt("DECIMAL_DIGITS");
            columnsInfo.remarks = resultSet.getString("REMARKS");
            columnsInfo.defaultValue = resultSet.getString("COLUMN_DEF");
            columnsInfo.nullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
            columnsInfoMap.put(name.toLowerCase(), columnsInfo);
        }
        return Collections.unmodifiableMap(columnsInfoMap);
    }

    public static class ColumnsInfo {

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
    }
}
