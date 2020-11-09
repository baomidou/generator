/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 * 生成文件
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
public class AutoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * 配置信息
     */
    protected ConfigBuilder config;
    /**
     * 注入配置
     */
    protected InjectionConfig injectionConfig;
    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;
    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;
    /**
     * 包 相关配置
     */
    private PackageConfig packageInfo;
    /**
     * 模板 相关配置
     */
    private TemplateConfig template;
    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;
    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * 后续不公开此构造方法
     *
     * @see #AutoGenerator(DataSourceConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator() {
    }

    /**
     * 构造方法
     *
     * @param dataSourceConfig 数据库配置
     * @since 3.5.0
     */
    public AutoGenerator(@NotNull DataSourceConfig dataSourceConfig) {
        //这个是必须参数,其他都是可选的,后续去除默认构造更改成final
        this.dataSource = dataSourceConfig;
    }

    /**
     * 数据库配置
     *
     * @param dataSource 数据库配置
     * @return this
     * @see #AutoGenerator(DataSourceConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setDataSource(DataSourceConfig dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    /**
     * 指定模板引擎
     *
     * @param templateEngine 模板引擎
     * @return this
     * @see #engine(AbstractTemplateEngine)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setTemplateEngine(AbstractTemplateEngine templateEngine) {
        return engine(templateEngine);
    }

    /**
     * 指定模板引擎
     *
     * @param templateEngine 模板引擎
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator engine(@NotNull AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    /**
     * 注入配置
     *
     * @param injectionConfig 注入配置
     * @return this
     * @see #injection(InjectionConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setInjectionConfig(InjectionConfig injectionConfig) {
        return injection(injectionConfig);
    }

    /**
     * 注入配置
     *
     * @param injectionConfig 注入配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator injection(@NotNull InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    /**
     * 生成策略
     *
     * @param strategyConfig 策略配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator strategy(@NotNull StrategyConfig strategyConfig) {
        this.strategy = strategyConfig;
        return this;
    }

    /**
     * 生成策略
     *
     * @param strategy 策略配置
     * @return this
     * @see #strategy(StrategyConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setStrategy(StrategyConfig strategy) {
        return strategy(strategy);
    }

    /**
     * 指定包配置信息
     *
     * @param packageConfig 包配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator packageInfo(@NotNull PackageConfig packageConfig) {
        this.packageInfo = packageConfig;
        return this;
    }

    /**
     * 指定包配置信息
     *
     * @param packageInfo 包配置
     * @return this
     * @see #packageInfo(PackageConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setPackageInfo(PackageConfig packageInfo) {
        return packageInfo(packageInfo);
    }

    /**
     * 指定模板配置
     *
     * @param template 模板配置
     * @return this
     * @see #template(TemplateConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setTemplate(TemplateConfig template) {
        return template(template);
    }

    /**
     * 指定模板配置
     *
     * @param templateConfig 模板配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator template(@NotNull TemplateConfig templateConfig) {
        this.template = templateConfig;
        return this;
    }

    /**
     * 指定全局配置
     *
     * @param globalConfig 全局配置
     * @return this
     * @see #global(GlobalConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setGlobalConfig(GlobalConfig globalConfig) {
        return global(globalConfig);
    }

    /**
     * 指定全局配置
     *
     * @param globalConfig 全局配置
     * @return this
     * @see 3.5.0
     */
    public AutoGenerator global(@NotNull GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    /**
     * 设置配置汇总
     *
     * @param configBuilder 配置汇总
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator config(@NotNull ConfigBuilder configBuilder) {
        this.config = configBuilder;
        return this;
    }

    /**
     * 设置配置汇总
     *
     * @param config 配置汇总
     * @return this
     * @see #config(ConfigBuilder)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setConfig(ConfigBuilder config) {
        return config(config);
    }

    /**
     * 生成代码
     */
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(packageInfo, dataSource, strategy, template, globalConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == templateEngine) {
            // 为了兼容之前逻辑，采用 Velocity 引擎 【 默认 】
            templateEngine = new VelocityTemplateEngine();
        }
        templateEngine.setConfigBuilder(config);
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("==========================文件生成完成！！！==========================");
    }

    /**
     * 开放表信息、预留子类重写
     *
     * @param config 配置信息
     * @return ignore
     */
    @NotNull
    protected List<TableInfo> getAllTableInfoList(@NotNull ConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * 预处理配置
     *
     * @param config 总配置信息
     * @return 解析数据结果集
     */
    @NotNull
    protected ConfigBuilder pretreatmentConfigBuilder(@NotNull ConfigBuilder config) {
        /*
         * 注入自定义配置
         */
        if (null != injectionConfig) {
            injectionConfig.initMap();
            config.setInjectionConfig(injectionConfig);
        }
        return config;
    }

    /**
     * @return this
     * @see #getInjectionConfig()
     * @deprecated 3.5.0
     */
    @Deprecated
    public InjectionConfig getCfg() {
        return injectionConfig;
    }

    /**
     * @param injectionConfig injectionConfig
     * @return this
     * @see #injection(InjectionConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public AutoGenerator setCfg(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    public ConfigBuilder getConfig() {
        return config;
    }

    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public DataSourceConfig getDataSource() {
        return dataSource;
    }

    public StrategyConfig getStrategy() {
        return strategy;
    }

    public PackageConfig getPackageInfo() {
        return packageInfo;
    }

    public TemplateConfig getTemplate() {
        return template;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public AbstractTemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
