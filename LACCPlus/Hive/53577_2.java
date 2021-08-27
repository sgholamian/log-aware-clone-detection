//,temp,AbstractHCatStorerTest.java,344,401,temp,AbstractHCatStorerTest.java,262,336
//,3
public class xxx {
  void pigValueRangeTest(String tblName, String hiveType, String pigType,
      HCatBaseStorer.OOR_VALUE_OPT_VALUES goal, String inputValue, String expectedValue,
      String format) throws Exception {
    AbstractHCatLoaderTest.dropTable(tblName, driver);
    final String field = "f1";
    AbstractHCatLoaderTest.createTableDefaultDB(tblName, field + " " + hiveType, null, driver,
            storageFormat);
    HcatTestUtils.createTestDataFile(INPUT_FILE_NAME, new String[] { inputValue });
    LOG.debug("File=" + INPUT_FILE_NAME);
    dumpFile(INPUT_FILE_NAME);
    PigServer server = createPigServer(true);
    int queryNumber = 1;
    logAndRegister(server,
        "A = load '" + INPUT_FILE_NAME + "' as (" + field + ":" + pigType + ");", queryNumber++);
    if (goal == null) {
      logAndRegister(server, "store A into '" + tblName + "' using " + HCatStorer.class.getName()
          + "();", queryNumber++);
    } else {
      FrontendException fe = null;
      try {
        logAndRegister(server, "store A into '" + tblName + "' using " + HCatStorer.class.getName()
            + "('','','-" + HCatStorer.ON_OOR_VALUE_OPT + " " + goal + "');", queryNumber++);
      } catch (FrontendException e) {
        fe = e;
      }
      switch (goal) {
      case Null:
        // do nothing, fall through and verify the data
        break;
      case Throw:
        assertTrue("Expected a FrontendException", fe != null);
        assertEquals("Expected a different FrontendException.", fe.getMessage(),
            "Unable to store alias A");
        return;// this test is done
      default:
        assertFalse("Unexpected goal: " + goal, 1 == 1);
      }
    }
    logAndRegister(server,
        "B = load '" + tblName + "' using " + HCatLoader.class.getName() + "();", queryNumber);
    try {
      driver.run("select * from " + tblName);
    } catch (CommandProcessorException e) {
      LOG.debug("cpr.respCode=" + e.getResponseCode() + " cpr.errMsg=" + e.getMessage() + " for table " + tblName);
    }
    List l = new ArrayList();
    driver.getResults(l);
    LOG.debug("Dumping rows via SQL from " + tblName);
    for (Object t : l) {
      LOG.debug(t == null ? null : t.toString() + " t.class=" + t.getClass());
    }
    Iterator<Tuple> itr = server.openIterator("B");
    int numRowsRead = 0;
    while (itr.hasNext()) {
      Tuple t = itr.next();
      if ("date".equals(hiveType)) {
        DateTime dateTime = (DateTime) t.get(0);
        assertTrue(format != null);
        assertEquals("Comparing Pig to Raw data for table " + tblName, expectedValue,
            dateTime == null ? null : dateTime.toString(format));
      } else {
        assertEquals("Comparing Pig to Raw data for table " + tblName, expectedValue,
            t.isNull(0) ? null : t.get(0).toString());
      }
      // see comment at "Dumping rows via SQL..." for why this doesn't work
      // assertEquals("Comparing Pig to Hive", t.get(0), l.get(0));
      numRowsRead++;
    }
    assertEquals("Expected " + 1 + " rows; got " + numRowsRead + " file=" + INPUT_FILE_NAME
        + "; table " + tblName, 1, numRowsRead);
    /*
     * Misc notes: Unfortunately Timestamp.toString() adjusts the value for local TZ and 't' is a
     * String thus the timestamp in 't' doesn't match rawData
     */
  }

};