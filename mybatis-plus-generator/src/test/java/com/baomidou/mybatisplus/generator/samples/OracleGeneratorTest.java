package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

public class OracleGeneratorTest {

    public static void main(String[] args) {
        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:oracle:thin:@xxx:1521:helowin",
                "system", "system").schema("ANONYMOUS"))
            .globalConfig(builder -> {
                builder.author("baomidou") // 设置作者
                    .enableSwagger() // 开启 swagger 模式
                    .outputDir("D://"); // 指定输出目录
            })
            .packageConfig(builder -> builder.parent("com.baomidou.mybatisplus.samples.generator"))
            .execute();
    }
}
