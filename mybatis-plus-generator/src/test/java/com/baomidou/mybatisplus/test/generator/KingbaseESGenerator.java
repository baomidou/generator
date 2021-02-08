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
package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;

/**
 * KingbaseESGenerator
 *
 * @author kingbase
 * @since 2019/10/12
 */
public class KingbaseESGenerator extends GeneratorTest {

    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:kingbase8://localhost:54321/mybatis-plus",
            "SYSTEM", "123456").build();
        dsc.setSchemaName("PUBLIC");// 指定 SCHEMA
        dsc.setDbType(DbType.KINGBASE_ES);
        dsc.setTypeConvert(new OracleTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(config, fieldType);
            }
        });
        // 自定义数据库信息查询
        dsc.setDbQuery(new MyKingbaseESQuery());
        dsc.setDriverName("com.kingbase8.Driver");
        int result = scanner();
        AutoGenerator mpg = new AutoGenerator(dsc);

        // 全局配置
        GlobalConfig gc = GeneratorBuilder.globalConfig();
        gc.setOutputDir("D://");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 开启 activeRecord 模式
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        //gc.setKotlin(true); // 是否生成 kotlin 代码
        //gc.setSwagger2(true); // 是否生成 Swagger2 注解
        gc.setAuthor("kingbase");
        gc.setIdType(IdType.AUTO);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setEntityName("%sEntity");
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.global(gc);


        // 策略配置
        StrategyConfig strategy = GeneratorBuilder.strategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名
        // strategy.setDbColumnUnderline(true);// 全局下划线命名
        strategy.setTablePrefix("BMD_", "MP_");// 表前缀
        strategy.setFieldPrefix("A_");
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 允许字段策略独立设置，默认为 naming 策略
        strategy.setInclude("T_USER", "^MP.*", "OK"); // 需要生成的表，支持正则表达式
        // strategy.setExclude("test"); // 排除生成的表，支持正则表达式
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        mpg.strategy(strategy);

        // 包配置
        mpg.packageInfo(GeneratorBuilder.packageConfigBuilder().moduleName("test")
            // 自定义包路径
            .parent("com.baomidou")
            // 这里是控制器包名，默认 web
            .controller("controller").build());

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig(Collections.singletonMap("abc", gc.getAuthor() + "-mp"));
        cfg.addFileOutConfig(new FileOutConfig("/templates/dto.java" + ((1 == result) ? ".ftl" : ".vm")) {
            @Override
            public File outputFile(@NotNull TableInfo tableInfo) {
                // 自定义输入文件名称
                return new File("D://test/my_" + tableInfo.getEntityName() + StringPool.DOT_JAVA);
            }
        });
        mpg.injection(cfg);

        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        // TemplateConfig tc = GeneratorBuilder.templateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // mpg.template(tc);

        // 执行生成
        AbstractTemplateEngine templateEngine = null;
        if (1 == result) {
            templateEngine = new FreemarkerTemplateEngine();
        }
        mpg.execute(templateEngine);
        // 打印注入设置
        System.err.println(mpg.getInjectionConfig().getMap().get("abc"));
    }

}
