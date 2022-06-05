package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

public class MysqlGeneratorTest {

    public static void main(String[] args) {
        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:mysql://XXX:3306/baomidou",
                "root", "root").schema("baomidou"))
            .globalConfig(builder -> {
                builder.author("baomidou")
                    .outputDir("/Users/XXX/Downloads");
            })
            .packageConfig(builder -> builder.parent("com.baomidou.mybatisplus.samples.generator"))
            .execute();
    }
}
