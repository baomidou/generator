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
package com.baomidou.mybatisplus.generator.query;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import com.baomidou.mybatisplus.generator.type.ITypeConvertHandler;
import com.baomidou.mybatisplus.generator.type.TypeRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 元数据查询数据库信息.
 *
 * @author nieqiurong 2022/5/11.
 * @see ITypeConvertHandler 类型转换器(如果默认逻辑不能满足，可实现此接口重写类型转换)
 * <p>
 * FAQ:
 * 1.Mysql无法读取表注释: 链接增加属性 remarks=true&useInformationSchema=true
 * @since 3.5.3
 */
public class MetaDataQuery extends AbstractDatabaseQuery {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final TypeRegistry typeRegistry;

    public MetaDataQuery(@NotNull ConfigBuilder configBuilder) {
        super(configBuilder);
        typeRegistry = new TypeRegistry(configBuilder.getGlobalConfig());
    }

    protected List<DatabaseMetaDataWrapper.Table> getTables() {
        boolean skipView = strategyConfig.isSkipView();
        //TODO 暂时采取内存过滤，还需要找数据库测试schema
        return databaseMetaDataWrapper.getTables(null, skipView ? new String[]{"TABLE"} : new String[]{"TABLE", "VIEW"});
    }

    @Override
    public @NotNull List<TableInfo> queryTables() {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();
        List<DatabaseMetaDataWrapper.Table> tables = getTables();
        //需要反向生成或排除的表信息
        List<TableInfo> includeTableList = new ArrayList<>();
        List<TableInfo> excludeTableList = new ArrayList<>();
        tables.forEach(table -> {
            String tableName = table.getName();
            if (StringUtils.isNotBlank(tableName)) {
                TableInfo tableInfo = new TableInfo(this.configBuilder, tableName);
                tableInfo.setComment(table.getRemarks());
                if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                    includeTableList.add(tableInfo);
                } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                    excludeTableList.add(tableInfo);
                }
                tableList.add(tableInfo);
            }
        });
        if (isExclude || isInclude) {
            Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                .stream()
                .filter(s -> !ConfigBuilder.matcherRegTable(s))
                .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (TableInfo tabInfo : tableList) {
                if (notExistTables.isEmpty()) {
                    break;
                }
                //解决可能大小写不敏感的情况导致无法移除掉
                notExistTables.remove(tabInfo.getName().toLowerCase());
            }
            if (notExistTables.size() > 0) {
                LOGGER.warn("表[{}]在数据库中不存在！！！", String.join(StringPool.COMMA, notExistTables.values()));
            }
            // 需要反向生成的表信息
            if (isExclude) {
                tableList.removeAll(excludeTableList);
            } else {
                tableList.clear();
                tableList.addAll(includeTableList);
            }
        }
        // 性能优化，只处理需执行表字段 https://github.com/baomidou/mybatis-plus/issues/219
        tableList.forEach(this::convertTableFields);
        return tableList;
    }

    protected void convertTableFields(@NotNull TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        try {
            Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsInfoMap = databaseMetaDataWrapper.getColumnsInfo(tableName, true);
            Entity entity = strategyConfig.entity();
            columnsInfoMap.forEach((k, columnInfo) -> {
                TableField.MetaInfo metaInfo = new TableField.MetaInfo(columnInfo);
                String columnName = columnInfo.getName();
                TableField field = new TableField(this.configBuilder, columnName);
                // 处理ID
                if (columnInfo.isPrimaryKey()) {
                    field.primaryKey(columnInfo.isAutoIncrement());
                    tableInfo.setHavePrimaryKey(true);
                    if (field.isKeyIdentityFlag() && entity.getIdType() != null) {
                        LOGGER.warn("当前表[{}]的主键为自增主键，会导致全局主键的ID类型设置失效!", tableName);
                    }
                }
                field.setColumnName(columnName).setComment(columnInfo.getRemarks());
                String propertyName = entity.getNameConvert().propertyNameConvert(field);
                IColumnType columnType = typeRegistry.getColumnType(metaInfo);
                ITypeConvertHandler typeConvertHandler = dataSourceConfig.getTypeConvertHandler();
                if (typeConvertHandler != null) {
                    columnType = typeConvertHandler.convert(globalConfig, typeRegistry, metaInfo);
                }
                field.setPropertyName(propertyName, columnType);
                field.setMetaInfo(metaInfo);
                tableInfo.addField(field);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableInfo.processTable();
    }

}
