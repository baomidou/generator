package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.generator.config.IConfigBuilder;


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
     * 是否覆盖已有文件（默认 false）
     *
     * @since 3.5.2
     */
    private boolean fileOverride;

    public boolean isFileOverride() {
        return fileOverride;
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
