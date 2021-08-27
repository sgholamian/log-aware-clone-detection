//,temp,sample_1901.java,2,16,temp,sample_1870.java,2,13
//,3
public class xxx {
public void dummy_method(){
try {
List<SQLPrimaryKey> pks = metaStoreClient.getPrimaryKeys(new PrimaryKeysRequest(dbName, "acid_table_incremental"));
assertEquals(pks.size(), 1);
} catch (TException te) {
assertNull(te);
}
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + incrementalDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id");
}

};