package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * 快速生成
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-16
 */
public class EntityConstantAutoGeneratorTest {

    /**
     * 执行初始化数据库脚本
     */
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.build().getConn();
        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/entity_constant_init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;MODE=MYSQL", "sa", "");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        before();
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
            // 全局配置
            .globalConfig((scanner, builder) -> builder.author("leegoo").fileOverride())
            // 包配置
            .packageConfig((scanner, builder) -> builder.parent("com.test"))
            // 策略配置
            .strategyConfig(builder -> builder.addInclude("t_simple").entityBuilder().enableColumnConstant().nameConvert(new INameConvert() {
                @Override
                public @NotNull String entityNameConvert(@NotNull TableInfo tableInfo) {
                    return tableInfo.getName();
                }

                @Override
                public @NotNull String propertyNameConvert(@NotNull TableField field) {
                    String name = field.getName();
                    if (StringUtils.equalsIgnoreCase("a1",name)){
                        return "appName";
                    }else if (StringUtils.equalsIgnoreCase("normal_name",name)) {
                        return "normalName";
                    }
                    return name;
                }
            }).fileOverride())
            .execute();
    }
}
