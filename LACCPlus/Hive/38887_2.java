//,temp,TestReplicationScenarios.java,2902,2935,temp,TestReplicationScenarios.java,2867,2900
//,2
public class xxx {
  @Test
  public void testRenameTableAcrossDatabases() throws IOException {
    String testName = "renameTableAcrossDatabases";
    LOG.info("Testing " + testName);
    String dbName1 = testName + "_" + tid + "_1";
    String dbName2 = testName + "_" + tid + "_2";
    String replDbName1 = dbName1 + "_dupe";
    String replDbName2 = dbName2 + "_dupe";

    createDB(dbName1, driver);
    createDB(dbName2, driver);
    run("CREATE TABLE " + dbName1 + ".unptned(a string) STORED AS TEXTFILE", driver);

    String[] unptn_data = new String[] { "ten", "twenty" };
    String unptn_locn = new Path(TEST_PATH, testName + "_unptn").toUri().getPath();

    createTestDataFile(unptn_locn, unptn_data);
    run("LOAD DATA LOCAL INPATH '" + unptn_locn + "' OVERWRITE INTO TABLE " + dbName1 + ".unptned", driver);

    Tuple bootstrap1 = bootstrapLoadAndVerify(dbName1, replDbName1);
    Tuple bootstrap2 = bootstrapLoadAndVerify(dbName2, replDbName2);

    verifyRun("SELECT a from " + replDbName1 + ".unptned ORDER BY a", unptn_data, driverMirror);
    verifyIfTableNotExist(replDbName2, "unptned", metaStoreClientMirror);

    verifyFail("ALTER TABLE " + dbName1 + ".unptned RENAME TO " + dbName2 + ".unptned_renamed", driver);

    incrementalLoadAndVerify(dbName1, replDbName1);
    incrementalLoadAndVerify(dbName2, replDbName2);

    verifyIfTableNotExist(replDbName1, "unptned_renamed", metaStoreClientMirror);
    verifyIfTableNotExist(replDbName2, "unptned_renamed", metaStoreClientMirror);
    verifyRun("SELECT a from " + replDbName1 + ".unptned ORDER BY a", unptn_data, driverMirror);
  }

};