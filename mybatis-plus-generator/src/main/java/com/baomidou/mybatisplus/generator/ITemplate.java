package com.baomidou.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author nieqiurong 2020/11/9.
 * @since 3.5.0
 */
public interface ITemplate {

    @NotNull
    Map<String, Object> renderData(@NotNull TableInfo tableInfo);

}
