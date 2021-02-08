package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Date;


/**
 * @author nieqiurong 2020/9/21.
 */
public class TableInfoTest {

    public static final DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:h2:mem:test;MODE=mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "sa", "").build();

    @Test
    void getFieldNamesTest() {
        TableInfo tableInfo;
        ConfigBuilder configBuilder;
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null);
        tableInfo = new TableInfo(configBuilder, "name");
        tableInfo.addField(new TableField(configBuilder, "name").setColumnName("name"));
        Assertions.assertEquals(tableInfo.getFieldNames(), "name");

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null);
        tableInfo = new TableInfo(configBuilder, "name");
        tableInfo.addField(new TableField(configBuilder, "name").setColumnName("name"));
        tableInfo.addField( new TableField(configBuilder, "age").setColumnName("age"));
        Assertions.assertEquals(tableInfo.getFieldNames(), "name, age");

        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null);
        tableInfo = new TableInfo(configBuilder, "name");
        tableInfo.addField(new TableField(configBuilder, "name").setColumnName("name"));
        tableInfo.addField(new TableField(configBuilder, "age").setColumnName("age"));
        tableInfo.addField(new TableField(configBuilder, "phone").setColumnName("phone"));
        Assertions.assertEquals(tableInfo.getFieldNames(), "name, age, phone");
    }

    @Test
    void processTableTest() {
        TableInfo tableInfo;
        StrategyConfig strategyConfig;
        strategyConfig = new StrategyConfig.Builder().build();
        tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig()), "user").setName("user");
        tableInfo.processTable();
        Assertions.assertFalse(tableInfo.isConvert());
        Assertions.assertEquals("UserMapper", tableInfo.getMapperName());
        Assertions.assertEquals("UserXml", tableInfo.getXmlName());
        Assertions.assertEquals("IUserService", tableInfo.getServiceName());
        Assertions.assertEquals("UserServiceImpl", tableInfo.getServiceImplName());
        Assertions.assertEquals("UserController", tableInfo.getControllerName());

        GlobalConfig globalConfig;
        strategyConfig = new StrategyConfig.Builder().build();
        globalConfig = new GlobalConfig().setEntityName("%sEntity")
            .setXmlName("%sXml").setMapperName("%sDao").setControllerName("%sAction").setServiceName("%sService").setServiceImplName("%sServiceImp");
        tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, globalConfig), "user");
        tableInfo.processTable();
        Assertions.assertTrue(tableInfo.isConvert());
        Assertions.assertEquals("UserEntity", tableInfo.getEntityName());
        Assertions.assertEquals("UserDao", tableInfo.getMapperName());
        Assertions.assertEquals("UserXml", tableInfo.getXmlName());
        Assertions.assertEquals("UserService", tableInfo.getServiceName());
        Assertions.assertEquals("UserServiceImp", tableInfo.getServiceImplName());
        Assertions.assertEquals("UserAction", tableInfo.getControllerName());
        strategyConfig = new StrategyConfig.Builder().build();
        strategyConfig.entityBuilder().nameConvert(new INameConvert() {
            @Override
            public @NotNull String entityNameConvert(@NotNull TableInfo tableInfo) {
                return "E" + tableInfo.getName();
            }

            @Override
            public @NotNull String propertyNameConvert(@NotNull TableField field) {
                return field.getName();
            }
        });
        tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, null), "user").setName("user");
        tableInfo.processTable();
        Assertions.assertTrue(tableInfo.isConvert());
        Assertions.assertEquals("Euser", tableInfo.getEntityName());
    }

    @Test
    void importPackageTest() {
        TableInfo tableInfo;
        StrategyConfig strategyConfig;
        ConfigBuilder configBuilder;
        tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null), "user");
        tableInfo.importPackage();
        Assertions.assertEquals(1, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));

        tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null), "user").setConvert(true);
        tableInfo.importPackage();
        Assertions.assertEquals(2, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableName.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig().setSuperEntityClass("con.baomihua.demo.SuperEntity");
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, null);
        tableInfo = new TableInfo(configBuilder, "user");
        tableInfo.importPackage();
        Assertions.assertEquals(1, tableInfo.getImportPackages().size());
        Assertions.assertFalse(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains("con.baomihua.demo.SuperEntity"));

        tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, new GlobalConfig().setActiveRecord(true)), "user");
        tableInfo.importPackage();
        Assertions.assertEquals(1, tableInfo.getImportPackages().size());
        Assertions.assertFalse(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Model.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig());
        tableInfo = new TableInfo(configBuilder, "user");
        tableInfo.addField(new TableField(configBuilder, "u_id").setName("u_id").setPropertyName("uid", DbColumnType.LONG).setKeyFlag(true));
        tableInfo.importPackage();
        Assertions.assertEquals(2, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableId.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig());
        tableInfo = new TableInfo(configBuilder, "user");
        tableInfo.addField(new TableField(configBuilder, "u_id").setPropertyName("uid", DbColumnType.LONG).setKeyFlag(true).setKeyIdentityFlag(true));
        tableInfo.importPackage();
        Assertions.assertEquals(3, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableId.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(IdType.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig().setLogicDeleteFieldName("delete_flag");
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig());
        tableInfo = new TableInfo(configBuilder, "user");
        tableInfo.addField(new TableField(configBuilder, "u_id").setName("u_id").setPropertyName("uid", DbColumnType.LONG).setKeyFlag(true));
        tableInfo.addField(new TableField(configBuilder, "delete_flag").setName("delete_flag").setPropertyName("deleteFlag", DbColumnType.BOOLEAN));
        tableInfo.importPackage();
        Assertions.assertEquals(4, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(com.baomidou.mybatisplus.annotation.TableField.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableLogic.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableId.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig().setIdType(IdType.ASSIGN_ID));
        tableInfo = new TableInfo(configBuilder, "user");
        tableInfo.addField(new TableField(configBuilder, "name").setPropertyName("name", DbColumnType.STRING));
        tableInfo.importPackage();
        Assertions.assertEquals(1, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig().setIdType(IdType.ASSIGN_ID));
        tableInfo = new TableInfo(configBuilder, "user").setHavePrimaryKey(true);
        tableInfo.addField(new TableField(configBuilder, "u_id").setName("u_id").setPropertyName("uid", DbColumnType.LONG).setKeyFlag(true));
        tableInfo.importPackage();
        Assertions.assertEquals(3, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableId.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(IdType.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig().entityBuilder().addTableFills(new TableFill("createTime", FieldFill.DEFAULT)).build();
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig());
        tableInfo = new TableInfo(configBuilder, "user").setHavePrimaryKey(true);
        tableInfo.addField(new TableField(configBuilder, "u_id").setName("u_id").setPropertyName("uid", DbColumnType.LONG).setKeyFlag(true));
        tableInfo.addField(new TableField(configBuilder, "create_time").setName("create_time").setPropertyName("createTime", DbColumnType.DATE).setFill(FieldFill.DEFAULT.name()));
        tableInfo.importPackage();
        Assertions.assertEquals(5, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Date.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableId.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(com.baomidou.mybatisplus.annotation.TableField.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(FieldFill.class.getName()));

        strategyConfig = GeneratorBuilder.strategyConfig().setVersionFieldName("version");
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, strategyConfig, null, new GlobalConfig());
        tableInfo = new TableInfo(configBuilder, "user").setHavePrimaryKey(true);
        tableInfo.addField(new TableField(configBuilder, "u_id").setPropertyName("uid", DbColumnType.LONG).setKeyFlag(true));
        tableInfo.addField(new TableField(configBuilder, "version").setPropertyName("version", DbColumnType.LONG));
        tableInfo.importPackage();
        Assertions.assertEquals(3, tableInfo.getImportPackages().size());
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Serializable.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(TableId.class.getName()));
        Assertions.assertTrue(tableInfo.getImportPackages().contains(Version.class.getName()));
    }

    @Test
    void setEntityNameTest() {
        ConfigBuilder configBuilder;
        Assertions.assertTrue(new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null), "user").setName("user").setEntityName("UserEntity").isConvert());
        Assertions.assertFalse(new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null), "user").setName("user").setEntityName("User").isConvert());
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig().setCapitalMode(true), null, null);
        Assertions.assertFalse(new TableInfo(configBuilder, "USER").setName("USER").setEntityName("User").isConvert());
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig().setCapitalMode(true), null, null);
        Assertions.assertTrue(new TableInfo(configBuilder, "USER").setName("USER").setEntityName("UserEntity").isConvert());
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig().setNaming(NamingStrategy.no_change), null, null);
        Assertions.assertTrue(new TableInfo(configBuilder, "test_user").setName("test_user").setEntityName("TestUser").isConvert());
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig().setNaming(NamingStrategy.no_change), null, null);
        Assertions.assertFalse(new TableInfo(configBuilder, "user").setName("user").setEntityName("User").isConvert());
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig().setNaming(NamingStrategy.underline_to_camel), null, null);
        Assertions.assertFalse(new TableInfo(configBuilder, "test_user").setName("test_user").setEntityName("TestUser").isConvert());
        configBuilder = new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig().setNaming(NamingStrategy.underline_to_camel), null, null);
        Assertions.assertTrue(new TableInfo(configBuilder, "TEST_USER").setName("TEST_USER").setEntityName("TestUser").isConvert());
    }

    @Test
    void getFileNameTest() {
        TableInfo tableInfo = new TableInfo(new ConfigBuilder(new PackageConfig.Builder().build(), dataSourceConfig, GeneratorBuilder.strategyConfig(), null, null), "user");
        Assertions.assertEquals("UserEntity", tableInfo.getFileName("User", "", () -> "UserEntity"));
        Assertions.assertEquals("UserEntity", tableInfo.getFileName("User", null, () -> "UserEntity"));
        Assertions.assertEquals("UserTable", tableInfo.getFileName("User", "%sTable", () -> "UserEntity"));
        Assertions.assertEquals("UserTable", tableInfo.getFileName("User", "UserTable", () -> "UserEntity"));
    }

}
