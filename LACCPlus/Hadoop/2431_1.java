//,temp,SQLCLI.java,529,552,temp,SQLCLI.java,344,367
//,3
public class xxx {
  private void convertOpenContainerDB(Path dbPath, Path outPath)
      throws Exception {
    LOG.info("Create table for open container db.");
    File dbFile = dbPath.toFile();
    try (MetadataStore dbStore = MetadataStoreBuilder.newBuilder()
        .setConf(conf).setDbFile(dbFile).build();
        Connection conn = connectDB(outPath.toString())) {
      executeSQL(conn, CREATE_OPEN_CONTAINER);

      dbStore.iterate(null, (key, value) -> {
        String containerName = DFSUtil.bytes2String(key);
        Long containerUsed =
            Long.parseLong(DFSUtil.bytes2String(value));
        String insertOpenContainer = String
            .format(INSERT_OPEN_CONTAINER, containerName, containerUsed);
        try {
          executeSQL(conn, insertOpenContainer);
          return true;
        } catch (SQLException e) {
          throw new IOException(e);
        }
      });
    }
  }

};