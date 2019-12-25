import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils
import org.apache.spark.sql.types.StructType

import org.apache.spark.sql.jdbc.{JdbcDialect, JdbcDialects, JdbcType}


val df = spark.read.json("examples/src/main/resources/people.json")
val option:Option[StructType]= Some(df.schema)
val jdbcDF = spark.read.format("jdbc").option("url", "jdbc:postgresql:0.0.0.0:56000/postgres").option("dbtable", "people").option("user", "postgres").option("password", "Minerva001").load()
val dialect = JdbcDialects.get("jdbc:postgresql://0.0.0.0:56000/expert")
JdbcUtils.getInsertStatement("people", df.schema, option, true, dialect)
JdbcUtils.getDeleteStatement("people", df.schema, option, true, dialect)
JdbcUtils.getUpdateStatement("people", df.schema, option, true, dialect)
# ./bin/spark-shell --driver-class-path postgresql-42.2.9.jar --jars pql-42.2.9.jar 
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite

val m = Map("username"->"postgres", "password"->"Minerva001", "url"->"jdbc:postgresql://localhost:56000/expert", "dbtable"->"people")
val options = new JdbcOptionsInWrite(m)
import org.apache.spark.sql.{AnalysisException, DataFrame, Row, SaveMode}
JdbcUtils.saveTable(df, option, true, options, SaveMode.Append)


