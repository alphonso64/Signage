package com.thingword.alphonso.util;

import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.dialect.SQLServerDialect;

public class SQLServerNativeDialect extends SQLServerDialect {
    public SQLServerNativeDialect() {
        super();
        registerColumnType(Types.VARCHAR, "nvarchar($l)");
        registerColumnType(Types.CLOB, "nvarchar(max)");
        registerHibernateType(-9, "string"); 
    }

   public String getTypeName(int code, int length, int precision, int scale) throws HibernateException {
       if(code != 2005) {
           return super.getTypeName(code, length, precision, scale);
       } else {
           return "ntext";
       }
   }
}