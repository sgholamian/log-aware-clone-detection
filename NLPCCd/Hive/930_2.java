//,temp,sample_967.java,2,14,temp,sample_968.java,2,15
//,3
public class xxx {
private int showTablesOrViews(Hive db, ShowTablesDesc showDesc) throws HiveException {
List<String> tablesOrViews = null;
String dbName      = showDesc.getDbName();
String pattern     = showDesc.getPattern();
String resultsFile = showDesc.getResFile();
TableType type     = showDesc.getType();
if (!db.databaseExists(dbName)) {
throw new HiveException(ErrorMsg.DATABASE_NOT_EXISTS, dbName);
}
tablesOrViews = db.getTablesByType(dbName, pattern, type);


log.info("results");
}

};