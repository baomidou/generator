package com.baomidou.mybatisplus.test.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.h2.Driver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

    private static final String outPutDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "D://tmp" : "/tmp";

    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE","sa","")
        .driver(Driver.class).build();

    @BeforeAll
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.getConn();
        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }


    private StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder().enableSqlFilter(true).capitalMode(true).entityBuilder().lombok(false).naming(NamingStrategy.underline_to_camel).build();
    }

    private GlobalConfig globalConfig() {
        return new GlobalConfig.Builder().activeRecord(false).idType(IdType.ASSIGN_ID).author("test").outputDir(outPutDir).openDir(true).fileOverride(true).build();
    }

    @Test
    void testLike() {
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setLikeTable(new LikeTable("USERS")))
            .execute();
    }

    @Test
    void testNotLike() {
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setNotLikeTable(new LikeTable("USERS")))
            .execute();
    }

    @Test
    void testInclude() {
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setInclude("USERS"))
            .execute();
    }

    @Test
    void testExclude() {
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setExclude("USERS"))
            .execute();
    }

    @Test
    void testLikeAndInclude(){
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setLikeTable(new LikeTable("TABLE")).setInclude("TABLE_PRIVILEGES","TABLE_TYPES"))
            .execute();
    }

    @Test
    void testLikeAndExclude(){
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setLikeTable(new LikeTable("TABLE")).setExclude("TABLE_PRIVILEGES","TABLE_TYPES"))
            .execute();
    }

    @Test
    void testNotLikeAndInclude(){
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setNotLikeTable(new LikeTable("TABLE")).setInclude("USERS"))
            .execute();
    }

    @Test
    void testNotLikeAndExclude(){
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(strategyConfig().setNotLikeTable(new LikeTable("TABLE")).setExclude("USERS"))
            .execute();
    }

    @Test
    void testSimple(){
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(globalConfig())
            .strategy(new StrategyConfig.Builder().capitalMode(true).addTablePrefix("t_").addInclude("t_simple")
                .entityBuilder().versionFieldName("version").naming(NamingStrategy.underline_to_camel).build())
            .execute();
    }
}
