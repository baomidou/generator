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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 路径信息处理
 *
 * @author nieqiurong hubin
 * @since 2020-10-06
 * @since 3.5.0
 */
class PathInfoHandler {
    private final Map<OutputFile, String> pathInfo = new HashMap<>();
    private final String outputDir;
    private final PackageConfig packageConfig;

    PathInfoHandler(GlobalConfig globalConfig, TemplateConfig templateConfig, PackageConfig packageConfig) {
        this.outputDir = globalConfig.getOutputDir();
        this.packageConfig = packageConfig;
        Map<OutputFile, String> pathInfo = packageConfig.getPathInfo();
        if (CollectionUtils.isNotEmpty(pathInfo)) {
            setDefaultPathInfo(globalConfig, templateConfig);
            this.pathInfo.putAll(pathInfo);
        }else{
            setDefaultPathInfo(globalConfig, templateConfig);
        }

    }

    private void setDefaultPathInfo(GlobalConfig globalConfig, TemplateConfig templateConfig){
        // 设置默认输出路径
        putPathInfo(templateConfig.getEntity(globalConfig.isKotlin()), OutputFile.entity, ConstVal.ENTITY);
        putPathInfo(templateConfig.getMapper(), OutputFile.mapper, ConstVal.MAPPER);
        putPathInfo(templateConfig.getXml(), OutputFile.mapperXml, ConstVal.MAPPER);
        putPathInfo(templateConfig.getService(), OutputFile.service, ConstVal.SERVICE);
        putPathInfo(templateConfig.getServiceImpl(), OutputFile.serviceImpl, ConstVal.SERVICE_IMPL);
        putPathInfo(templateConfig.getController(), OutputFile.controller, ConstVal.CONTROLLER);
    }

    public Map<OutputFile, String> getPathInfo() {
        return this.pathInfo;
    }

    private void putPathInfo(String template, OutputFile outputFile, String module) {
        if (StringUtils.isNotBlank(template)) {
            pathInfo.putIfAbsent(outputFile, joinPath(outputDir, packageConfig.getPackageInfo(module)));
        }
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }
}
