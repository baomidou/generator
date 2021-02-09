package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.generator.SimpleAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.IConfigBuilder;

/**
 * H2 代码生成
 *
 * @author hubin
 * @since 1.0
 */
public class H2CodeGenerator extends SimpleAutoGenerator {

    @Override
    public IConfigBuilder<DataSourceConfig> dataSourceConfigBuilder() {
        return new DataSourceConfig.Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE",
            "sa", "");
    }

    public static void main(String[] args) {
        // 执行自定义生成代码
        new H2CodeGenerator().execute();
    }
}
