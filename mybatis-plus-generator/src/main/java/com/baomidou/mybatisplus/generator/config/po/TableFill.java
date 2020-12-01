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
import com.baomidou.mybatisplus.generator.fill.Column;
import org.jetbrains.annotations.NotNull;

/**
 * 字段填充
 *
 * @author hubin
 * @see Column
 * @since 2017-06-26
 * @deprecated 3.5.0
 */
@Deprecated
public class TableFill extends Column {

    public TableFill(@NotNull String fieldName) {
        super(fieldName);
    }

    public TableFill(@NotNull String fieldName, @NotNull FieldFill fieldFill) {
        super(fieldName, fieldFill);
    }

    public String getFieldName() {
        return super.getName();
    }

    @Override
    public @NotNull String getName() {
        return super.getName();
    }

    public @NotNull FieldFill getFieldFill() {
        return super.getFieldFill();
    }

}
