package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.generator.SimpleAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;

import java.util.Collections;

/**
 * H2 代码生成
 *
 * @author hubin
 * @since 1.0
 */
public class H2CodeGenerator {

    /**
     * 执行自定义生成代码
     */
    public static void main(String[] args) {
        new SimpleAutoGenerator() {
            @Override
            public IConfigBuilder<DataSourceConfig> dataSourceConfigBuilder() {
                return new DataSourceConfig.Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE",
                    "sa", "");
            }

            @Override
            public IConfigBuilder<InjectionConfig> injectionConfigBuilder() {
                // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
                return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName()
                        + "objectMap: " + objectMap.size());
                });
            }

            @Override
            public PackageConfig.Builder packageConfigBuilder() {
                return super.packageConfigBuilder().pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://"));
            }
        }.execute();
    }
}
