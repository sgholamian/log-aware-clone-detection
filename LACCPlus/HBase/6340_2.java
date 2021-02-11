//,temp,TestImportTSVWithVisibilityLabels.java,433,475,temp,TestImportTSVWithOperationAttributes.java,205,254
//,3
public class xxx {
  private static void validateTable(Configuration conf, TableName tableName, String family,
      int valueMultiplier, boolean dataAvailable) throws IOException {

    LOG.debug("Validating table.");
    Connection connection = ConnectionFactory.createConnection(conf);
    Table table = connection.getTable(tableName);
    boolean verified = false;
    long pause = conf.getLong("hbase.client.pause", 5 * 1000);
    int numRetries = conf.getInt(HConstants.HBASE_CLIENT_RETRIES_NUMBER, 5);
    for (int i = 0; i < numRetries; i++) {
      try {
        Scan scan = new Scan();
        // Scan entire family.
        scan.addFamily(Bytes.toBytes(family));
        if (dataAvailable) {
          ResultScanner resScanner = table.getScanner(scan);
          for (Result res : resScanner) {
            LOG.debug("Getting results " + res.size());
            assertTrue(res.size() == 2);
            List<Cell> kvs = res.listCells();
            assertTrue(CellUtil.matchingRows(kvs.get(0), Bytes.toBytes("KEY")));
            assertTrue(CellUtil.matchingRows(kvs.get(1), Bytes.toBytes("KEY")));
            assertTrue(CellUtil.matchingValue(kvs.get(0), Bytes.toBytes("VALUE" + valueMultiplier)));
            assertTrue(CellUtil.matchingValue(kvs.get(1),
                Bytes.toBytes("VALUE" + 2 * valueMultiplier)));
            // Only one result set is expected, so let it loop.
            verified = true;
          }
        } else {
          ResultScanner resScanner = table.getScanner(scan);
          Result[] next = resScanner.next(2);
          assertEquals(0, next.length);
          verified = true;
        }

        break;
      } catch (NullPointerException e) {
        // If here, a cell was empty. Presume its because updates came in
        // after the scanner had been opened. Wait a while and retry.
      }
      try {
        Thread.sleep(pause);
      } catch (InterruptedException e) {
        // continue
      }
    }
    table.close();
    connection.close();
    assertTrue(verified);
  }

};