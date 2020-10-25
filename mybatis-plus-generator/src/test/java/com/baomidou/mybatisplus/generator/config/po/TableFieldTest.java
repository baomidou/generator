package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author nieqiurong 2020/10/8.
 */
public class TableFieldTest {

    @Test
    void convertTest(){
        TableField tableField;
        Assertions.assertFalse(new TableField().setName("desc").setPropertyName(new StrategyConfig(), "desc").isConvert());
        Assertions.assertTrue(new TableField().setName("desc").setPropertyName(new StrategyConfig(), "desc1").isConvert());
        Assertions.assertTrue(new TableField().setName("DESC").setPropertyName(new StrategyConfig(), "desc").isConvert());
        Assertions.assertTrue(new TableField().setName("desc").setKeyWords(true).setPropertyName(new StrategyConfig(), "desc").isConvert());
        Assertions.assertTrue(new TableField().setName("desc").setKeyWords(true).setPropertyName(new StrategyConfig(), "desc1").isConvert());
        Assertions.assertTrue(new TableField().setName("name").setPropertyName(new StrategyConfig().setEntityTableFieldAnnotationEnable(true), "name").isConvert());
        Assertions.assertTrue(new TableField().setName("name").setPropertyName(new StrategyConfig().setEntityTableFieldAnnotationEnable(true), "name1").isConvert());
        Assertions.assertFalse(new TableField().setName("NAME").setPropertyName(new StrategyConfig().setCapitalMode(true), "name").isConvert());
        Assertions.assertTrue(new TableField().setName("USER_NAME").setPropertyName(new StrategyConfig().setCapitalMode(true), "userName").isConvert());
        Assertions.assertTrue(new TableField().setName("USER_NAME").setPropertyName(new StrategyConfig().setCapitalMode(true), "userName1").isConvert());
        Assertions.assertTrue(new TableField().setName("USER_NAME").setPropertyName(new StrategyConfig().setCapitalMode(true), "userName").isConvert());
        Assertions.assertFalse(new TableField().setName("user_name").setPropertyName(new StrategyConfig().setColumnNaming(NamingStrategy.underline_to_camel), "userName").isConvert());
        Assertions.assertTrue(new TableField().setName("USER_NAME").setPropertyName(new StrategyConfig().setColumnNaming(NamingStrategy.underline_to_camel), "userName").isConvert());
        Assertions.assertTrue(new TableField().setName("user_name").setPropertyName(new StrategyConfig().setColumnNaming(NamingStrategy.no_change), "userName").isConvert());
        Assertions.assertFalse(new TableField().setName("USER_NAME").setPropertyName(new StrategyConfig().setColumnNaming(NamingStrategy.no_change), "USER_NAME").isConvert());
        Assertions.assertTrue(new TableField().setName("NAME").setPropertyName(new StrategyConfig().setColumnNaming(NamingStrategy.no_change), "name").isConvert());
        tableField = new TableField().setName("delete").setColumnType(DbColumnType.BOOLEAN).setPropertyName(new StrategyConfig().setEntityBooleanColumnRemoveIsPrefix(true), "delete");
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertFalse(tableField.isConvert());
        tableField = new TableField("delete",new StrategyConfig().setEntityBooleanColumnRemoveIsPrefix(true)).setName("delete").setPropertyName("delete", DbColumnType.BOOLEAN);
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertFalse(tableField.isConvert());
        tableField = new TableField().setName("is_delete").setColumnType(DbColumnType.BOOLEAN).setPropertyName(new StrategyConfig().setEntityBooleanColumnRemoveIsPrefix(true), "isDelete");
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertTrue(tableField.isConvert());
        tableField = new TableField("is_delete",new StrategyConfig().setEntityBooleanColumnRemoveIsPrefix(true)).setName("is_delete").setPropertyName("isDelete", DbColumnType.BOOLEAN);
        Assertions.assertEquals("delete", tableField.getPropertyName());
        Assertions.assertTrue(tableField.isConvert());
    }

    @Test
    void versionFieldTest() {
        StrategyConfig strategyConfig;
        strategyConfig = new StrategyConfig.Builder().entityBuilder().versionColumnName("c_version").build();
        Assertions.assertFalse(new TableField("version", strategyConfig).setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertFalse(new TableField("version", strategyConfig).setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertTrue(new TableField("c_version", strategyConfig).setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertTrue(new TableField("C_VERSION", strategyConfig).setPropertyName("version", DbColumnType.LONG).isVersionField());

        strategyConfig = new StrategyConfig.Builder().entityBuilder().versionPropertyName("version").build();
        Assertions.assertTrue(new TableField("version", strategyConfig).setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertTrue(new TableField("VERSION", strategyConfig).setPropertyName("version", DbColumnType.LONG).isVersionField());
        Assertions.assertFalse(new TableField("c_version", strategyConfig).setPropertyName("cVersion", DbColumnType.LONG).isVersionField());
        Assertions.assertFalse(new TableField("C_VERSION", strategyConfig).setPropertyName("cVersion", DbColumnType.LONG).isVersionField());
    }

    @Test
    void logicDeleteFiledTest() {
        StrategyConfig strategyConfig;
        strategyConfig = new StrategyConfig.Builder().entityBuilder().logicDeleteColumnName("delete").build();
        Assertions.assertTrue(new TableField("DELETE", strategyConfig).setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField("delete", strategyConfig).setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());

        strategyConfig = new StrategyConfig.Builder().entityBuilder().logicDeletePropertyName("delete").build();
        Assertions.assertTrue(new TableField("IS_DELETE", strategyConfig).setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField("is_delete", strategyConfig).setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertFalse(new TableField("is_delete", strategyConfig).setPropertyName("isDelete", DbColumnType.BOOLEAN).isLogicDeleteFiled());

        strategyConfig = new StrategyConfig.Builder().entityBuilder().booleanColumnRemoveIsPrefix(true).logicDeletePropertyName("delete").build();
        Assertions.assertTrue(new TableField("IS_DELETE", strategyConfig).setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField("is_delete", strategyConfig).setPropertyName("delete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertTrue(new TableField("is_delete", strategyConfig).setPropertyName("isDelete", DbColumnType.BOOLEAN).isLogicDeleteFiled());
        Assertions.assertFalse(new TableField("is_delete", strategyConfig).setPropertyName("isDelete", DbColumnType.INTEGER).isLogicDeleteFiled());
    }
}
