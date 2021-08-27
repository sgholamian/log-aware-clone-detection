//,temp,HMSBenchmarks.java,215,235,temp,HMSBenchmarks.java,193,213
//,2
public class xxx {
  static DescriptiveStatistics benchmarkListManyPartitions(@NotNull MicroBenchmark bench,
                                                           @NotNull BenchData data,
                                                           int howMany) {
    final HMSClient client = data.getClient();
    String dbName = data.dbName;
    String tableName = data.tableName;

    BenchmarkUtils.createPartitionedTable(client, dbName, tableName);
    try {
      addManyPartitions(client, dbName, tableName, null, Collections.singletonList("d"), howMany);
      LOG.debug("Created {} partitions", howMany);
      LOG.debug("started benchmark... ");
      return bench.measure(() ->
          throwingSupplierWrapper(() -> client.listPartitions(dbName, tableName)));
    } catch (TException e) {
      e.printStackTrace();
      return new DescriptiveStatistics();
    } finally {
      throwingSupplierWrapper(() -> client.dropTable(dbName, tableName));
    }
  }

};