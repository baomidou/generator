package com.baomidou.mybatisplus.generator.jdbc;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

public class DatabaseMetaDataWrapperTest {

    @Test
    void test() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:h2:mem:test;MODE=mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "sa", "").build();
        DatabaseMetaDataWrapper databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceConfig.getConn());
        Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsInfo = databaseMetaDataWrapper.getColumnsInfo(null, null, "USERS");
        Assertions.assertNotNull(columnsInfo);
        DatabaseMetaDataWrapper.ColumnsInfo name = columnsInfo.get("name");
        Assertions.assertTrue(name.isNullable());
        Assertions.assertEquals(JdbcType.VARCHAR, name.getJdbcType());
        Assertions.assertEquals(Integer.MAX_VALUE, name.getLength());
    }

}
