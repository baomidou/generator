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
package com.baomidou.mybatisplus.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.entity.ColumnConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Velocity 模板引擎实现文件输出
 *
 * @author hubin
 * @since 2018-01-10
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine {
    private VelocityEngine velocityEngine;
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static final String TABLE_KEY = "table";
    private static final String ENTITY_COLUMN_CONSTANT_KEY = "entityColumnConstant";

    {
        try {
            Class.forName("org.apache.velocity.util.DuckType");
        } catch (ClassNotFoundException e) {
            // velocity1.x的生成格式错乱 https://github.com/baomidou/generator/issues/5
            logger.warn("Velocity 1.x is outdated, please upgrade to 2.x or later.");
        }
    }

    @Override
    public @NotNull VelocityTemplateEngine init(@NotNull ConfigBuilder configBuilder) {
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty(ConstVal.VM_LOAD_PATH_KEY, ConstVal.VM_LOAD_PATH_VALUE);
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, ConstVal.UTF8);
            p.setProperty(Velocity.INPUT_ENCODING, ConstVal.UTF8);
            p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
            velocityEngine = new VelocityEngine(p);
        }
        return this;
    }


    @Override
    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        Template template = velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, ConstVal.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
             //处理实体类常量配置
             handleEntityConstant(objectMap);
             template.merge(new VelocityContext(objectMap), writer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull String templateFilePath(@NotNull String filePath) {
        final String dotVm = ".vm";
        return filePath.endsWith(dotVm) ? filePath : filePath + dotVm;
    }





    /**
     * @Description 如果用户指定了对象的属性名称（驼峰命名规则）。将会将实体类常量的 名字 改为用户自定义的名字
     * 例如：
     *  1.用户已实现 {@link INameConvert#propertyNameConvert(com.baomidou.mybatisplus.generator.config.po.TableField)}
     *  2.数据库中的字段名为a1
     *  3.用户已将a1转化为app_name
     * 但是此时生成的实体类常量字段却是A1 = "a1" 。
     * 正常的场景应该是 当用户自定义了实体类名称，那么常量的名称应该优先采用用户自定义名称
     * @param objectMap velocity模板入参数据
     * @return void
     * @auther liming
     * @date: 2021/12/30 8:31
     */
    private void handleEntityConstant(Map<String, Object> objectMap) {
        if (null == objectMap  || !objectMap.containsKey(TABLE_KEY) || !objectMap.containsKey(ENTITY_COLUMN_CONSTANT_KEY) || !(Boolean) objectMap.get(ENTITY_COLUMN_CONSTANT_KEY)) {
            return;
        }
        TableInfo tableObj = (TableInfo) objectMap.get(TABLE_KEY);
        if (null == tableObj) {
            return;
        }

        List<TableField> fields = tableObj.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }

        List<ColumnConstant> columnConstantList = new ArrayList<>();
        for (TableField field : fields) {
            ColumnConstant columnConstant = new ColumnConstant();
            String propertyName = field.getPropertyName();
            String name = field.getName();
            String str ;
            if (StringUtils.isNotBlank(propertyName) && !StringUtils.equals(name, propertyName)){
                str =propertyName;
            }else{
                str =name;
            }
            columnConstant.setValue(name);
            columnConstant.setName(camelToLine(str));
            columnConstantList.add(columnConstant);
        }
        objectMap.put("columnConstantList",columnConstantList);
    }


    public static String camelToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0));
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase(Locale.ROOT);
    }
}
