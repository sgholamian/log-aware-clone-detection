//,temp,sample_1824.java,2,16,temp,sample_1889.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("ALTER TABLE " + dbName + ".ptned DROP PARTITION (b='2')", driver);
run("DROP TABLE " + dbName + ".ptned2", driver);
run("SELECT a from " + dbName + ".ptned WHERE b=2", driver);
verifyResults(empty, driver);
run("SELECT a from " + dbName + ".ptned", driver);
verifyResults(ptn_data_1, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String postDropReplDumpLocn = getResult(0,0,driver);
String postDropReplDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};