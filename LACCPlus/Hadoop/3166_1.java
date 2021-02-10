//,temp,SQLCLI.java,466,490,temp,SQLCLI.java,344,367
//,3
public class xxx {
  private void convertContainerDB(Path dbPath, Path outPath)
      throws Exception {
    LOG.info("Create tables for sql container db.");
    File dbFile = dbPath.toFile();
    try (MetadataStore dbStore = MetadataStoreBuilder.newBuilder()
        .setConf(conf).setDbFile(dbFile).build();
        Connection conn = connectDB(outPath.toString())) {
      executeSQL(conn, CREATE_CONTAINER_INFO);

      dbStore.iterate(null, (key, value) -> {
        long containerID = Longs.fromByteArray(key);
        ContainerInfo containerInfo = null;
        containerInfo = ContainerInfo.fromProtobuf(
            HddsProtos.SCMContainerInfo.PARSER.parseFrom(value));
        Preconditions.checkNotNull(containerInfo);
        try {
          //TODO: include container state to sqllite schema
          insertContainerDB(conn, containerInfo, containerID);
          return true;
        } catch (SQLException e) {
          throw new IOException(e);
        }
      });
    }
  }

};