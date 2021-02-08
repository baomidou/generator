package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import org.h2.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author nieqiurong 2020/10/6.
 */
public class ConfigBuilderTest {

    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig.Builder("jdbc:h2:mem:test;MODE=mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "sa", "").driver(Driver.class).build();

    @Test
    void matcherRegTableTest(){
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("user"));
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("USER"));
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("t_user"));
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("T_USER"));
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("t_user_1"));
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("t_user_12"));
        Assertions.assertFalse(ConfigBuilder.matcherRegTable("t-user-12"));
        Assertions.assertTrue(ConfigBuilder.matcherRegTable("t_user_[0-9]"));
        Assertions.assertTrue(ConfigBuilder.matcherRegTable("t_user_\\d"));
        Assertions.assertTrue(ConfigBuilder.matcherRegTable("t_user_\\d{3,4}"));
        Assertions.assertTrue(ConfigBuilder.matcherRegTable("^t_.*"));
    }

    @Test
    void pathInfoTest() {
        ConfigBuilder configBuilder;
        Map<String, String> pathInfo;
        configBuilder = new ConfigBuilder(GeneratorBuilder.packageConfig(), DATA_SOURCE_CONFIG, GeneratorBuilder.strategyConfig(), GeneratorBuilder.templateConfig(), null);
        pathInfo = configBuilder.getPathInfo();
        Assertions.assertFalse(pathInfo.isEmpty());
        Assertions.assertEquals(6, pathInfo.size());
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.ENTITY_PATH));
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.CONTROLLER_PATH));
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.SERVICE_PATH));
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.SERVICE_IMPL_PATH));
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.XML_PATH));
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.MAPPER_PATH));

        configBuilder = new ConfigBuilder(
            GeneratorBuilder.packageConfigBuilder().pathInfo(Collections.singletonMap(ConstVal.ENTITY_PATH,
                "/tmp/code/entity")).build(), DATA_SOURCE_CONFIG, GeneratorBuilder.strategyConfig(),
            GeneratorBuilder.templateConfig(), null);
        pathInfo = configBuilder.getPathInfo();
        Assertions.assertFalse(pathInfo.isEmpty());
        Assertions.assertEquals(1, pathInfo.size());
        Assertions.assertTrue(pathInfo.containsKey(ConstVal.ENTITY_PATH));
    }
}
