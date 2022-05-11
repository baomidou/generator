package com.baomidou.mybatisplus.generator.type;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nieqiurong 2022/5/11.
 */
public class TypeRegistry {

    private final GlobalConfig globalConfig;

    private final Map<Integer, IColumnType> typeMap = new HashMap<>();
    ;

    public TypeRegistry(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        // byte[]
        typeMap.put(Types.BINARY, DbColumnType.BYTE_ARRAY);
        typeMap.put(Types.BLOB, DbColumnType.BYTE_ARRAY);
        typeMap.put(Types.LONGVARBINARY, DbColumnType.BYTE_ARRAY);
        typeMap.put(Types.VARBINARY, DbColumnType.BYTE_ARRAY);
        //byte
        typeMap.put(Types.TINYINT, DbColumnType.BYTE);
        //long
        typeMap.put(Types.BIGINT, DbColumnType.LONG);
        //boolean
        typeMap.put(Types.BIT, DbColumnType.BOOLEAN);
        typeMap.put(Types.BOOLEAN,DbColumnType.BOOLEAN);
        //short
        typeMap.put(Types.SMALLINT, DbColumnType.SHORT);
        //string
        typeMap.put(Types.CHAR, DbColumnType.STRING);
        typeMap.put(Types.CLOB, DbColumnType.STRING);
        typeMap.put(Types.VARCHAR,DbColumnType.STRING);
        typeMap.put(Types.LONGVARCHAR, DbColumnType.STRING);
        typeMap.put(Types.LONGNVARCHAR, DbColumnType.STRING);
        typeMap.put(Types.NCHAR, DbColumnType.STRING);
        typeMap.put(Types.NCLOB, DbColumnType.STRING);
        typeMap.put(Types.NVARCHAR, DbColumnType.STRING);
        //date
        typeMap.put(Types.DATE, DbColumnType.DATE);
        //timestamp
        typeMap.put(Types.TIMESTAMP, DbColumnType.TIMESTAMP);
        //double
        typeMap.put(Types.FLOAT, DbColumnType.DOUBLE);
        typeMap.put(Types.REAL, DbColumnType.DOUBLE);
        //int
        typeMap.put(Types.INTEGER, DbColumnType.INTEGER);
        //bigDecimal
        typeMap.put(Types.NUMERIC, DbColumnType.BIG_DECIMAL);
        typeMap.put(Types.DECIMAL, DbColumnType.BIG_DECIMAL);
        //TODO 类型需要补充完整
    }

    public IColumnType getIColumnType(DatabaseMetaDataWrapper.ColumnsInfo columnsInfo) {
        int typeCode = columnsInfo.getJdbcType().TYPE_CODE;
        switch (typeCode) {
            case Types.DATE:
                return getDateType();
            case Types.TIME:
                return getTimeType();
            case Types.TIMESTAMP:
                return getTimestampType();
            default:
                return typeMap.getOrDefault(typeCode, DbColumnType.OBJECT);
        }
    }

    private IColumnType getDateType() {
        DbColumnType dbColumnType;
        DateType dateType = globalConfig.getDateType();
        switch (dateType) {
            // TODO 需要增加类型处理，尚未补充完整
            case SQL_PACK:
                dbColumnType = DbColumnType.DATE_SQL;
                break;
            case TIME_PACK:
                dbColumnType = DbColumnType.LOCAL_DATE;
                break;
            default:
                dbColumnType = DbColumnType.DATE;
        }
        return dbColumnType;
    }

    private IColumnType getTimeType() {
        DbColumnType dbColumnType;
        DateType dateType = globalConfig.getDateType();
        if (dateType == DateType.TIME_PACK) {
            dbColumnType = DbColumnType.LOCAL_TIME;
        } else {
            dbColumnType = DbColumnType.TIME;
        }
        return dbColumnType;
    }

    private IColumnType getTimestampType() {
        DbColumnType dbColumnType;
        DateType dateType = globalConfig.getDateType();
        if (dateType == DateType.TIME_PACK) {
            dbColumnType = DbColumnType.LOCAL_DATE_TIME;
        } else {
            dbColumnType = DbColumnType.TIMESTAMP;
        }
        return dbColumnType;
    }

}
