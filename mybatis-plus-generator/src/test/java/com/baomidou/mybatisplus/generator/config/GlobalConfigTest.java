package com.baomidou.mybatisplus.generator.config;

import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author nieqiurong 2020/10/12.
 */
public class GlobalConfigTest {

    private void buildAssert(GlobalConfig globalConfig) {
        Assertions.assertTrue(globalConfig.isKotlin());
        Assertions.assertTrue(globalConfig.isSwagger2());
        Assertions.assertTrue(globalConfig.isOpen());
        Assertions.assertEquals(globalConfig.getAuthor(), "mp");
        Assertions.assertEquals(globalConfig.getOutputDir(), "/temp/code");
        Assertions.assertEquals(globalConfig.getDateType(), DateType.SQL_PACK);
    }

    @Test
    void builderTest() {
        GlobalConfig globalConfig;
        globalConfig = GeneratorBuilder.globalConfig().setAuthor("mp")
            .setDateType(DateType.SQL_PACK).setOpen(true).setOutputDir("/temp/code")
            .setActiveRecord(true).setBaseColumnList(true).setBaseResultMap(true).setKotlin(true).setSwagger2(true);
        buildAssert(globalConfig);
        globalConfig = GeneratorBuilder.globalConfigBuilder().author("mp")
            .dateType(DateType.SQL_PACK).openDir(true).outputDir("/temp/code").activeRecord(true).baseColumnList(true)
            .baseColumnList(true).kotlin(true).swagger2(true).build();
        buildAssert(globalConfig);
    }

    @Test
    void commentDateTest() {
        String defaultDate = GeneratorBuilder.globalConfig().getCommentDate();
        String commentDate = GeneratorBuilder.globalConfigBuilder().commentDate("yyyy-MM-dd").build().getCommentDate();
        Assertions.assertEquals(defaultDate, commentDate);
        Assertions.assertEquals("2200年11月10日", GeneratorBuilder.globalConfigBuilder().commentDate(() -> "2200年11月10日").build().getCommentDate());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ISO_DATE), GeneratorBuilder.globalConfigBuilder().commentDate(() -> LocalDate.now().format(DateTimeFormatter.ISO_DATE)).build().getCommentDate());
    }
}
