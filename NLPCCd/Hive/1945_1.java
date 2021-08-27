//,temp,sample_1876.java,2,16,temp,sample_1882.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifySetup("SELECT a from " + dbName + ".ptned_src where (b=1 and c=1)", empty, driver);
verifySetup("SELECT a from " + dbName + ".ptned_src where (b=2 and c=2) ORDER BY a", ptn_data_2, driver);
verifySetup("SELECT a from " + dbName + ".ptned_src where (b=2 and c=3) ORDER BY a", ptn_data_2, driver);
verifySetup("SELECT a from " + dbName + ".ptned_dest where (b=1 and c=1) ORDER BY a", ptn_data_1, driver);
verifySetup("SELECT a from " + dbName + ".ptned_dest where (b=2 and c=2)", empty, driver);
verifySetup("SELECT a from " + dbName + ".ptned_dest where (b=2 and c=3)", empty, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};