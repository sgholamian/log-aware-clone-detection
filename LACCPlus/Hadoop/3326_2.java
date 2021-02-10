//,temp,InMemoryLevelDBAliasMapClient.java,191,198,temp,InMemoryLevelDBAliasMapClient.java,182,189
//,2
public class xxx {
  @Override
  public Reader<FileRegion> getReader(Reader.Options opts, String blockPoolID)
      throws IOException {
    InMemoryAliasMapProtocol aliasMap = getAliasMap(blockPoolID);
    LOG.info("Loading InMemoryAliasMapReader for block pool id {}",
        blockPoolID);
    return new LevelDbReader(aliasMap);
  }

};