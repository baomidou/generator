package com.baomidou.mybatisplus.generator.config.querys;

import com.baomidou.mybatisplus.generator.config.IDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lix
 */
public class Gbase8sQuery implements IDbQuery {

    @Override
    public String tablesSql()
    {
        return "select * from systables where 1=1";
    }

    @Override
    public String tableFieldsSql()
    {
        return "select a.tabname,b.colname,b.coltype, " +
                "case b.colname " +
                "when 'id' then 'PRI' " +
                "end as key, " +
                "case b.coltype " +
                "when '0' then 'CHAR' " +
                "when '1' then 'SMALLINT' " +
                "when '2' then 'INTEGER' " +
                "when '3' then 'FLOAT' " +
                "when '4' then 'SMALLFLOAT' " +
                "when '5' then 'DECIMAL' " +
                "when '6' then 'SERIAL' " +
                "when '7' then 'DATE' " +
                "when '8' then 'MONEY' " +
                "when '9' then 'NULL' " +
                "when '10' then 'DATETIME' " +
                "when '11' then 'BYTE' " +
                "when '12' then 'TEXT' " +
                "when '13' then 'VARCHAR' " +
                "when '14' then 'INTERVAL' " +
                "when '15' then 'NCHAR' " +
                "when '16' then 'NVARCHAR' " +
                "when '17' then 'INT8' " +
                "when '18' then 'SERIAL8' " +
                "when '19' then 'SET' " +
                "when '20' then 'MULTISET' " +
                "when '21' then 'LIST' " +
                "when '22' then 'Unnamed ROW' " +
                "when '40' then 'LVARCHAR' " +
                "when '41' then 'CLOB' " +
                "when '43' then 'BLOB' " +
                "when '44' then 'BOOLEAN' " +
                "when '256' then 'CHAR' " +
                "when '257' then 'SMALLINT' " +
                "when '258' then 'INTEGER' " +
                "when '259' then 'FLOAT' " +
                "when '260' then 'REAL' " +
                "when '261' then 'DECIMAL' " +
                "when '262' then 'SERIAL' " +
                "when '263' then 'DATE' " +
                "when '264' then 'MONEY' " +
                "when '266' then 'DATETIME' " +
                "when '267' then 'BYTE' " +
                "when '268' then 'TEXT' " +
                "when '269' then 'VARCHAR' " +
                "when '270' then 'INTERVAL' " +
                "when '271' then 'NCHAR' " +
                "when '272' then 'NVARCHAR' " +
                "when '273' then 'INT8' " +
                "when '274' then 'SERIAL8' " +
                "when '275' then 'SET' " +
                "when '276' then 'MULTISET' " +
                "when '277' then 'LIST' " +
                "when '278' then 'Unnamed ROW' " +
                "when '296' then 'LVARCHAR' " +
                "when '297' then 'CLOB' " +
                "when '298' then 'BLOB' " +
                "when '299' then 'BOOLEAN' " +
                "when '4118' then 'Named ROW' " +
                "end as coltypename, b.colno from systables a left join syscolumns b on a.tabid=b.tabid where a.tabid>99 and a.tabtype='T' and a.tabname = 'lap20' order by a.tabname,b.colno;";
    }

    @Override
    public String tableName()
    {
        return "tabname";
    }

    @Override
    public String tableComment()
    {
        return "tabname";
    }

    @Override
    public String fieldName()
    {
        return "colname";
    }

    @Override
    public String fieldType()
    {
        return "coltypename";
    }

    @Override
    public String fieldComment()
    {
        return "colname";
    }

    @Override
    public String fieldKey()
    {
        return "key";
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException
    {
        return false;
    }

    @Override
    public String[] fieldCustom()
    {
        return null;
    }
}
