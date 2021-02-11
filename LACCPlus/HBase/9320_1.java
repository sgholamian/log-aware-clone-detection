//,temp,TestMultiParallel.java,418,446,temp,TestMultiParallel.java,387,416
//,3
public class xxx {
  @Test
  public void testHTableDeleteWithList() throws Exception {
    LOG.info("test=testHTableDeleteWithList");
    Table table = UTIL.getConnection().getTable(TEST_TABLE);

    // Load some data
    List<Put> puts = constructPutRequests();
    Object[] results = new Object[puts.size()];
    table.batch(puts, results);
    validateSizeAndEmpty(results, KEYS.length);

    // Deletes
    ArrayList<Delete> deletes = new ArrayList<>();
    for (int i = 0; i < KEYS.length; i++) {
      Delete delete = new Delete(KEYS[i]);
      delete.addFamily(BYTES_FAMILY);
      deletes.add(delete);
    }
    table.delete(deletes);
    Assert.assertTrue(deletes.isEmpty());

    // Get to make sure ...
    for (byte[] k : KEYS) {
      Get get = new Get(k);
      get.addColumn(BYTES_FAMILY, QUALIFIER);
      Assert.assertFalse(table.exists(get));
    }
    table.close();
  }

};