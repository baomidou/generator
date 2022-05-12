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
package com.baomidou.mybatisplus.generator.query;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.querys.DbQueryDecorator;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import org.jetbrains.annotations.NotNull;


/**
 *
 * @author nieqiurong
 * @since 3.5.3
 */
public abstract class AbstractDatabaseQuery implements IDatabaseQuery {

    protected final ConfigBuilder configBuilder;

    protected final DataSourceConfig dataSourceConfig;

    protected final StrategyConfig strategyConfig;

    protected final GlobalConfig globalConfig;

    protected final DbQueryDecorator dbQuery;

    protected final DatabaseMetaDataWrapper databaseMetaDataWrapper;


    public AbstractDatabaseQuery(@NotNull ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.dbQuery = new DbQueryDecorator(dataSourceConfig, strategyConfig);
        this.globalConfig = configBuilder.getGlobalConfig();
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dbQuery.getConnection());

    }

    @NotNull
    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    @NotNull
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

}
