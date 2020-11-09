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
package com.baomidou.mybatisplus.generator.config.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.jetbrains.annotations.NotNull;


/**
 * 表信息，关联到当前字段信息
 *
 * @author YangHu
 * @since 2016/8/30
 */
public class TableInfo {

    private final Set<String> importPackages = new HashSet<>();
    private boolean convert;
    private String name;
    private String comment;
    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private final List<TableField> fields = new ArrayList<>();
    private boolean havePrimaryKey;
    /**
     * 公共字段
     */
    private final List<TableField> commonFields = new ArrayList<>();
    private String fieldNames;

    /**
     * @param convert convert
     * @return this
     * @see #setConvert(StrategyConfig)
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    protected TableInfo setConvert(@NotNull StrategyConfig strategyConfig) {
        if (strategyConfig.startsWithTablePrefix(name) || strategyConfig.entity().isTableFieldAnnotationEnable()) {
            // 包含前缀
            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(name)) {
            // 包含
            this.convert = !entityName.equalsIgnoreCase(name);
        } else {
            // 转换字段
            if (NamingStrategy.underline_to_camel == strategyConfig.entity().getColumnNaming()) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(name)) {
                    this.convert = true;
                }
            } else if (!entityName.equalsIgnoreCase(name)) {
                this.convert = true;
            }
        }
        return this;
    }

    public String getEntityPath() {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    /**
     * @param entityName 实体名称
     * @return this
     * @see #setEntityName(StrategyConfig, String)
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setEntityName(@NotNull String entityName) {
        this.entityName = entityName;
        return this;
    }

    public TableInfo setEntityName(@NotNull StrategyConfig strategyConfig, @NotNull String entityName) {
        this.entityName = entityName;
        this.setConvert(strategyConfig);
        return this;
    }


    /**
     * @see #addFields(List)
     * @see #addFields(TableField...)
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setFields(@NotNull List<TableField> fields) {
        this.fields.clear();    //保持语义
        return addFields(fields);
    }

    /**
     * @param fields 字段集合
     * @return this
     * @since 3.5.0
     */
    public TableInfo addFields(@NotNull List<TableField> fields) {
        this.fields.addAll(fields);
        return this;
    }

    /**
     * @param fields 字段集合
     * @return this
     * @since 3.5.0
     */
    public TableInfo addFields(@NotNull TableField... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    /**
     * @param pkg 包空间
     * @return this
     * @see #addImportPackages(String...)
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setImportPackages(@NotNull String pkg) {
        importPackages.clear(); //保持语义
        return addImportPackages(pkg);
    }

    /**
     * @param pkgs 包空间
     * @return this
     * @since 3.5.0
     */
    public TableInfo addImportPackages(@NotNull String... pkgs) {
        importPackages.addAll(Arrays.asList(pkgs));
        return this;
    }

    /**
     * 逻辑删除
     *
     * @see TableField#isLogicDeleteFiled()
     * @deprecated 3.5.0
     */
    @Deprecated
    public boolean isLogicDelete(String logicDeletePropertyName) {
        return fields.parallelStream().anyMatch(tf -> tf.getName().equals(logicDeletePropertyName));
    }

    /**
     * @param fieldNames fieldNames
     * @deprecated 3.5.0 不打算公开此方法了
     */
    @Deprecated
    public TableInfo setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
        return this;
    }

    /**
     * 转换filed实体为 xml mapper 中的 base column 字符串信息
     */
    public String getFieldNames() {
        //TODO 感觉这个也啥必要,不打算公开set方法了
        if (StringUtils.isBlank(fieldNames)) {
            this.fieldNames = this.fields.stream().map(TableField::getColumnName).collect(Collectors.joining(", "));
        }
        return this.fieldNames;
    }

    /**
     * @param commonFields 公共字段
     * @return this
     * @see #addCommonFields(TableField...)
     * @see #addCommonFields(List)
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setCommonFields(@NotNull List<TableField> commonFields) {
        this.commonFields.clear(); //保持语义
        return addCommonFields(commonFields);
    }

    /**
     * 添加公共字段
     *
     * @param commonFields 公共字段
     * @return this
     * @since 3.5.0
     */
    public TableInfo addCommonFields(@NotNull TableField... commonFields) {
        return addCommonFields(Arrays.asList(commonFields));
    }

    /**
     * 添加公共字段
     *
     * @param commonFields 公共字段
     * @return this
     * @since 3.5.0
     */
    public TableInfo addCommonFields(@NotNull List<TableField> commonFields) {
        this.commonFields.addAll(commonFields);
        return this;
    }

    /**
     * 导包处理
     *
     * @param strategyConfig 策略配置
     * @param globalConfig   全局配置
     * @since 3.5.0
     */
    public void importPackage(@NotNull StrategyConfig strategyConfig, @NotNull GlobalConfig globalConfig) {
        boolean importSerializable = true;
        if (StringUtils.isNotBlank(strategyConfig.entity().getSuperClass())) {
            // 自定义父类
            importSerializable = false;
            this.importPackages.add(strategyConfig.entity().getSuperClass());
        } else {
            if (globalConfig.isActiveRecord() || strategyConfig.entity().isActiveRecord()) {
                // 无父类开启 AR 模式
                this.importPackages.add(Model.class.getCanonicalName());
                importSerializable = false;
            }
        }
        if (importSerializable) {
            this.importPackages.add(Serializable.class.getCanonicalName());
        }
        if (this.isConvert()) {
            this.importPackages.add(TableName.class.getCanonicalName());
        }
        IdType idType = Optional.ofNullable(strategyConfig.entity().getIdType()).orElseGet(globalConfig::getIdType);
        if (null != idType && this.isHavePrimaryKey()) {
            // 指定需要 IdType 场景
            this.importPackages.add(IdType.class.getCanonicalName());
            this.importPackages.add(TableId.class.getCanonicalName());
        }
        this.fields.forEach(field -> {
            if (null != field.getColumnType() && null != field.getColumnType().getPkg()) {
                importPackages.add(field.getColumnType().getPkg());
            }
            if (field.isKeyFlag()) {
                // 主键
                if (field.isConvert() || field.isKeyIdentityFlag()) {
                    importPackages.add(TableId.class.getCanonicalName());
                }
                // 自增
                if (field.isKeyIdentityFlag()) {
                    importPackages.add(IdType.class.getCanonicalName());
                }
            } else if (field.isConvert()) {
                // 普通字段
                importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
            }
            if (null != field.getFill()) {
                // 填充字段
                importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                //TODO 好像default的不用处理也行,这个做优化项目.
                importPackages.add(FieldFill.class.getCanonicalName());
            }
            if (field.isVersionField()) {
                this.importPackages.add(Version.class.getCanonicalName());
            }
            if (field.isLogicDeleteFiled()) {
                this.importPackages.add(TableLogic.class.getCanonicalName());
            }
        });
    }

    /**
     * 处理表信息(文件名与导包)
     *
     * @param strategyConfig 策略配置
     * @param globalConfig   全局配置
     * @since 3.5.0
     */
    public void processTable(@NotNull StrategyConfig strategyConfig, @NotNull GlobalConfig globalConfig) {
        String entityName = strategyConfig.entity().getNameConvert().entityNameConvert(this);
        this.setEntityName(strategyConfig, this.getFileName(entityName, globalConfig.getEntityName(), () -> strategyConfig.entity().getConverterFileName().convert(entityName)));
        this.mapperName = this.getFileName(entityName, globalConfig.getMapperName(), () -> strategyConfig.mapper().getConverterMapperFileName().convert(entityName));
        this.xmlName = this.getFileName(entityName, globalConfig.getXmlName(), () -> strategyConfig.mapper().getConverterXmlFileName().convert(entityName));
        this.serviceName = this.getFileName(entityName, globalConfig.getServiceName(), () -> strategyConfig.service().getConverterServiceFileName().convert(entityName));
        this.serviceImplName = this.getFileName(entityName, globalConfig.getServiceImplName(), () -> strategyConfig.service().getConverterServiceImplFileName().convert(entityName));
        this.controllerName = this.getFileName(entityName, globalConfig.getControllerName(), () -> strategyConfig.controller().getConverterFileName().convert(entityName));
        this.importPackage(strategyConfig, globalConfig);
    }


    /**
     * 获取文件名称(含格式化处理)
     *
     * @param entityName   实体名
     * @param value        文件名(支持格式化处理)
     * @param defaultValue 默认文件名
     * @return 文件名称
     * @since 3.5.0
     */
    @Deprecated
    public String getFileName(String entityName, String value, Supplier<String> defaultValue) {
        return StringUtils.isNotBlank(value) ? String.format(value, entityName) : defaultValue.get();
    }

    public TableInfo setName(String name) {
        this.name = name;
        return this;
    }

    public TableInfo setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public TableInfo setMapperName(String mapperName) {
        this.mapperName = mapperName;
        return this;
    }

    public TableInfo setXmlName(String xmlName) {
        this.xmlName = xmlName;
        return this;
    }

    public TableInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public TableInfo setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
        return this;
    }

    public TableInfo setControllerName(String controllerName) {
        this.controllerName = controllerName;
        return this;
    }

    public TableInfo setHavePrimaryKey(boolean havePrimaryKey) {
        this.havePrimaryKey = havePrimaryKey;
        return this;
    }

    @NotNull
    public Set<String> getImportPackages() {
        return importPackages;
    }

    public boolean isConvert() {
        return convert;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getMapperName() {
        return mapperName;
    }

    public String getXmlName() {
        return xmlName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceImplName() {
        return serviceImplName;
    }

    public String getControllerName() {
        return controllerName;
    }

    @NotNull
    public List<TableField> getFields() {
        return fields;
    }

    public boolean isHavePrimaryKey() {
        return havePrimaryKey;
    }

    @NotNull
    public List<TableField> getCommonFields() {
        return commonFields;
    }
}
