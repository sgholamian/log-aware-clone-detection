//,temp,ApplicationTableRW.java,84,126,temp,SubApplicationTableRW.java,84,126
//,2
public class xxx {
  public void createTable(Admin admin, Configuration hbaseConf)
      throws IOException {

    TableName table = getTableName(hbaseConf);
    if (admin.tableExists(table)) {
      // do not disable / delete existing table
      // similar to the approach taken by map-reduce jobs when
      // output directory exists
      throw new IOException("Table " + table.getNameAsString()
          + " already exists.");
    }

    HTableDescriptor subAppTableDescp = new HTableDescriptor(table);
    HColumnDescriptor infoCF =
        new HColumnDescriptor(SubApplicationColumnFamily.INFO.getBytes());
    infoCF.setBloomFilterType(BloomType.ROWCOL);
    subAppTableDescp.addFamily(infoCF);

    HColumnDescriptor configCF =
        new HColumnDescriptor(SubApplicationColumnFamily.CONFIGS.getBytes());
    configCF.setBloomFilterType(BloomType.ROWCOL);
    configCF.setBlockCacheEnabled(true);
    subAppTableDescp.addFamily(configCF);

    HColumnDescriptor metricsCF =
        new HColumnDescriptor(SubApplicationColumnFamily.METRICS.getBytes());
    subAppTableDescp.addFamily(metricsCF);
    metricsCF.setBlockCacheEnabled(true);
    // always keep 1 version (the latest)
    metricsCF.setMinVersions(1);
    metricsCF.setMaxVersions(
        hbaseConf.getInt(METRICS_MAX_VERSIONS, DEFAULT_METRICS_MAX_VERSIONS));
    metricsCF.setTimeToLive(hbaseConf.getInt(METRICS_TTL_CONF_NAME,
        DEFAULT_METRICS_TTL));
    subAppTableDescp.setRegionSplitPolicyClassName(
        "org.apache.hadoop.hbase.regionserver.KeyPrefixRegionSplitPolicy");
    subAppTableDescp.setValue("KeyPrefixRegionSplitPolicy.prefix_length",
        TimelineHBaseSchemaConstants.USERNAME_SPLIT_KEY_PREFIX_LENGTH);
    admin.createTable(subAppTableDescp,
        TimelineHBaseSchemaConstants.getUsernameSplits());
    LOG.info("Status of table creation for " + table.getNameAsString() + "="
        + admin.tableExists(table));
  }

};