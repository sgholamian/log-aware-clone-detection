//,temp,sample_1806.java,2,16,temp,sample_1885.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL LOAD " + dbName + "_dupe FROM '" + replDumpLocn + "'", driverMirror);
verifyRun("SELECT * from " + dbName + "_dupe.unptned", unptn_data, driverMirror);
verifyFail("SELECT a from " + dbName + "_dupe.ptned WHERE b=1", driverMirror);
verifyIfTableNotExist(dbName + "_dupe", "ptned", metaStoreClient);
run("DROP TABLE " + dbName + ".ptned", driver);
verifyIfTableNotExist(dbName, "ptned", metaStoreClient);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String postDropReplDumpLocn = getResult(0,0, driver);
String postDropReplDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};