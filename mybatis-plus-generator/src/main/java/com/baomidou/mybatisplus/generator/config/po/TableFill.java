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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.IFill;
import org.jetbrains.annotations.NotNull;

/**
 * 字段填充
 *
 * @author hubin
 * @since 2017-06-26
 */
public class TableFill implements IFill {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 填充策略
     */
    private FieldFill fieldFill;

    /**
     * 默认填充策略
     *
     * @param fieldName 字段名称
     * @since 3.5.0
     */
    public TableFill(@NotNull String fieldName) {
        this.fieldName = fieldName;
        this.fieldFill = FieldFill.DEFAULT;
    }

    public TableFill(@NotNull String fieldName, @NotNull FieldFill fieldFill) {
        this.fieldName = fieldName;
        this.fieldFill = fieldFill;
    }

    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName 字段名
     * @see #TableFill(String, FieldFill)
     * @deprecated 3.5.0
     */
    @Deprecated
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getName() {
        return this.fieldName;
    }

    public FieldFill getFieldFill() {
        return fieldFill;
    }

    /**
     * @param fieldFill 填充策略
     * @see #TableFill(String, FieldFill)
     * @deprecated 3.5.0
     */
    @Deprecated
    public void setFieldFill(FieldFill fieldFill) {
        this.fieldFill = fieldFill;
    }

    @Override
    public String toString() {
        return "TableFill{" +
            "fieldName='" + fieldName + '\'' +
            ", fieldFill=" + fieldFill +
            '}';
    }
}
