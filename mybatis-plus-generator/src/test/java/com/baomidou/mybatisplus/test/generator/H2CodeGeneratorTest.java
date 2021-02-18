package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * H2代码生成
 *
 * @author nieqiuqiu
 */
class H2CodeGeneratorTest {

    @BeforeAll
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.getConn();
        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE", "sa", "")
        .build();

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig() {
        return GeneratorBuilder.globalConfigBuilder()
            .outputDir(System.getProperty("os.name").toLowerCase().contains("windows") ? "D://tmp" : "/tmp")// 输出目录
            .author("test")// 作者
            .openDir(false)// 是否打开输出目录
            .fileOverride()// 覆盖已有文件
            .build();
    }

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder()
            .entityBuilder() // 实体配置构建者
            .enableLombok() // 是否为lombok模型
            .versionColumnName("version") //乐观锁数据库表字段
            .naming(NamingStrategy.underline_to_camel)// 数据库表映射到实体的命名策略
            .addTableFills(new Column("create_time", FieldFill.INSERT))
            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
            .build();
    }
}
