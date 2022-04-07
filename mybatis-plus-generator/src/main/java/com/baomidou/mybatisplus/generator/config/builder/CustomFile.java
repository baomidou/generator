package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.generator.config.IConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 自定义模板文件配置
 *
 * @author xusimin
 * @since 2022-04-06
 */
public class CustomFile {

    /**
     * 自定义文件名称
     */
    private String formatFileName;

    /**
     * 自定义模板路径
     */
    private String templatePath;

    /**
     * 自定义文件包名
     */
    private String packageName;

    /**
     * 输出文件之前消费者
     */
    private BiConsumer<TableInfo, Map<String, Object>> beforeOutputFileBiConsumer;

    /**
     * 是否覆盖已有文件（默认 false）
     *
     * @since 3.5.2
     */
    private boolean fileOverride;

    @NotNull
    public void beforeOutputFile(TableInfo tableInfo, Map<String, Object> objectMap) {
        if (null != beforeOutputFileBiConsumer) {
            beforeOutputFileBiConsumer.accept(tableInfo, objectMap);
        }
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public BiConsumer<TableInfo, Map<String, Object>> getBeforeOutputFileBiConsumer() {
        return beforeOutputFileBiConsumer;
    }

    public String getFormatFileName() {
        return formatFileName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public String getPackageName() {
        return packageName;
    }

    /**
     * 构建者
     */
    public static class Builder implements IConfigBuilder<CustomFile> {

        private final CustomFile customFile;

        public Builder() {
            this.customFile = new CustomFile();
        }

        /**
         * 文件名
         */
        public CustomFile.Builder formatFileName(String formatFileName) {
            this.customFile.formatFileName = formatFileName;
            return this;
        }

        /**
         * 模板路径
         */
        public CustomFile.Builder templatePath(String templatePath) {
            this.customFile.templatePath = templatePath;
            return this;
        }

        /**
         * 包路径
         */
        public CustomFile.Builder packageName(String packageName) {
            this.customFile.packageName = packageName;
            return this;
        }

        /**
         * 输出文件之前消费者
         *
         * @param biConsumer 消费者
         * @return this
         */
        public CustomFile.Builder beforeOutputFile(@NotNull BiConsumer<TableInfo, Map<String, Object>> biConsumer) {
            this.customFile.beforeOutputFileBiConsumer = biConsumer;
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public CustomFile.Builder fileOverride() {
            this.customFile.fileOverride = true;
            return this;
        }

        @Override
        public CustomFile build() {
            return this.customFile;
        }
    }
}
