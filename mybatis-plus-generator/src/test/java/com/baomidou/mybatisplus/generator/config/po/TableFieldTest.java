package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author nieqiurong 2020/10/8.
 */
public class TableFieldTest {

    @Test
    void convertTest() {
        ConfigBuilder configBuilder;
        TableField tableField;
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, new StrategyConfig(), null, new GlobalConfig());
        Assertions.assertFalse(new TableField(configBuilder, "desc").setName("desc").setPropertyName("desc", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "desc").setName("desc").setPropertyName("desc1", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "DESC").setName("DESC").setPropertyName("desc", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "desc").setName("desc").setKeyWords(true).setPropertyName("desc", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "desc").setName("desc").setKeyWords(true).setPropertyName("desc1", DbColumnType.STRING).isConvert());

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, new StrategyConfig().setEntityTableFieldAnnotationEnable(true), null, new GlobalConfig());
        Assertions.assertTrue(new TableField(configBuilder, "name").setName("name").setPropertyName("name", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "name").setName("name").setPropertyName("name1", DbColumnType.STRING).isConvert());

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, new StrategyConfig().setCapitalMode(true), null, new GlobalConfig());
        Assertions.assertFalse(new TableField(configBuilder, "NAME").setName("NAME").setPropertyName("name", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "USER_NAME").setName("USER_NAME").setPropertyName("userName", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "USER_NAME").setName("USER_NAME").setPropertyName("userName1", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "USER_NAME").setName("USER_NAME").setPropertyName("userName", DbColumnType.STRING).isConvert());

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, new StrategyConfig().setColumnNaming(NamingStrategy.underline_to_camel), null, new GlobalConfig());
        Assertions.assertFalse(new TableField(configBuilder, "user_name").setName("user_name").setPropertyName("userName", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "USER_NAME").setName("USER_NAME").setPropertyName("userName", DbColumnType.STRING).isConvert());

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, new StrategyConfig().setColumnNaming(NamingStrategy.no_change), null, new GlobalConfig());
        Assertions.assertTrue(new TableField(configBuilder, "user_name").setName("user_name").setPropertyName("userName", DbColumnType.STRING).isConvert());
        Assertions.assertFalse(new TableField(configBuilder, "USER_NAME").setName("USER_NAME").setPropertyName("USER_NAME", DbColumnType.STRING).isConvert());
        Assertions.assertTrue(new TableField(configBuilder, "NAME").setName("NAME").setPropertyName("name", DbColumnType.STRING).isConvert());

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, new StrategyConfig().setEntityBooleanColumnRemoveIsPrefix(true), null, new GlobalConfig());
        tableField = new TableField(configBuilder, "delete").setName("delete").setPropertyName("delete", DbColumnType.BOOLEAN);
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertFalse(tableField.isConvert());
        tableField = new TableField(configBuilder, "delete").setName("delete").setPropertyName("delete", DbColumnType.BOOLEAN);
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertFalse(tableField.isConvert());
        tableField = new TableField(configBuilder, "is_delete").setName("is_delete").setPropertyName("isDelete", DbColumnType.BOOLEAN);
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertTrue(tableField.isConvert());
        tableField = new TableField(configBuilder, "is_delete").setName("is_delete").setPropertyName("isDelete", DbColumnType.BOOLEAN);
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertTrue(tableField.isConvert());
    }

    @Test
    void versionFieldTest() {
        ConfigBuilder configBuilder;
        StrategyConfig strategyConfig;
        strategyConfig = new StrategyConfig.Builder().entityBuilder().versionColumnName("c_version").build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, strategyConfig, null, new GlobalConfig());
        Assertions.assertFalse(new TableField(configBuilder, "version").setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertFalse(new TableField(configBuilder, "version").setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertTrue(new TableField(configBuilder, "c_version").setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertTrue(new TableField(configBuilder, "C_VERSION").setPropertyName("version", DbColumnType.LONG).isVersionField());

        strategyConfig = new StrategyConfig.Builder().entityBuilder().versionPropertyName("version").build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, strategyConfig, null, new GlobalConfig());
        Assertions.assertTrue(new TableField(configBuilder, "version").setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertTrue(new TableField(configBuilder, "VERSION").setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertFalse(new TableField(configBuilder, "c_version").setPropertyName("cVersion", DbColumnType.LONG).isVersionField());
        Assertions.assertFalse(new TableField(configBuilder, "C_VERSION").setPropertyName("cVersion", DbColumnType.LONG).isVersionField());
    }

    @Test
    void logicDeleteFiledTest() {
        ConfigBuilder configBuilder;
        StrategyConfig strategyConfig;
        strategyConfig = new StrategyConfig.Builder().entityBuilder().logicDeleteColumnName("delete").build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, strategyConfig, null, new GlobalConfig());
        Assertions.assertTrue(new TableField(configBuilder, "DELETE").setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField(configBuilder, "delete").setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());

        strategyConfig = new StrategyConfig.Builder().entityBuilder().logicDeletePropertyName("delete").build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, strategyConfig, null, new GlobalConfig());
        Assertions.assertTrue(new TableField(configBuilder, "IS_DELETE").setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField(configBuilder, "is_delete").setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertFalse(new TableField(configBuilder, "is_delete").setPropertyName("isDelete", DbColumnType.BOOLEAN).isLogicDeleteFiled());

        strategyConfig = new StrategyConfig.Builder().entityBuilder().booleanColumnRemoveIsPrefix(true).logicDeletePropertyName("delete").build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, strategyConfig, null, new GlobalConfig());
        Assertions.assertTrue(new TableField(configBuilder, "IS_DELETE").setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField(configBuilder, "is_delete").setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField(configBuilder, "is_delete").setPropertyName("isDelete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertFalse(new TableField(configBuilder, "is_delete").setPropertyName("isDelete", DbColumnType.INTEGER).isLogicDeleteFiled());
    }

    @Test
    void fillTest() {
        ConfigBuilder configBuilder;
        StrategyConfig strategyConfig;
        strategyConfig = new StrategyConfig.Builder()
            .entityBuilder()
            .addTableFills(
                new TableFill("create_time", FieldFill.INSERT), new TableFill("update_time", FieldFill.UPDATE),
                new PropertyFill("createBy", FieldFill.INSERT), new PropertyFill("updateBy", FieldFill.INSERT)
            ).build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), TableInfoTest.dataSourceConfig, strategyConfig, null, new GlobalConfig());
        Assertions.assertNotNull(new TableField(configBuilder, "create_time").getFill());
        Assertions.assertNotNull(new TableField(configBuilder, "update_time").getFill());
        Assertions.assertNull(new TableField(configBuilder, "name").getFill());
        Assertions.assertNull(new TableField(configBuilder, "createBy").getFill());
        Assertions.assertNull(new TableField(configBuilder, "updateBy").getFill());
        Assertions.assertNull(new TableField(configBuilder, "create_by").getFill());
        Assertions.assertNull(new TableField(configBuilder, "update_by").getFill());
        Assertions.assertNotNull(new TableField(configBuilder, "createBy").setPropertyName("createBy", DbColumnType.STRING).getFill());
        Assertions.assertNotNull(new TableField(configBuilder, "updateBy").setPropertyName("createBy", DbColumnType.STRING).getFill());
    }
}
