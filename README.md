#### 数据库配置

```java
// 基础配置
new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus","root","123456")
// 可选扩展属性
.driver(Driver.class)	//指定数据库驱动
.dbType(DbType.MYSQL)	//指定数据库类型
.typeConvert(new MySqlTypeConvert()) //指定类型转换器
.keyWordsHandler(new MySqlKeyWordsHandler())	//指定数据库关键字处理器
.dbQuery(new MySqlQuery())	//指定数据库查询
.schema("test")	//指定数据库schema
.build();
```

