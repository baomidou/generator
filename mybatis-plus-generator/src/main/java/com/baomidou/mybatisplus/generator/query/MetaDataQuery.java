package com.baomidou.mybatisplus.generator.query;

import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import com.baomidou.mybatisplus.generator.type.TypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 通过原数据查询数据库信息.
 *
 * @author nieqiurong 2022/5/11.
 */
public class MetaDataQuery extends DefaultDatabaseQuery {

    private final TypeRegistry typeRegistry;

    public MetaDataQuery(@NotNull ConfigBuilder configBuilder) {
        super(configBuilder);
        typeRegistry = new TypeRegistry(configBuilder.getGlobalConfig());
    }

    @Override
    public @NotNull List<TableInfo> queryTables() {
        //TODO 后面修改成通用查询，暂时先调用父类的查询方法
        return super.queryTables();
    }

    @Override
    protected void convertTableFields(@NotNull TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        try {
            Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsInfoMap = databaseMetaDataWrapper.getColumnsInfo(tableName);
            Entity entity = strategyConfig.entity();
            columnsInfoMap.forEach((k, columnsInfo) -> {
                TableField.MetaInfo metaInfo = new TableField.MetaInfo(columnsInfo);
                String columnName = columnsInfo.getName();
                TableField field = new TableField(this.configBuilder, columnName);
                // 处理ID
                if (columnsInfo.isPrimaryKey()) {
                    field.primaryKey(columnsInfo.isAutoIncrement());
                    tableInfo.setHavePrimaryKey(true);
                    if (field.isKeyIdentityFlag() && entity.getIdType() != null) {
                        LOGGER.warn("当前表[{}]的主键为自增主键，会导致全局主键的ID类型设置失效!", tableName);
                    }
                }
                field.setColumnName(columnName)
                    .setComment(columnsInfo.getRemarks());
                String propertyName = entity.getNameConvert().propertyNameConvert(field);
                IColumnType columnType = typeRegistry.getIColumnType(columnsInfo);
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
