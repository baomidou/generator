### 安装
``` xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>Latest Version</version>
</dependency>
```

### 使用（存在对历史版本的不兼容）
```java
new SimpleAutoGenerator() {
    @Override
    public IConfigBuilder<DataSourceConfig> dataSourceConfigBuilder() {
        return new DataSourceConfig.Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE",
        "sa", "");
    }
}.execute();
```

#### 数据库配置(DataSourceConfig)

##### 基础配置

| 属性          | 说明       | 示例 |
| --------------- | ---------- | ------ |
| url             | jdbc路径   | jdbc:mysql://127.0.0.1:3306/mybatis-plus |
| username        | 数据库账号 | root  |
| password        | 数据库密码 | 123456 |

```java
new DataSourceConfig.
    Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus","root","123456").build();
```
##### 可选配置

| 方法         | 说明                         | 示例                                  |
| --------------- | ---------------------------- | ------------------------------------- |
| driver          | 数据库驱动                   | Driver.class 或 com.mysql.jdbc.Driver |
| dbType          | 数据库类型                   | DbType.MYSQL                          |
| typeConvert     | 数据库类型转换器             | new MySqlTypeConvert()                |
| keyWordsHandler | 数据库关键字处理器          | new MySqlKeyWordsHandler()            |
| dbQuery         | 数据库查询                   | new MySqlQuery()                      |
| schema          | 数据库schema(部分数据库适用) | mybatis-plus                          |

```java
new DataSourceConfig
    .Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus","root","123456")
	.driver(Driver.class)
	.dbType(DbType.MYSQL)
	.typeConvert(new MySqlTypeConvert())
	.keyWordsHandler(new MySqlKeyWordsHandler())
	.dbQuery(new MySqlQuery())
	.schema("mybatis-plus")
	.build();
```

#### 全局配置(GlobalConfig)
| 方法            | 说明       | 示例 |
| --------------- | ---------- | ------ |
| fileOverride             | 是否覆盖已生成文件   | 默认值:false |
| openDir        | 是否打开生成目录 | 默认值:true   |
| outputDir        | 指定输出目录 | /opt/baomidou/ 默认值: windows:D:// linux or mac : /tmp |
| author        | 作者名 | baomidou 默认值:无 |
| kotlin        | 是否生成kotlin | 默认值:false  |
| swagger        | 是否生成swagger注解 | 默认值:false |
| dateType        | 时间策略 | DateType.ONLY_DATE 默认值: DateType.TIME_PACK |
| commentDate | 注释日期 | 默认值: yyyy-MM-dd |

```java
GeneratorBuilder.globalConfigBuilder()
   .fileOverride(true).openDir(true).kotlin(false).swagger(true)
   .outputDir("/opt/baomidou")
   .author("baomidou").dateType(DateType.TIME_PACK).commentDate("yyyy-MM-dd")
   .build();
```

#### 包配置(PackageConfig)
| 方法            | 说明       | 示例 |
| --------------- | ---------- | ------ |
| parent      | 父包名      | 默认值:com.baomidou |
| moduleName  | 父包模块名  | sys 默认值:无 |
| entity      | Entity包名  | 默认值:entity |
| service     | Service包名 | 默认值:service |
| serviceImpl     | Service Impl包名 | 默认值:service.impl |
| mapper     | Mapper包名 | 默认值:mapper |
| xml     | Mapper XML包名 | 默认值:mapper.xml |
| controller     | Controller包名 | 默认值:controller |

```java
new PackageConfig.Builder().parent("com.baomidou.mybatisplus.samples.generator").moduleName("sys").build();
```

#### 模板配置(TemplateConfig)

| 方法                   | 说明                     | 示例                                                |
| ---------------------- | ------------------------ | --------------------------------------------------- |
| all                    | 激活所有默认模板         |                                                     |
| entity                 | 使用默认实体模板         | /templates/entity                                   |
| entity(string)         | 使用自定义实体模板       |                                                     |
| service                | 使用默认配套service模板  | /templates/service.java,/templates/serviceImpl.java |
| service(string,string) | 使用自定义service模板    |                                                     |
| mapper                 | 使用默认mapper模板       | /templates/mapper.java                              |
| mapper(string)         | 使用自定义mapper模板     |                                                     |
| mapperXml              | 使用默认mapperXml模板    | /templates/mapper.xml                               |
| mapperXml(string)      | 使用自定义mapperXml模板  |                                                     |
| controller             | 使用默认controller模板   | /templates/controller.java                          |
| controller(string)     | 使用自定义controller模板 |                                                     |

```java
new TemplateConfig.Builder().all().build();	//激活所有默认模板
```

#### 策略配置(StrategyConfig)

| 方法                      | 说明                     | 示例                                                         |
| ------------------------- | ------------------------ | ------------------------------------------------------------ |
| enableCapitalMode         | 开启大写命名             |                                                    |
| enableSkipView            | 开启跳过视图             |                                                   |
| disableSqlFilter          | 禁用sql过滤              | 语法不能支持使用sql过滤表的话，可以考虑关闭此开关. |
| likeTable                 | 模糊表匹配(sql过滤)      |                                                              |
| notLikeTable              | 模糊表排除(sql过滤)      |                                                              |
| addFieldPrefix(string...) | 增加表字段前缀           |                                                              |
| addInclude(string...)     | 增加表匹配(内存过滤)     |                                                              |
| addExclude(string...)     | 增加表排除匹配(内存过滤) |                                                              |
| addTablePrefix(string...) | 增加表前缀               |                                                              |
| entityBuilder             | 实体策略配置             |                                                              |
| controllerBuilder         | controller策略配置       |                                                              |
| mapperBuilder             | mapper策略配置           |                                                              |
| serviceBuilder            | service策略配置          |                                                              |

##### Entity策略配置

| 方法                             | 说明                             | 示例                          |
| -------------------------------- | -------------------------------- | ----------------------------- |
| nameConvert                      | 名称转换实现                     |                               |
| superClass                       | 父类                             |                    |
| enableSerialVersionUID           | 开启生成serialVersionUID         |                     |
| enableColumnConstant             | 开启生成字段常量                 |                     |
| enableChainModel                 | 开启链式模型                   |                    |
| enableLombok                     | 开启lombok模型                 |                     |
| enableRemoveIsPrefix             | 开启Boolean类型字段移除is前缀    |                     |
| enableTableFieldAnnotationEnable | 开启生成实体时生成字段注解            |                     |
| versionColumnName(string)        | 乐观锁字段名(数据库)             |                               |
| versionPropertyName(string)      | 乐观锁属性名(实体)               |                               |
| logicDeleteColumnName(string)    | 逻辑删除字段名(数据库)           |                               |
| logicDeletePropertyName(string)  | 逻辑删除属性名(实体)             |                               |
| naming                           | 数据库表映射到实体的命名策略     | 默认:NamingStrategy.no_change |
| columnNaming                     | 数据库表字段映射到实体的命名策略 |                               |
| addSuperEntityColumns(string...) | 添加父类公共字段                 |                               |
| addTableFills(IFill...)          | 添加属性填充字段                 |                               |
| enableActiveRecord               | 开启ActiveRecord模型         |                     |
| idType                           | 全局主键类型                     |                               |
| convertFileName                  | 转换文件名称                     |                               |
| formatFileName                   | 格式化文件名称                   |                               |

##### Controller策略配置

| 方法                             | 说明                             | 示例                          |
| -------------------------------- | -------------------------------- | ----------------------------- |
| superClass                       | 父类                             |                     |
| enableHyphenStyle                | 开启驼峰转连字符                 |                    |
| enableRestStyle                  | 开启生成@RestController控制器    |                     |                            |
| convertFileName                  | 转换文件名称                     |                               |
| formatFileName                   | 格式化文件名称                   |                               |

##### Service策略配置

| 方法                       | 说明                        | 示例 |
| -------------------------- | --------------------------- | ---- |
| superServiceClass          | 设置service接口父类         |      |
| superServiceImplClass      | 设置service实现类父类       |      |
| convertServiceFileName     | 转换service接口文件名称     |      |
| convertServiceImplFileName | 转换service实现类文件名称   |      |
| formatServiceFileName      | 格式化service接口文件名称   |      |
| formatServiceImplFileName  | 格式化service实现类文件名称 |      |

##### Mapper策略配置

| 方法                  | 说明                        | 示例       |
| --------------------- | --------------------------- | ---------- |
| superClass            | 设置父类                    |            |
| enableBaseResultMap         | 启用BaseResultMap生成   |  |
| enableBaseColumnList        | 启用BaseColumnList      |  |
| cache        | 设置缓存实现类 |  |
| formatMapperFileName  | 格式化mapper文件名称        |            |
| formatXmlFileName     | 格式化xml实现类文件名称     |            |
| convertMapperFileName | 转换mapper类文件名称        |            |
| convertXmlFileName    | 转换xml文件名称             |            |

```java
new StrategyConfig.Builder()
    .enableSqlFilter(true)// 启用sql过滤
    .capitalMode(true)// 是否大写命名
    .entityBuilder()// 实体配置构建者
        .enableLombok()// 开启lombok模型
        .versionColumnName("version") //乐观锁数据库表字段
        .naming(NamingStrategy.underline_to_camel)// 数据库表映射到实体的命名策略
        .addTableFills(new Column("create_time", FieldFill.INSERT))	//基于数据库字段填充
        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))	//基于模型属性填充
    .controllerBuilder() //控制器属性配置构建
    	.restStyle(true)
    .build();
```

