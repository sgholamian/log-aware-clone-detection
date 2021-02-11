//,temp,SQLCLI.java,529,552,temp,SQLCLI.java,344,367
//,3
public class xxx {
  private void convertOMDB(Path dbPath, Path outPath) throws Exception {
    LOG.info("Create tables for sql om db.");
    File dbFile = dbPath.toFile();
    try (MetadataStore dbStore = MetadataStoreBuilder.newBuilder()
        .setConf(conf).setDbFile(dbFile).build();
         Connection conn = connectDB(outPath.toString())) {
      executeSQL(conn, CREATE_VOLUME_LIST);
      executeSQL(conn, CREATE_VOLUME_INFO);
      executeSQL(conn, CREATE_ACL_INFO);
      executeSQL(conn, CREATE_BUCKET_INFO);
      executeSQL(conn, CREATE_KEY_INFO);

      dbStore.iterate(null, (key, value) -> {
        String keyString = DFSUtilClient.bytes2String(key);
        KeyType type = getKeyType(keyString);
        try {
          insertOMDB(conn, type, keyString, value);
        } catch (IOException | SQLException ex) {
          LOG.error("Exception inserting key {} type {}", keyString, type, ex);
        }
        return true;
      });
    }
  }

};