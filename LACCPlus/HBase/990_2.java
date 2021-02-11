//,temp,TestSpaceQuotas.java,612,648,temp,TestSpaceQuotas.java,564,610
//,3
public class xxx {
  private void verifyViolation(
			SpaceViolationPolicy policyToViolate, TableName tn, Mutation m) throws Exception {
    // But let's try a few times to get the exception before failing
    boolean sawError = false;
    for (int i = 0; i < NUM_RETRIES && !sawError; i++) {
      try (Table table = TEST_UTIL.getConnection().getTable(tn)) {
        if (m instanceof Put) {
          table.put((Put) m);
        } else if (m instanceof Delete) {
          table.delete((Delete) m);
        } else if (m instanceof Append) {
          table.append((Append) m);
        } else if (m instanceof Increment) {
          table.increment((Increment) m);
        } else {
          fail(
              "Failed to apply " + m.getClass().getSimpleName() +
              " to the table. Programming error");
        }
        LOG.info("Did not reject the " + m.getClass().getSimpleName() + ", will sleep and retry");
        Thread.sleep(2000);
      } catch (Exception e) {
        String msg = StringUtils.stringifyException(e);
        if (policyToViolate.equals(SpaceViolationPolicy.DISABLE)) {
          assertTrue(e instanceof TableNotEnabledException);
        } else {
          assertTrue("Expected exception message to contain the word '" + policyToViolate.name()
              + "', but was " + msg,
            msg.contains(policyToViolate.name()));
        }
        sawError = true;
      }
    }
    if (!sawError) {
      try (Table quotaTable = TEST_UTIL.getConnection().getTable(QuotaUtil.QUOTA_TABLE_NAME)) {
        ResultScanner scanner = quotaTable.getScanner(new Scan());
        Result result = null;
        LOG.info("Dumping contents of hbase:quota table");
        while ((result = scanner.next()) != null) {
          LOG.info(Bytes.toString(result.getRow()) + " => " + result.toString());
        }
        scanner.close();
      }
    }
    assertTrue(
        "Expected to see an exception writing data to a table exceeding its quota", sawError);
  }

};