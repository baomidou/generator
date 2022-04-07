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
package com.baomidou.mybatisplus.generator.config;

import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * 注入配置
 *
 * @author hubin
 * @since 2016-12-07
 */
public class InjectionConfig {

    /**
     * 自定义配置 Map 对象
     */
    private Map<String, Object> customMap = new HashMap<>();

    /**
     * 自定义模板文件
     */
    private List<CustomFile> customFile = new ArrayList<>();

    @NotNull
    public void beforeOutputFile(TableInfo tableInfo, Map<String, Object> objectMap) {
        if (!customMap.isEmpty()) {
            objectMap.putAll(customMap);
        }
        Optional.ofNullable(customFile).ifPresent(list -> list.forEach(customFile -> {
            if (null != customFile.getBeforeOutputFileBiConsumer()) {
                customFile.getBeforeOutputFileBiConsumer().accept(tableInfo, objectMap);
            }
        }));
    }

    @NotNull
    public Map<String, Object> getCustomMap() {
        return customMap;
    }

    @NotNull
    public List<CustomFile> getCustomFile() {
        return customFile;
    }

    /**
     * 构建者
     */
    public static class Builder implements IConfigBuilder<InjectionConfig> {

        private final InjectionConfig injectionConfig;

        public Builder() {
            this.injectionConfig = new InjectionConfig();
        }

        /**
         * 自定义配置 Map 对象
         *
         * @param customMap Map 对象
         * @return this
         */
        public Builder customMap(@NotNull Map<String, Object> customMap) {
            this.injectionConfig.customMap.putAll(customMap);
            return this;
        }

        /**
         * 自定义配置 Map 对象
         *
         * @param key Map key
         * @param value Map value
         * @return this
         */
        public Builder putCustomMap(@NotNull String key, @NotNull Object value) {
            this.injectionConfig.customMap.put(key, value);
            return this;
        }

        /**
         * 自定义配置模板文件
         *
         * @param consumer 自定义模板文件配置
         * @return this
         */
        public Builder customFile(Consumer<CustomFile.Builder> consumer) {
            CustomFile.Builder builder = new CustomFile.Builder();
            consumer.accept(builder);
            this.injectionConfig.customFile.add(builder.build());
            return this;
        }

        @Override
        public InjectionConfig build() {
            return this.injectionConfig;
        }
    }
}
