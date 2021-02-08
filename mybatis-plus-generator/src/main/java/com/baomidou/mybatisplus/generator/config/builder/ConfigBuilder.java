/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
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
package com.baomidou.mybatisplus.generator.config.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.generator.IDatabaseQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.DecoratorDbQuery;

/**
 * 配置汇总 传递给文件生成工具
 *
 * @author YangHu, tangguo, hubin, Juzi
 * @since 2016-08-30
 */
public class ConfigBuilder {

    /**
     * 模板路径配置信息
     */
    private final TemplateConfig template;
    /**
     * 数据库表信息
     */
    private final List<TableInfo> tableInfoList = new ArrayList<>();
    /**
     * 路径配置信息
     */
    private final Map<String, String> pathInfo = new HashMap<>();
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;
    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;
    /**
     * 注入配置信息
     */
    private InjectionConfig injectionConfig;
    /**
     * 过滤正则
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()+\\\\\\[\\]|{};:'\",<.>?]+");
    /**
     * 包配置信息
     */
    private final PackageConfig packageConfig;

    private final DataSourceConfig dataSourceConfig;

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilder(@Nullable PackageConfig packageConfig, @NotNull DataSourceConfig dataSourceConfig,
                         @Nullable StrategyConfig strategyConfig, @Nullable TemplateConfig template,
                         @Nullable GlobalConfig globalConfig) {
        this.dataSourceConfig = dataSourceConfig;
        this.strategyConfig = Optional.ofNullable(strategyConfig).orElseGet(() -> GeneratorBuilder.strategyConfig());
        //TODO 先把验证插在这里，后续改成build构建的话在build的时候验证
        this.strategyConfig.validate();
        this.globalConfig = Optional.ofNullable(globalConfig).orElseGet(() -> new GlobalConfig.Builder().build());
        this.template = Optional.ofNullable(template).orElseGet(() -> new TemplateConfig.Builder().all().build());
        this.packageConfig = Optional.ofNullable(packageConfig).orElseGet(() -> GeneratorBuilder.packageConfig());
        this.pathInfo.putAll(new PathInfoHandler(this.globalConfig, this.template, this.packageConfig).getPathInfo());
    }

    /**
     * 格式化数据库注释内容
     *
     * @param comment 注释
     * @return 注释
     * @see DecoratorDbQuery.ResultSetWrapper#formatComment(java.lang.String)
     * @since 3.4.0
     * @deprecated 3.5.0
     */
    @Deprecated
    @NotNull
    public String formatComment(@Nullable String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }

    /**
     * 不再建议调用此方法，后续不再公开此方法.
     *
     * @param tableInfoList tableInfoList
     * @return configBuild
     * @deprecated 3.5.0 {@link #getTableInfoList()} 返回引用，如果有需要请直接操作
     */
    @Deprecated
    public ConfigBuilder setTableInfoList(@NotNull List<TableInfo> tableInfoList) {
        this.tableInfoList.clear(); //保持语义
        this.tableInfoList.addAll(tableInfoList);
        return this;
    }

    /**
     * 判断表名是否为正则表名(这表名规范比较随意,只能尽量匹配上特殊符号)
     *
     * @param tableName 表名
     * @return 是否正则
     * @since 3.5.0
     */
    public static boolean matcherRegTable(@NotNull String tableName) {
        return REGX.matcher(tableName).find();
    }

    /**
     * 获取包配置信息
     *
     * @return 包配置信息
     * @see PackageConfig#getPackageInfo()
     * @deprecated 3.5.0
     */
    @Deprecated
    public Map<String, String> getPackageInfo() {
        return packageConfig.getPackageInfo();
    }

    @NotNull
    public ConfigBuilder setStrategyConfig(@NotNull StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    @NotNull
    public ConfigBuilder setGlobalConfig(@NotNull GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    @NotNull
    public ConfigBuilder setInjectionConfig(@NotNull InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    @NotNull
    public TemplateConfig getTemplate() {
        return template;
    }

    @NotNull
    public List<TableInfo> getTableInfoList() {
        if (tableInfoList.isEmpty()) {
            // TODO 暂时不开放自定义
            List<TableInfo> tableInfos = new IDatabaseQuery.DefaultDatabaseQuery(this).queryTables();
            if (!tableInfos.isEmpty()) {
                this.tableInfoList.addAll(tableInfos);
            }
        }
        return tableInfoList;
    }

    @NotNull
    public Map<String, String> getPathInfo() {
        return pathInfo;
    }

    @NotNull
    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    @NotNull
    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    @Nullable
    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    @NotNull
    public PackageConfig getPackageConfig() {
        return packageConfig;
    }

    @NotNull
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }
}
