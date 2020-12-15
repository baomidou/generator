package com.baomidou.mybatisplus.test.generator;


import java.sql.Driver;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * issue19 generator test
 *
 * @author xiao liang
 * @since 2020/12/15
 */
public class Isuue19Generator {


    public static void main(String[] args) {

        /**
         * 示例sql脚本
         * CREATE TABLE `t_order` (
         *   `id` int(11) NOT NULL AUTO_INCREMENT,
         *   `state` bit(1) DEFAULT NULL,
         *   `msgstate` bit(64) DEFAULT NULL,
         *   `myyear` year(4) DEFAULT NULL,
         *   `timetest` time(6) DEFAULT NULL,
         *   PRIMARY KEY (`id`)
         * )
         */
        String url = "jdbc:mysql://localhost:3306/atest?serverTimezone=GMT%2B8";
        String username = "root";
        String password = "qinliang";
        String driverName = Driver.class.getName();
        DbType dbType = DbType.MYSQL;

        String parentPackName = "com.baomidou.mybatisplus.generator.news";

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置

        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("xiao liang");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService"); //去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.TIME_PACK);//定义生成的实体类中日期类型
        gc.setSwagger2(false);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        //dsc.setUrl("jdbc:mysql://localhost:3306/atest?serverTimezone=GMT%2B8");
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setDbType(dbType);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null); //模块名
        pc.setParent(parentPackName);
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);
        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("t_order");//对那一张表生成代码
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }

}
