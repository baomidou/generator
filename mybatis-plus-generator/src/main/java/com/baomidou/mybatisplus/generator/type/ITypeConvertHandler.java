package com.baomidou.mybatisplus.generator.type;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import org.jetbrains.annotations.NotNull;

/**
 * @author nieqiurong 2022/5/12.
 * @since 3.5.3
 */
public interface ITypeConvertHandler {

    /**
     * 转换字段类型
     *
     * @param globalConfig 全局配置
     * @param typeRegistry 类型注册信息
     * @param metaInfo     字段元数据信息
     * @return 子类类型
     */
    @NotNull
    IColumnType convert(GlobalConfig globalConfig, TypeRegistry typeRegistry, TableField.MetaInfo metaInfo);

}
