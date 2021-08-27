//,temp,sample_1836.java,2,16,temp,sample_1823.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("DROP TABLE " + dbName + ".ptned2", driver);
run("ALTER TABLE " + dbName + ".ptned3 DROP PARTITION (b=1)", driver);
verifySetup("SELECT a from " + dbName + ".ptned WHERE b='2'", empty, driver);
verifySetup("SELECT a from " + dbName + ".ptned", ptn_data_1, driver);
verifySetup("SELECT a from " + dbName + ".ptned3 WHERE b=1",empty, driver);
verifySetup("SELECT a from " + dbName + ".ptned3", ptn_data_2, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String postDropReplDumpLocn = getResult(0,0,driver);
String postDropReplDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};