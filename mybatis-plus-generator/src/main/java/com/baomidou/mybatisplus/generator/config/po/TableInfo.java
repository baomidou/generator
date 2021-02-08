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
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.jetbrains.annotations.NotNull;


/**
 * 表信息，关联到当前字段信息
 *
 * @author YangHu
 * @since 2016/8/30
 */
public class TableInfo {

    private final StrategyConfig strategyConfig;
    private final GlobalConfig globalConfig;
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

    private final Entity entity;

    /**
     * 构造方法
     *
     * @param configBuilder 配置构建
     * @param name          表名
     * @since 3.5.0
     */
    public TableInfo(@NotNull ConfigBuilder configBuilder, @NotNull String name) {
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
        this.entity = configBuilder.getStrategyConfig().entity();
        this.name = name;
    }

    /**
     * @param convert convert
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    /**
     * @since 3.5.0
     */
    protected void setConvert() {
        if (strategyConfig.startsWithTablePrefix(name) || entity.isTableFieldAnnotationEnable()) {
            // 包含前缀
            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(name)) {
            // 包含
            this.convert = !entityName.equalsIgnoreCase(name);
        } else {
            // 转换字段
            if (NamingStrategy.underline_to_camel == entity.getColumnNaming()) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(name)) {
                    this.convert = true;
                }
            } else if (!entityName.equalsIgnoreCase(name)) {
                this.convert = true;
            }
        }
    }

    public String getEntityPath() {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    /**
     * @param entityName 实体名称
     * @return this
     */
    public TableInfo setEntityName(@NotNull String entityName) {
        this.entityName = entityName;
        //TODO 先放置在这里
        setConvert();
        return this;
    }

    /**
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setFields(@NotNull List<TableField> fields) {
        this.fields.clear();    //保持语义
        this.fields.addAll(fields);
        return this;
    }

    /**
     * 添加字段
     *
     * @param field 字段
     * @since 3.5.0
     */
    public void addField(@NotNull TableField field) {
        if (entity.matchSuperEntityColumns(field.getColumnName())) {
            this.commonFields.add(field);
        } else {
            this.fields.add(field);
        }
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
     * @see TableField#isLogicDeleteField()
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
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setCommonFields(@NotNull List<TableField> commonFields) {
        this.commonFields.clear(); //保持语义
        this.commonFields.addAll(commonFields);
        return this;
    }

    /**
     * 导包处理
     *
     * @since 3.5.0
     */
    public void importPackage() {
        boolean importSerializable = true;
        String superEntity = entity.getSuperClass();
        if (StringUtils.isNotBlank(superEntity)) {
            // 自定义父类
            importSerializable = false;
            this.importPackages.add(superEntity);
        } else {
            if (globalConfig.isActiveRecord() || entity.isActiveRecord()) {
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
        IdType idType = Optional.ofNullable(entity.getIdType()).orElseGet(globalConfig::getIdType);
        if (null != idType && this.isHavePrimaryKey()) {
            // 指定需要 IdType 场景
            this.importPackages.add(IdType.class.getCanonicalName());
            this.importPackages.add(TableId.class.getCanonicalName());
        }
        this.fields.forEach(field -> {
            IColumnType columnType = field.getColumnType();
            if (null != columnType && null != columnType.getPkg()) {
                importPackages.add(columnType.getPkg());
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
            if (field.isLogicDeleteField()) {
                this.importPackages.add(TableLogic.class.getCanonicalName());
            }
        });
    }

    /**
     * 处理表信息(文件名与导包)
     *
     * @since 3.5.0
     */
    public void processTable() {
        String entityName = entity.getNameConvert().entityNameConvert(this);
        this.setEntityName(this.getFileName(entityName, globalConfig.getEntityName(), () -> entity.getConverterFileName().convert(entityName)));
        this.mapperName = this.getFileName(entityName, globalConfig.getMapperName(), () -> strategyConfig.mapper().getConverterMapperFileName().convert(entityName));
        this.xmlName = this.getFileName(entityName, globalConfig.getXmlName(), () -> strategyConfig.mapper().getConverterXmlFileName().convert(entityName));
        this.serviceName = this.getFileName(entityName, globalConfig.getServiceName(), () -> strategyConfig.service().getConverterServiceFileName().convert(entityName));
        this.serviceImplName = this.getFileName(entityName, globalConfig.getServiceImplName(), () -> strategyConfig.service().getConverterServiceImplFileName().convert(entityName));
        this.controllerName = this.getFileName(entityName, globalConfig.getControllerName(), () -> strategyConfig.controller().getConverterFileName().convert(entityName));
        this.importPackage();
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

    /**
     * @param name 表名
     * @return this
     * @see #TableInfo(ConfigBuilder, String)
     */
    @Deprecated
    public TableInfo setName(String name) {
        this.name = name;
        return this;
    }

    public TableInfo setComment(String comment) {
        //TODO 暂时挪动到这
        this.comment = this.globalConfig.isSwagger2()
            && StringUtils.isNotBlank(comment) ? comment.replace("\"", "\\\"") : comment;
        return this;
    }

    /**
     * @param mapperName mapper文件名称
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setMapperName(String mapperName) {
        this.mapperName = mapperName;
        return this;
    }

    /**
     * @param xmlName xml文件名称
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setXmlName(String xmlName) {
        this.xmlName = xmlName;
        return this;
    }

    /**
     * @param serviceName service文件名称
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    /**
     * @param serviceImplName serviceImpl文件名称
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
    public TableInfo setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
        return this;
    }

    /**
     * @param controllerName controller文件名称
     * @return this
     * @deprecated 3.5.0
     */
    @Deprecated
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
