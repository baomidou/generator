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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 抽象的对外接口
 *
 * @author hubin
 * @since 2016-12-07
 */
@Data
@Accessors(chain = true)
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
     */
    private IFileCreate fileCreate;

    /**
     * 初始化自定义全局参数
     *
     * @see #InjectionConfig(Map)
     * 注入自定义 Map 对象，针对所有表的全局参数
     * @deprecated 3.4.1
     */
    @Deprecated
    public void initMap() {

    }

    /**
     * 依据表相关信息，从三方获取到需要元数据，处理方法环境里面
     *
     * @param tableInfo
     */
    public void initTableMap(TableInfo tableInfo) {
        // 子类重写注入表对应补充信息
    }

    /**
     * 模板待渲染 Object Map 预处理<br>
     * com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine
     * 方法： getObjectMap 结果处理
     */
    public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
        return objectMap;
    }

    /**
     * @param map 自定义全局参数
     * @return this
     * @see #InjectionConfig(Map)
     * @deprecated 3.4.1
     */
    @Deprecated
    public InjectionConfig setMap(Map<String, Object> map) {
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
     * @since 3.4.1
     */
    public InjectionConfig(Map<String, Object> map) {
        this.map.putAll(map);
    }

    /**
     * 指定自定义输出文件
     *
     * @param fileOutConfigList 自定义输出文件集合
     * @return this
     * @deprecated 3.4.1
     */
    @Deprecated
    public InjectionConfig setFileOutConfigList(List<FileOutConfig> fileOutConfigList) {
        this.fileOutConfigList.clear(); //保持方法语义
        return addFileOutConfig(fileOutConfigList);
    }

    /**
     * 添加自定义输出文件
     *
     * @param fileOutConfigList 自定义输出文件集合
     * @return this
     * @since 3.4.1
     */
    public InjectionConfig addFileOutConfig(List<FileOutConfig> fileOutConfigList) {
        this.fileOutConfigList.addAll(fileOutConfigList);
        return this;
    }

    /**
     * 添加自定义输出文件
     *
     * @param fileOutConfig 自定义输出文件
     * @return this
     * @since 3.4.1
     */
    public InjectionConfig addFileOutConfig(FileOutConfig fileOutConfig) {
        this.fileOutConfigList.add(fileOutConfig);
        return this;
    }

}
