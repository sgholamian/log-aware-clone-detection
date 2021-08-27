//,temp,sample_1826.java,2,16,temp,sample_1861.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[0] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[1] + "')", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String lastDumpIdWithoutRename = getResult(0, 1, driver);
run("ALTER TABLE " + dbName + ".ptned PARTITION (b=2) RENAME TO PARTITION (b=10)", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + lastDumpIdWithoutRename, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};