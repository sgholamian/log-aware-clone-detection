//,temp,AbstractHCatStorerTest.java,344,401,temp,AbstractHCatStorerTest.java,262,336
//,3
public class xxx {
  @Test
  public void testDateCharTypes() throws Exception {
    final String tblName = "junit_date_char";
    AbstractHCatLoaderTest.dropTable(tblName, driver);
    AbstractHCatLoaderTest.createTableDefaultDB(tblName,
        "id int, char5 char(5), varchar10 varchar(10), dec52 decimal(5,2)", null, driver,
        storageFormat);
    int NUM_ROWS = 5;
    String[] rows = new String[NUM_ROWS];
    for (int i = 0; i < NUM_ROWS; i++) {
      // since the file is read by Pig, we need to make sure the values are in format that Pig
      // understands
      // otherwise it will turn the value to NULL on read
      rows[i] = i + "\txxxxx\tyyy\t" + 5.2;
    }
    HcatTestUtils.createTestDataFile(INPUT_FILE_NAME, rows);
    LOG.debug("File=" + INPUT_FILE_NAME);
    // dumpFile(INPUT_FILE_NAME);
    PigServer server = createPigServer(true);
    int queryNumber = 1;
    logAndRegister(server, "A = load '" + INPUT_FILE_NAME
        + "' as (id:int, char5:chararray, varchar10:chararray, dec52:bigdecimal);", queryNumber++);
    logAndRegister(server, "store A into '" + tblName + "' using " + HCatStorer.class.getName()
        + "();", queryNumber++);
    logAndRegister(server,
        "B = load '" + tblName + "' using " + HCatLoader.class.getName() + "();", queryNumber);
    try {
      driver.run("select * from " + tblName);
    } catch (CommandProcessorException e) {
      LOG.debug("cpr.respCode=" + e.getResponseCode() + " cpr.errMsg=" + e.getMessage());
    }
    List l = new ArrayList();
    driver.getResults(l);
    LOG.debug("Dumping rows via SQL from " + tblName);
    /*
     * Unfortunately Timestamp.toString() adjusts the value for local TZ and 't' is a String thus
     * the timestamp in 't' doesn't match rawData
     */
    for (Object t : l) {
      LOG.debug(t == null ? null : t.toString());
    }
    Iterator<Tuple> itr = server.openIterator("B");
    int numRowsRead = 0;
    while (itr.hasNext()) {
      Tuple t = itr.next();
      StringBuilder rowFromPig = new StringBuilder();
      for (int i = 0; i < t.size(); i++) {
        rowFromPig.append(t.get(i)).append("\t");
      }
      rowFromPig.setLength(rowFromPig.length() - 1);
      assertEquals("Comparing Pig to Raw data", rows[numRowsRead], rowFromPig.toString());
      // see comment at "Dumping rows via SQL..." for why this doesn't work (for all types)
      // assertEquals("Comparing Pig to Hive", rowFromPig.toString(), l.get(numRowsRead));
      numRowsRead++;
    }
    assertEquals("Expected " + NUM_ROWS + " rows; got " + numRowsRead + " file=" + INPUT_FILE_NAME,
        NUM_ROWS, numRowsRead);
  }

};