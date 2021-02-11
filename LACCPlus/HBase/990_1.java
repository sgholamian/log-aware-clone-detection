//,temp,TestSpaceQuotas.java,612,648,temp,TestSpaceQuotas.java,564,610
//,3
public class xxx {
  private void verifyNoViolation(SpaceViolationPolicy policyToViolate, TableName tn, Mutation m)
      throws Exception {
    // But let's try a few times to write data before failing
    boolean sawSuccess = false;
    for (int i = 0; i < NUM_RETRIES && !sawSuccess; i++) {
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
            "Failed to apply " + m.getClass().getSimpleName() + " to the table. Programming error");
        }
        sawSuccess = true;
      } catch (Exception e) {
        LOG.info("Rejected the " + m.getClass().getSimpleName() + ", will sleep and retry");
        Thread.sleep(2000);
      }
    }
    if (!sawSuccess) {
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
    assertTrue("Expected to succeed in writing data to a table not having quota ", sawSuccess);
  }

};