//,temp,InMemoryLevelDBAliasMapClient.java,191,198,temp,InMemoryLevelDBAliasMapClient.java,182,189
//,2
public class xxx {
  @Override
  public Writer<FileRegion> getWriter(Writer.Options opts, String blockPoolID)
      throws IOException {
    InMemoryAliasMapProtocol aliasMap = getAliasMap(blockPoolID);
    LOG.info("Loading InMemoryAliasMapWriter for block pool id {}",
        blockPoolID);
    return new LevelDbWriter(aliasMap);
  }

};