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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * 抽象的对外接口
 *
 * @author hubin
 * @since 2016-12-07
 */
public class InjectionConfig {

    /**
     * 全局配置
     */
    private ConfigBuilder config;

    /**
     * 自定义返回配置 Map 对象
     */
    private final Map<String, Object> map = new HashMap<>();

    /**
     * 自定义输出文件
     */
    private final List<FileOutConfig> fileOutConfigList = new ArrayList<>();

    /**
     * 自定义判断是否创建文件
     *
     * @deprecated 3.5.0
     */
    @Deprecated
    private IFileCreate fileCreate;

    /**
     * 初始化自定义全局参数
     *
     * @see #InjectionConfig(Map)
     * 注入自定义 Map 对象，针对所有表的全局参数
     * @deprecated 3.5.0
     */
    @Deprecated
    public void initMap() {

    }

    /**
     * 依据表相关信息，从三方获取到需要元数据，处理方法环境里面
     *
     * @param tableInfo
     */
    public void initTableMap(@NotNull TableInfo tableInfo) {
        // 子类重写注入表对应补充信息
    }

    /**
     * 模板待渲染 Object Map 预处理<br>
     * com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine
     * 方法： getObjectMap 结果处理
     */
    @NotNull
    public Map<String, Object> prepareObjectMap(@NotNull Map<String, Object> objectMap) {
        objectMap.putAll(getMap());
        return objectMap;
    }

    /**
     * @param map 自定义全局参数
     * @return this
     * @see #InjectionConfig(Map)
     * @deprecated 3.5.0
     */
    @Deprecated
    public InjectionConfig setMap(@NotNull Map<String, Object> map) {
        this.map.putAll(map);
        return this;
    }

    /**
     * 默认构造
     */
    public InjectionConfig() {
    }

    /**
     * 构造方法
     *
     * @param map 自定义全局参数
     * @since 3.5.0
     */
    public InjectionConfig(@NotNull Map<String, Object> map) {
        this.map.putAll(map);
    }

    /**
     * 指定自定义输出文件
     *
     * @param fileOutConfigList 自定义输出文件集合
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
    @NotNull
    public InjectionConfig setFileOutConfigList(@NotNull List<FileOutConfig> fileOutConfigList) {
        this.fileOutConfigList.clear(); //保持方法语义
        return addFileOutConfig(fileOutConfigList);
    }

    /**
     * 添加自定义输出文件
     *
     * @param fileOutConfigList 自定义输出文件集合
     * @return this
     * @since 3.5.0
     */
    @NotNull
    public InjectionConfig addFileOutConfig(@NotNull List<FileOutConfig> fileOutConfigList) {
        this.fileOutConfigList.addAll(fileOutConfigList);
        return this;
    }

    /**
     * 添加自定义输出文件
     *
     * @param fileOutConfigs 自定义输出文件
     * @return this
     * @since 3.5.0
     */
    @NotNull
    public InjectionConfig addFileOutConfig(@NotNull FileOutConfig... fileOutConfigs) {
        return addFileOutConfig(Arrays.asList(fileOutConfigs));
    }

    @NotNull
    public InjectionConfig setConfig(@NotNull ConfigBuilder config) {
        this.config = config;
        return this;
    }

    @NotNull
    public InjectionConfig setFileCreate(@NotNull IFileCreate fileCreate) {
        this.fileCreate = fileCreate;
        return this;
    }

    @NotNull
    public ConfigBuilder getConfig() {
        return config;
    }

    @NotNull
    public Map<String, Object> getMap() {
        return map;
    }

    @NotNull
    public List<FileOutConfig> getFileOutConfigList() {
        return fileOutConfigList;
    }

    @Deprecated
    public IFileCreate getFileCreate() {
        return fileCreate;
    }
}
