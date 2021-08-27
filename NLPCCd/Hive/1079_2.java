//,temp,sample_1831.java,2,16,temp,sample_1808.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("ALTER TABLE " + dbName + ".ptned DROP PARTITION (b=1)", driver);
run("ALTER TABLE " + dbName + ".ptned DROP PARTITION (b=2)", driver);
verifyIfPartitionNotExist(dbName, "ptned", new ArrayList<>(Arrays.asList("1")), metaStoreClient);
verifyIfPartitionNotExist(dbName, "ptned", new ArrayList<>(Arrays.asList("2")), metaStoreClient);
verifySetup("SELECT a from " + dbName + ".ptned WHERE b=1", empty, driver);
verifySetup("SELECT a from " + dbName + ".ptned WHERE b=2", empty, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String postDropReplDumpLocn = getResult(0,0,driver);
String postDropReplDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};