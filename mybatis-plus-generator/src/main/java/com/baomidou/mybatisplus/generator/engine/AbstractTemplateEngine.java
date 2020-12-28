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
package com.baomidou.mybatisplus.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.util.FileUtils;
import com.baomidou.mybatisplus.generator.util.RuntimeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;


/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;


    /**
     * 模板引擎初始化
     */
    @NotNull
    public abstract AbstractTemplateEngine init(@NotNull ConfigBuilder configBuilder);

    /**
     * 自定义内容输出
     *
     * @param injectionConfig 注入配置
     * @param tableInfo       表信息
     * @param objectMap       渲染数据
     * @since 3.5.0
     */
    protected void outputCustomFile(@NotNull InjectionConfig injectionConfig, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
        for (FileOutConfig foc : focList) {
            File outputFile = foc.outputFile(tableInfo);
            if (isCreate(FileType.OTHER, outputFile)) {
                outputFile(outputFile, FileType.OTHER, objectMap, foc.getTemplatePath());
            }
        }
    }

    /**
     * 输出实体文件
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputEntity(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = getPathInfo(ConstVal.ENTITY_PATH);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            getTemplateFilePath(template -> template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())).ifPresent((entity) -> {
                String entityFile = String.format((entityPath + File.separator + "%s" + suffixJavaOrKt()), entityName);
                outputFile(new File(entityFile), FileType.ENTITY, objectMap, entity);
            });
        }
    }

    /**
     * 输出Mapper文件(含xml)
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputMapper(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        // MpMapper.java
        String entityName = tableInfo.getEntityName();
        String mapperPath = getPathInfo(ConstVal.MAPPER_PATH);
        if (StringUtils.isNotBlank(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
            getTemplateFilePath(TemplateConfig::getMapper).ifPresent(mapper -> {
                String mapperFile = String.format((mapperPath + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                outputFile(new File(mapperFile), FileType.MAPPER, objectMap, mapper);
            });
        }
        // MpMapper.xml
        String xmlPath = getPathInfo(ConstVal.XML_PATH);
        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
            getTemplateFilePath(TemplateConfig::getXml).ifPresent(xml -> {
                String xmlFile = String.format((xmlPath + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                outputFile(new File(xmlFile), FileType.XML, objectMap, xml);
            });
        }
    }

    /**
     * 输出service文件
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputService(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        // IMpService.java
        String entityName = tableInfo.getEntityName();
        String servicePath = getPathInfo(ConstVal.SERVICE_PATH);
        if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(servicePath)) {
            getTemplateFilePath(TemplateConfig::getService).ifPresent(service -> {
                String serviceFile = String.format((servicePath + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                outputFile(new File(serviceFile), FileType.SERVICE, objectMap, service);
            });
        }
        // MpServiceImpl.java
        String serviceImplPath = getPathInfo(ConstVal.SERVICE_IMPL_PATH);
        if (StringUtils.isNotBlank(tableInfo.getServiceImplName()) && StringUtils.isNotBlank(serviceImplPath)) {
            getTemplateFilePath(TemplateConfig::getServiceImpl).ifPresent(serviceImpl -> {
                String implFile = String.format((serviceImplPath + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                outputFile(new File(implFile), FileType.SERVICE_IMPL, objectMap, serviceImpl);
            });
        }
    }

    /**
     * 输出controller文件
     *
     * @param tableInfo 表信息
     * @param objectMap 渲染数据
     * @since 3.5.0
     */
    protected void outputController(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        // MpController.java
        String controllerPath = getPathInfo(ConstVal.CONTROLLER_PATH);
        if (StringUtils.isNotBlank(tableInfo.getControllerName()) && StringUtils.isNotBlank(controllerPath)) {
            getTemplateFilePath(TemplateConfig::getController).ifPresent(controller -> {
                String entityName = tableInfo.getEntityName();
                String controllerFile = String.format((controllerPath + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                outputFile(new File(controllerFile), FileType.CONTROLLER, objectMap, controller);
            });
        }
    }

    /**
     * 输出文件
     *
     * @param file         文件
     * @param fileType     文件类型
     * @param objectMap    渲染信息
     * @param templatePath 模板路径
     * @since 3.5.0
     */
    protected void outputFile(@NotNull File file, @NotNull FileType fileType, @NotNull Map<String, Object> objectMap, @NotNull String templatePath) {
        if (isCreate(fileType, file)) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }
                writer(objectMap, templatePath, file);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * 获取模板路径
     *
     * @param function function
     * @return 模板路径
     * @since 3.5.0
     */
    @NotNull
    protected Optional<String> getTemplateFilePath(@NotNull Function<TemplateConfig, String> function) {
        TemplateConfig template = getConfigBuilder().getTemplate();
        String filePath = function.apply(template);
        if (StringUtils.isNotBlank(filePath)) {
            return Optional.of(templateFilePath(filePath));
        }
        return Optional.empty();
    }

    /**
     * 获取路径信息
     *
     * @param key key {@link ConstVal}_xxxPath
     * @return 路径信息
     */
    @Nullable
    protected String getPathInfo(@NotNull String key) {
        Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
        return pathInfo.get(key);
    }

    /**
     * 输出 java xml 文件
     */
    @NotNull
    public AbstractTemplateEngine batchOutput() {
        try {
            InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            tableInfoList.forEach(tableInfo -> {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                if (injectionConfig != null) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    outputCustomFile(injectionConfig, tableInfo, objectMap);
                }
                // 自定义内容
                // Mp.java
                outputEntity(tableInfo, objectMap);
                // mapper and xml
                outputMapper(tableInfo, objectMap);
                // service
                outputService(tableInfo, objectMap);
                // MpController.java
                outputController(tableInfo, objectMap);
            });
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    /**
     * 输出文件
     *
     * @param objectMap    渲染数据
     * @param templatePath 模板路径
     * @param outputFile   输出文件
     * @throws Exception ex
     * @see #outputFile(File, FileType, Map, String)
     * @deprecated 3.5.0
     */
    @Deprecated
    protected void writerFile(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        if (StringUtils.isNotBlank(templatePath)) this.writer(objectMap, templatePath, outputFile);
    }

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @see #writer(Map, String, File)
     * @deprecated 3.5.0
     */
    @Deprecated
    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull String outputFile) throws Exception {

    }

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @throws Exception 异常
     * @since 3.5.0
     */
    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        this.writer(objectMap, templatePath, outputFile.getPath());
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    /**
     * 处理输出目录
     *
     * @deprecated 3.5.0
     */
    @Deprecated
    public AbstractTemplateEngine mkdirs() {
        getConfigBuilder().getPathInfo().forEach((key, value) -> {
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + value + "]");
                }
            }
        });
        return this;
    }


    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        if (getConfigBuilder().getGlobalConfig().isOpen()
            && StringUtils.isNotBlank(outDir)) {
            try {
                RuntimeUtils.openDir(outDir);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    @NotNull
    public Map<String, Object> getObjectMap(@NotNull TableInfo tableInfo) {
        ConfigBuilder config = getConfigBuilder();
        GlobalConfig globalConfig = config.getGlobalConfig();
        Map<String, Object> controllerData = config.getStrategyConfig().controller().renderData(tableInfo);
        Map<String, Object> objectMap = new HashMap<>(controllerData);
        Map<String, Object> mapperData = config.getStrategyConfig().mapper().renderData(tableInfo);
        //　兼容过渡代码
        if (globalConfig.isEnableCache()) {
            mapperData.put("enableCache", true);
        }
        if (globalConfig.isBaseResultMap()) {
            mapperData.put("baseResultMap", true);
        }
        if (globalConfig.isBaseColumnList()) {
            mapperData.put("baseColumnList", true);
        }
        objectMap.putAll(mapperData);
        Map<String, Object> serviceData = config.getStrategyConfig().service().renderData(tableInfo);
        objectMap.putAll(serviceData);
        Map<String, Object> entityData = config.getStrategyConfig().entity().renderData(tableInfo);
        //　兼容过渡代码
        if (globalConfig.getIdType() != null) {
            entityData.put("idType", globalConfig.getIdType().toString());
        }
        if (globalConfig.isActiveRecord()) {
            entityData.put("activeRecord", true);
        }
        objectMap.putAll(entityData);
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageConfig().getPackageInfo());
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger2", globalConfig.isSwagger2());
        objectMap.put("date", globalConfig.getCommentDate());
        objectMap.put("table", tableInfo);
        objectMap.put("entity", tableInfo.getEntityName());
        Optional.ofNullable(config.getInjectionConfig()).ifPresent((injectionConfig) -> injectionConfig.prepareObjectMap(objectMap));
        return objectMap;
    }

    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    @NotNull
    public abstract String templateFilePath(@NotNull String filePath);


    /**
     * 检测文件是否存在
     *
     * @return 文件是否存在
     * @see #isCreate(FileType, File)
     * @deprecated 3.5.0
     */
    @Deprecated
    protected boolean isCreate(FileType fileType, String filePath) {
        return isCreate(fileType, new File(filePath));
    }

    /**
     * 检查文件是否创建文件
     *
     * @param fileType 文件类型
     * @param file     文件
     * @return 是否创建文件
     * @since 3.5.0
     */
    protected boolean isCreate(@NotNull FileType fileType, @NotNull File file) {
        ConfigBuilder cb = getConfigBuilder();
        // 自定义判断
        InjectionConfig ic = cb.getInjectionConfig();
        if (null != ic && null != ic.getFileCreate()) {
            return ic.getFileCreate().isCreate(cb, fileType, file.getPath());
        }
        // 全局判断【默认】
        return !file.exists() || getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return getConfigBuilder().getGlobalConfig().isKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
    }

    @NotNull
    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    @NotNull
    public AbstractTemplateEngine setConfigBuilder(@NotNull ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}
