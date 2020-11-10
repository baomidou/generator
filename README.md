#### 数据库配置(DataSourceConfig)

##### 基础配置

| 属性            | 说明       | 示例 |
| --------------- | ---------- | ------ |
| url             | jdbc路径   | jdbc:mysql://127.0.0.1:3306/mybatis-plus |
| username        | 数据库账号 | root  |
| password        | 数据库密码 | 123456 |

```java
new DataSourceConfig.
    Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus","root","123456").build();
```
##### 可选配置

| 属性            | 说明                         | 示例                                  |
| --------------- | ---------------------------- | ------------------------------------- |
| driver          | 数据库驱动                   | Driver.class 或 com.mysql.jdbc.Driver |
| dbType          | 数据库类型                   | DbType.MYSQL                          |
| typeConvert     | 数据库类型转换器             | new MySqlTypeConvert()                |
| keyWordsHandler | 数据库关键字处理器(由于各个版本数据库关键字不一样,暂时只开放了处理接口,建议自行实现)           | new MySqlKeyWordsHandler()            |
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

