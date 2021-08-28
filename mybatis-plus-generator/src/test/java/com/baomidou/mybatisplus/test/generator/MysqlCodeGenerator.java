package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.generator.SimpleAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.IConfigBuilder;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;

/**
 * H2 代码生成
 *
 * @author hubin
 * @since 1.0
 */
public class MysqlCodeGenerator {

    /**
     * 执行自定义生成代码
     */
    public static void main(String[] args) {
        new SimpleAutoGenerator() {
            @Override
            public IConfigBuilder<DataSourceConfig> dataSourceConfigBuilder() {
                return new DataSourceConfig.Builder(
                    "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                    "root",
                    ""
                );
            }

            @Override
            public IConfigBuilder<InjectionConfig> injectionConfigBuilder() {
                // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
                return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName()
                        + "objectMap: " + objectMap.size());
                });
            }

            /*
            @Override
            public PackageConfig.Builder packageConfigBuilder() {
                return super.packageConfigBuilder().pathInfo(
                    Collections.singletonMap(OutputFile.mapperXml, "D://")
                );
            }
             */
        }.execute();
    }
}
