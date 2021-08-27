//,temp,sample_1896.java,2,16,temp,sample_1840.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifySetup("SELECT * from " + dbName + ".unptned", unptn_data, driver);
verifySetup("SELECT a from " + dbName + ".ptned WHERE b=1", ptn_data_1, driver);
verifySetup("SELECT count(*) from " + dbName + ".unptned", new String[]{"2"}, driver);
verifySetup("SELECT count(*) from " + dbName + ".ptned", new String[]{"3"}, driver);
verifySetup("SELECT max(a) from " + dbName + ".unptned", new String[]{"2"}, driver);
verifySetup("SELECT max(a) from " + dbName + ".ptned where b=1", new String[]{"8"}, driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0,0,driver);
String replDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};