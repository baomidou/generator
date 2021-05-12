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

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;


/**
 * 全局配置
 *
 * @author hubin
 * @since 2016-12-02
 */
public class GlobalConfig {

    /**
     * 生成文件的输出目录【 windows:D://  linux or mac:/tmp 】
     */
    private String outputDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "D://" : "/tmp";

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;

    /**
     * 是否打开输出目录
     */
    private boolean open = true;

    /**
     * 开发人员
     */
    private String author;

    /**
     * 开启 Kotlin 模式
     */
    private boolean kotlin = false;

    /**
     * 开启 swagger2 模式
     */
    private boolean swagger2 = false;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 支持动态数据源的@DS 注解
     * https://github.com/baomidou/dynamic-datasource-spring-boot-starter
     */
    private String dynamicDataSource;

    /**
     * 获取注释日期
     *
     * @since 3.5.0
     */
    private Supplier<String> commentDate = () -> new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    private GlobalConfig() {
        // 不推荐使用
    }

    public String getOutputDir() {
        return outputDir;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public boolean isOpen() {
        return open;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isKotlin() {
        return kotlin;
    }

    public boolean isSwagger2() {
        return swagger2;
    }

    @NotNull
    public DateType getDateType() {
        return dateType;
    }

    @NotNull
    public String getCommentDate() {
        return commentDate.get();
    }

    public String getDynamicDataSource() {
        return dynamicDataSource;
    }

    public void setDynamicDataSource(String dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    /**
     * 全局配置构建
     *
     * @author nieqiurong 2020/10/11.
     * @since 3.5.0
     */
    public static class Builder implements IConfigBuilder<GlobalConfig> {

        private final GlobalConfig globalConfig;

        public Builder() {
            this.globalConfig = new GlobalConfig();
        }

        /**
         * 覆盖已有文件
         */
        public Builder fileOverride() {
            this.globalConfig.fileOverride = true;
            return this;
        }

        public Builder openDir(boolean open) {
            this.globalConfig.open = open;
            return this;
        }

        public Builder outputDir(@NotNull String outputDir) {
            this.globalConfig.outputDir = outputDir;
            return this;
        }

        public Builder author(@NotNull String author) {
            this.globalConfig.author = author;
            return this;
        }

        /**
         * 开启 kotlin 模式
         */
        public Builder enableKotlin() {
            this.globalConfig.kotlin = true;
            return this;
        }

        /**
         * 开启 swagger2 模式
         */
        public Builder enableSwagger2() {
            this.globalConfig.swagger2 = true;
            return this;
        }

        public Builder dateType(@NotNull DateType dateType) {
            this.globalConfig.dateType = dateType;
            return this;
        }

        /**
         * 注释日志获取处理
         * example: () -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
         *
         * @param commentDate 获取注释日期
         * @return this
         * @since 3.5.0
         */
        public Builder commentDate(@NotNull Supplier<String> commentDate) {
            this.globalConfig.commentDate = commentDate;
            return this;
        }

        /**
         * 指定注释日期格式化
         *
         * @param pattern 格式
         * @return this
         * @since 3.5.0
         */
        public Builder commentDate(@NotNull String pattern) {
            return commentDate(() -> new SimpleDateFormat(pattern).format(new Date()));
        }

        /**
         * 设置动态数据源
         * @see GlobalConfig#dynamicDataSource
         *
         * @param dynamicDataSource 动态数据源配置
         * @return this
         */
        public Builder dynamicDataSource(@NotNull String dynamicDataSource) {
            this.globalConfig.dynamicDataSource = dynamicDataSource;
            return this;
        }

        @Override
        public GlobalConfig build() {
            return this.globalConfig;
        }
    }
}
