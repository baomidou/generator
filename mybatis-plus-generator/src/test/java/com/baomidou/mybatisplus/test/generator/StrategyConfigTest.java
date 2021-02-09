/*
 * Copyright (c) 2011-2019, hubin (jobob@qq.com).
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
package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.po.TableInfoTest;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.test.generator.entity.BaseEntity;
import com.baomidou.mybatisplus.test.generator.entity.SuperEntity;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * 策略测试
 * </p>
 *
 * @author hubin
 * @since 2019-02-20
 */
class StrategyConfigTest {

    @Test
    void baseEntity() {
        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(BaseEntity.class);
        Set<String> columns = strategyConfig.getSuperEntityColumns();
        columns.forEach(System.out::println);
        assertThat(columns).containsAll(Arrays.asList("deleted", "createTime", "id"));
        Assertions.assertEquals(columns.size(), 3);
    }

    @Test
    void baseEntityNaming() {
        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(BaseEntity.class).columnNaming(NamingStrategy.underline_to_camel);
        Set<String> columns = strategyConfig.getSuperEntityColumns();
        columns.forEach(System.out::println);
        assertThat(columns).containsAll(Arrays.asList("deleted", "create_time", "id"));
        Assertions.assertEquals(columns.size(), 3);

        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.setSuperEntityColumns("aa", "bb").entityBuilder().superClass(BaseEntity.class).columnNaming(NamingStrategy.underline_to_camel);
        Assertions.assertEquals(strategyConfig.getSuperEntityColumns().size(), 5);
        assertThat(strategyConfig.getSuperEntityColumns()).containsAll(Arrays.asList("aa", "bb", "deleted", "create_time", "id"));

        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(BaseEntity.class).columnNaming(NamingStrategy.underline_to_camel).addSuperEntityColumns("aa", "bb");
        Assertions.assertEquals(strategyConfig.getSuperEntityColumns().size(), 5);
        assertThat(strategyConfig.getSuperEntityColumns()).containsAll(Arrays.asList("aa", "bb", "deleted", "create_time", "id"));
    }

    @Test
    void superEntity() {
        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(SuperEntity.class);
        Set<String> columns = strategyConfig.getSuperEntityColumns();
        columns.forEach(System.out::println);
        assertThat(columns).containsAll(Arrays.asList("deleted", "id"));
        Assertions.assertEquals(columns.size(), 2);
    }

    @Test
    void testSuperAnnotation() {
        StrategyConfig strategyConfig;

        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(SuperBean.class).columnNaming(NamingStrategy.no_change);
        assertThat(strategyConfig.getSuperEntityColumns()).containsAll(Arrays.asList("test_id", "aa_name", "ok", "testName"));

        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(SuperBean.class).columnNaming(NamingStrategy.no_change);
        assertThat(strategyConfig.getSuperEntityColumns()).containsAll(Arrays.asList("test_id", "aa_name", "ok", "testName"));

        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(SuperBean.class).columnNaming(NamingStrategy.underline_to_camel);
        assertThat(strategyConfig.getSuperEntityColumns()).containsAll(Arrays.asList("test_id", "aa_name", "ok", "test_name"));

        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().superClass(SuperBean.class).columnNaming(NamingStrategy.underline_to_camel);
        assertThat(strategyConfig.getSuperEntityColumns()).containsAll(Arrays.asList("test_id", "aa_name", "ok", "test_name"));

    }

    @Test
    void startsWithTablePrefixTest() {
        StrategyConfig.Builder strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        Assertions.assertFalse(strategyConfigBuilder.build().startsWithTablePrefix("t_name"));
        strategyConfigBuilder.addTablePrefix("a_", "t_");
        Assertions.assertTrue(strategyConfigBuilder.build().startsWithTablePrefix("t_name"));
    }

    @Test
    void addTableFillsTest() {
        TableFill tableFill = new TableFill("test", FieldFill.INSERT);
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(tableFill);
        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.setTableFillList(tableFillList);
        Assertions.assertFalse(strategyConfig.getTableFillList().isEmpty());
        strategyConfig = GeneratorBuilder.strategyConfig();
        strategyConfig.entityBuilder().addTableFills(tableFill);
        Assertions.assertFalse(strategyConfig.getTableFillList().isEmpty());
    }

    @Test
    void entityNameConvertTest() {
        TableInfo tableInfo = new TableInfo(new ConfigBuilder(GeneratorBuilder.packageConfig(),
            TableInfoTest.dataSourceConfig, GeneratorBuilder.strategyConfig(),
            null, null), "t_user");

        StrategyConfig.Builder strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        Assertions.assertEquals("T_user", strategyConfigBuilder.build().getNameConvert().entityNameConvert(tableInfo));
        strategyConfigBuilder.addTablePrefix("t_", "a_");
        Assertions.assertEquals("User", strategyConfigBuilder.build().getNameConvert().entityNameConvert(tableInfo));

        strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        Assertions.assertEquals("TUser", strategyConfigBuilder.build().getNameConvert().entityNameConvert(tableInfo));

        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.addTablePrefix("t_", "a_");
        Assertions.assertEquals("User", strategyConfigBuilder.build().getNameConvert().entityNameConvert(tableInfo));

        strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        strategyConfigBuilder.entityBuilder().nameConvert(new INameConvert() {
            @Override
            public @NotNull String entityNameConvert(@NotNull TableInfo tableInfo) {
                return "aaaa";
            }

            @Override
            public @NotNull String propertyNameConvert(@NotNull TableField field) {
                return "bbbb";
            }
        });
        Assertions.assertEquals("aaaa", strategyConfigBuilder.build().getNameConvert().entityNameConvert(tableInfo));
    }

    @Test
    void propertyNameConvertTest() {
        ConfigBuilder configBuilder;
        StrategyConfig.Builder strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        configBuilder = new ConfigBuilder(GeneratorBuilder.packageConfig(), TableInfoTest.dataSourceConfig, strategyConfigBuilder.build(), null, null);
        TableField tableField = new TableField(configBuilder,"c_user_name");
        Assertions.assertEquals("c_user_name", strategyConfigBuilder.build().getNameConvert().propertyNameConvert(tableField));
        strategyConfigBuilder.addTablePrefix("t_", "c_");
        Assertions.assertEquals("user_name", strategyConfigBuilder.build().getNameConvert().propertyNameConvert(tableField));

        strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        Assertions.assertEquals("cUserName", strategyConfigBuilder.build().getNameConvert().propertyNameConvert(tableField));

        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.addTablePrefix("t_", "c_");
        Assertions.assertEquals("userName", strategyConfigBuilder.build().getNameConvert().propertyNameConvert(tableField));

        strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        strategyConfigBuilder.entityBuilder().nameConvert(new INameConvert() {
            @Override
            public @NotNull String entityNameConvert(@NotNull TableInfo tableInfo) {
                return "aaaa";
            }

            @Override
            public @NotNull String propertyNameConvert(@NotNull TableField field) {
                return "bbbb";
            }
        });
        Assertions.assertEquals("bbbb", strategyConfigBuilder.build().getNameConvert().propertyNameConvert(tableField));
    }

    @Test
    void matchExcludeTableTest() {
        StrategyConfig.Builder strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        strategyConfigBuilder.addExclude("system", "user_1", "test[a|b]");
        StrategyConfig strategyConfig = strategyConfigBuilder.build();
        Assertions.assertTrue(strategyConfig.matchExcludeTable("system"));
        Assertions.assertFalse(strategyConfig.matchExcludeTable("test_exclude"));
        Assertions.assertTrue(strategyConfig.matchExcludeTable("testa"));
        Assertions.assertTrue(strategyConfig.matchExcludeTable("testb"));
        Assertions.assertFalse(strategyConfig.matchExcludeTable("testc"));
    }

    @Test
    void matchIncludeTableTest() {
        StrategyConfig.Builder strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        strategyConfigBuilder.addInclude("system", "user_1", "test[a|b]");
        StrategyConfig strategyConfig = strategyConfigBuilder.build();
        Assertions.assertTrue(strategyConfig.matchIncludeTable("system"));
        Assertions.assertFalse(strategyConfig.matchIncludeTable("test_exclude"));
        Assertions.assertTrue(strategyConfig.matchIncludeTable("testa"));
        Assertions.assertTrue(strategyConfig.matchIncludeTable("testb"));
        Assertions.assertFalse(strategyConfig.matchIncludeTable("testc"));
    }

    @Test
    void isCapitalModeNamingTest() {
        Assertions.assertFalse(GeneratorBuilder.strategyConfig().isCapitalModeNaming("T_USER"));
        Assertions.assertFalse(GeneratorBuilder.strategyConfig().setCapitalMode(true).isCapitalModeNaming("user"));
        Assertions.assertFalse(GeneratorBuilder.strategyConfig().setCapitalMode(true).isCapitalModeNaming("user_name"));
        Assertions.assertTrue(GeneratorBuilder.strategyConfig().setCapitalMode(true).isCapitalModeNaming("USER_NAME"));
        Assertions.assertTrue(GeneratorBuilder.strategyConfig().setCapitalMode(true).isCapitalModeNaming("T_USER"));
        Assertions.assertTrue(GeneratorBuilder.strategyConfig().setCapitalMode(true).isCapitalModeNaming("NAME"));
    }

    private void buildAssert(StrategyConfig strategyConfig) {
        Assertions.assertTrue(strategyConfig.isSkipView());
        Assertions.assertTrue(strategyConfig.isChainModel());
        Assertions.assertTrue(strategyConfig.entity().isChain());
        Assertions.assertTrue(strategyConfig.isEntityLombokModel());
        Assertions.assertTrue(strategyConfig.entity().isLombok());
        Assertions.assertTrue(strategyConfig.isEntitySerialVersionUID());
        Assertions.assertTrue(strategyConfig.entity().isSerialVersionUID());
        Assertions.assertTrue(strategyConfig.isControllerMappingHyphenStyle());
        Assertions.assertTrue(strategyConfig.controller().isHyphenStyle());
        Assertions.assertTrue(strategyConfig.isRestControllerStyle());
        Assertions.assertTrue(strategyConfig.controller().isRestStyle());
        Assertions.assertEquals("com.baomidou.mp.SuperController", strategyConfig.getSuperControllerClass());
        Assertions.assertEquals("com.baomidou.mp.SuperController", strategyConfig.controllerBuilder().get().getSuperClass());
        Assertions.assertEquals("com.baomidou.mp.SuperMapper", strategyConfig.getSuperMapperClass());
        Assertions.assertEquals("com.baomidou.mp.SuperMapper", strategyConfig.mapper().getSuperClass());
    }

    @Test
    void builderTest() {
        StrategyConfig strategyConfig;
        strategyConfig = GeneratorBuilder.strategyConfig().setCapitalMode(true).setChainModel(true).setSkipView(true).setEntityLombokModel(true)
            .setEntitySerialVersionUID(true).setControllerMappingHyphenStyle(true).setRestControllerStyle(true)
            .setSuperControllerClass("com.baomidou.mp.SuperController").setSuperMapperClass("com.baomidou.mp.SuperMapper")
        ;
        buildAssert(strategyConfig);
        strategyConfig = new StrategyConfig.Builder().skipView(true)
            .entityBuilder().chainModel(true).lombok(true).serialVersionUID(true)
            .controllerBuilder().superClass("com.baomidou.mp.SuperController").hyphenStyle(true).restStyle(true)
            .mapperBuilder().superClass("com.baomidou.mp.SuperMapper").build();
        buildAssert(strategyConfig);
    }

    @Data
    static class SuperBean {
        @com.baomidou.mybatisplus.annotation.TableId(value = "test_id")
        private String id;

        @com.baomidou.mybatisplus.annotation.TableField(value = "aa_name")
        private String name;

        private String ok;

        private String testName;

    }
}
