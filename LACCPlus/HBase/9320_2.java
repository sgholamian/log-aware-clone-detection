//,temp,TestMultiParallel.java,418,446,temp,TestMultiParallel.java,387,416
//,3
public class xxx {
  @Test
  public void testBatchWithDelete() throws Exception {
    LOG.info("test=testBatchWithDelete");
    Table table = UTIL.getConnection().getTable(TEST_TABLE);

    // Load some data
    List<Put> puts = constructPutRequests();
    Object[] results = new Object[puts.size()];
    table.batch(puts, results);
    validateSizeAndEmpty(results, KEYS.length);

    // Deletes
    List<Row> deletes = new ArrayList<>();
    for (int i = 0; i < KEYS.length; i++) {
      Delete delete = new Delete(KEYS[i]);
      delete.addFamily(BYTES_FAMILY);
      deletes.add(delete);
    }
    results= new Object[deletes.size()];
    table.batch(deletes, results);
    validateSizeAndEmpty(results, KEYS.length);

    // Get to make sure ...
    for (byte[] k : KEYS) {
      Get get = new Get(k);
      get.addColumn(BYTES_FAMILY, QUALIFIER);
      Assert.assertFalse(table.exists(get));
    }
    table.close();
  }

};