#### 3.5.0版本不兼容项

| 类              | 方法             | 修改说明                                                  |
| --------------- | ---------------- | --------------------------------------------------------- |
| FileOutConfig   | outputFile       | 方法返回值修改为File,自定义类直接new File(xxxxx)返回即可. |
| InjectionConfig | prepareObjectMap | 方法返回值修改为void                                      |



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
| swagger2        | 是否生成swagger2注解 | 默认值:false |
| dateType        | 时间策略 | DateType.ONLY_DATE 默认值: DateType.TIME_PACK |
| commentDate | 注释日期 | 默认值: yyyy-MM-dd |

```java
new GlobalConfig.Builder()
   .fileOverride(true).openDir(true).kotlin(false).swagger2(true)
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

```
包名拼接规则:${parent}.${moduleName}.${type}
比如:
parent = "com.baomidou"
moduleName = "sys"
type = "entity"（或者"service"、"serviceImpl"、"mapper"、"xml"、"controller"）
包名就是:com.baomidou.sys.entity
```

```java
new PackageConfig.Builder().parent("com.baomidou.mybatisplus.samples.generator").moduleName("sys").build();
```
