# CHANGELOG

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

