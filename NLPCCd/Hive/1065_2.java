//,temp,sample_1857.java,2,16,temp,sample_1875.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".ptned_src partition(b=2, c=3) values('" + ptn_data_2[0] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".ptned_src partition(b=2, c=3) values('" + ptn_data_2[1] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".ptned_src partition(b=2, c=3) values('" + ptn_data_2[2] + "')", driver);
verifySetup("SELECT a from " + dbName + ".ptned_src where (b=1 and c=1) ORDER BY a", ptn_data_1, driver);
verifySetup("SELECT a from " + dbName + ".ptned_src where (b=2 and c=2) ORDER BY a", ptn_data_2, driver);
verifySetup("SELECT a from " + dbName + ".ptned_src where (b=2 and c=3) ORDER BY a", ptn_data_2, driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};