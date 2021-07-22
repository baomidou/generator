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

package com.baomidou.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.util.function.Consumer;

/**
 * mybatis plus FastAutoGenerator
 *
 * @author L.cm
 * @since 2021-07-22
 */
public final class FastAutoGenerator {
    private final DataSourceConfig.Builder dataSourceConfigBuilder;
    private final GlobalConfig.Builder globalConfigBuilder;
    private final TemplateConfig.Builder templateConfigBuilder;
    private final PackageConfig.Builder packageConfigBuilder;
    private final StrategyConfig.Builder strategyConfigBuilder;
    private final InjectionConfig.Builder injectionConfigBuilder;
    private AbstractTemplateEngine templateEngine;

    private FastAutoGenerator(DataSourceConfig.Builder dataSourceConfigBuilder) {
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new GlobalConfig.Builder();
        this.packageConfigBuilder = new PackageConfig.Builder();
        this.strategyConfigBuilder = new StrategyConfig.Builder();
        this.injectionConfigBuilder = new InjectionConfig.Builder();
        this.templateConfigBuilder = new TemplateConfig.Builder();
    }

    public static FastAutoGenerator create(@NotNull String url, String username, String password) {
        return new FastAutoGenerator(new DataSourceConfig.Builder(url, username, password));
    }

    public static FastAutoGenerator create(@NotNull DataSource dataSource) {
        return new FastAutoGenerator(new DataSourceConfig.Builder(dataSource));
    }

    public FastAutoGenerator dataSourceConfig(Consumer<DataSourceConfig.Builder> consumer) {
        consumer.accept(this.dataSourceConfigBuilder);
        return this;
    }

    public FastAutoGenerator globalConfig(Consumer<GlobalConfig.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    public FastAutoGenerator packageConfig(Consumer<PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    public FastAutoGenerator strategyConfig(Consumer<StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);
        return this;
    }

    public FastAutoGenerator injectionConfig(Consumer<InjectionConfig.Builder> consumer) {
        consumer.accept(this.injectionConfigBuilder);
        return this;
    }

    public FastAutoGenerator templateConfig(Consumer<TemplateConfig.Builder> consumer) {
        consumer.accept(this.templateConfigBuilder);
        return this;
    }

    public FastAutoGenerator templateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    public void execute() {
        new AutoGenerator(this.dataSourceConfigBuilder.build())
            // 全局配置
            .global(this.globalConfigBuilder.build())
            // 模板配置
            .template(this.templateConfigBuilder.build())
            // 包配置
            .packageInfo(this.packageConfigBuilder.build())
            // 策略配置
            .strategy(this.strategyConfigBuilder.build())
            // 注入配置
            .injection(this.injectionConfigBuilder.build())
            // 执行
            .execute(this.templateEngine);
    }

}
