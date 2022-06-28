# CHANGELOG

## [v3.5.3] 2022-06-26

- 新增 springdoc 支持
- 增加类型转换，开发类型转换接口
- 增加数据库连接属性方法指定，去除初始化连接schema指定
- 新增全局覆盖已有文件的配置失效的警告日志
- 新增达梦数据库测试例子，删除多余的驱动引用
- 重构主键，自增主键判断，字段注释获取，表信息获取
- 修复license-gradle-plugin无法在 gradle7.1 下使用问题
- 支持自定义Mapper注解(mapperAnnotation(Mapper.class))
- 通过connection获取catalog与schema
- 过滤方法调整，元数据开放表查询与字段查询方法
- 修改查询Mysql，Mariadb查看列的信息Sql语句
- 当没开启文件覆盖时，生成文件如果已经存在了，提示警告日志
- fileOverride 替换为 enableFileOverride 表达更清晰
- 作者默认值更改为"baomidou"
- fix: 修复win系统下指定输出目录中有空格就报错的BUG
- 默认查询方式为元数据查询
- 删除多余的依赖，删除entity模板多余的空行，修复多余的db文件
- 重构自定义模板生成的逻辑

## [v3.5.2] 2022-03-01

- MySqlKeyWordsHandler 添加MySQL8.0关键字
- Merge pull request #121 from Max-Qiu/develop
- 优化 jetbrains annotations 依赖
- 去掉controller模板中多余的空白行
- Merge pull request #128 from lanjerry/develop
- Merge pull request #123 from ChunMengLu/develop
- 包含String...参数方法新增重载list参数
- refactor:修改 dbQuery 装饰类命名及执行 sql 方法名为 execute
- Merge pull request #146 from lanjerry/develop
- 修复开启AR模式时，生成的实体Serializable未导入依赖bug
- 文件覆盖属性fileOverride()从全局配置移到策略配置中，Entity、Controller、Map
- Merge pull request #157 from lanjerry/develop
- 修复btl模板生成Entity时"pkVal"方法无法获取主键id属性问题
- Merge pull request #160 from wzjjwxy/develop
- 修复controller模板enableHyphenStyle永远为true的bug
- Update controller.java.btl
- Merge pull request #162 from i-Vincent/develop
- fix: tabschema param needs quotes
- Merge pull request #1 from jiminsc/DB2-Tabschema-参数处理错误
- Merge pull request #168 from jiminsc/develop
- Sybase代码生成适配
- Merge pull request #170 from Lroyia/develop
- 统一变量xml的命名，跟之前保持一致，mapperXml->xml
- 修复字段的是否需要转换的属性的BUG
- 策略模式支持需要生成的表，输入用字符串，","号隔开
- Merge pull request #179 from lanjerry/develop
- 修复postgres 高版本（>11 ）数据库的主键判断的BUG.
- Merge pull request #181 from lanjerry/develop
- refactor: 兼容 GitHub issues/151
- refactor: 兼容 Oracle 11版本的字段查询sql
- Merge pull request #186 from lanjerry/develop
- Merge pull request #188 from lanjerry/develop


## [v3.5.1] 2021-09-25

- 重构文件输出路径设置逻辑
- 修改mapper.xml文件输出路径问题
- 添加 FastAutoGenerator 快速生成
- 优化策略配置的验证
- 注入配置新增注入map，以便支持在模板渲染内容中使用自己的map
- feat: service和serviceImpl的模板取消强关联绑定，支持只生成一个
- feat: 支持自定义模板
- fix: 修复enableTableFieldAnnotation注解失效的BUG
- 策略配置新增表名、字段名的后缀过滤功能
- 名称转换默认下划线转驼峰命名
- 默认开启isSerialVersionUID的生成
- 新增ClickHouse字段类型转换
- 修复和调整Kotlin模板
- 修复文档中的Controller策略配置的排版
- 调整代码优化fastAutoGenerator移除多余依赖
- lombok ApiModelProperty 主键移除 value 部分
- 新增 schema 启用控制
- 下划线转驼峰策略，字段不做注解处理


## [v3.5.0]

- 优化代码重构核心构建方式
- 修复swagger注释包含双引号生成代码错误
- swagger2 修改为 swagger 避免以为支持 swagger2 版本
- 修复生成驼峰命名属性字段转换错误
- 修复mysql自动生成代码类型错误(bit,year类型)
- 修复h2数据库主键自增判断错误
- 修复无乐观锁或逻辑删除字段导入多余的包
- 修复Oscar（神通数据库）生成错误
- 修复高斯数据库生成错误
- 修复SQLServer日期生成错误
- 增加虚谷数据库代码生成支持
- 增加ClickHouse代码生成支持
- 支持Beetl模板3.2.x版代码生成
- 去除PG中不包含的clob、blob类型，二进制类型调整为byte类型
- 支持PostgreSql大写表名
- 支持基于模型属性字段填充
- 支持通过数据源构建DataSourceConfig
- velocity提示1.x版本依赖过时，输出日志警告信息
- 表的主键为自增主键时会导致全局主键的ID类型设置生效，输出日志警告信息
- 移除lombok依赖
- 优化 Jdbc 连接关闭逻辑
- PackageConfig,DataSourceConfig,GlobalConfig,StrategyConfig,TemplateConfig更改为构建者模式.
- Entity 新增 ignoreColumns 支持忽略指定字段不生成
- 文本输入 scanner 读取 next 修改为 nextLine

