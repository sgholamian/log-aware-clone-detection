//,temp,HiveMetaStoreAuthorizer.java,178,196,temp,HiveMetaStoreAuthorizer.java,115,133
//,3
public class xxx {
  @Override
  public final List<String> filterDatabases(List<String> list) throws MetaException {
    LOG.debug("HiveMetaStoreAuthorizer.filterDatabases()");

    if (list == null) {
      return Collections.emptyList();
    }

    DatabaseFilterContext databaseFilterContext = new DatabaseFilterContext(list);
    HiveMetaStoreAuthzInfo hiveMetaStoreAuthzInfo = databaseFilterContext.getAuthzContext();
    List<String> filteredDatabases = filterDatabaseObjects(hiveMetaStoreAuthzInfo);
    if (CollectionUtils.isEmpty(filteredDatabases)) {
      filteredDatabases = Collections.emptyList();
    }

    LOG.debug("HiveMetaStoreAuthorizer.filterDatabases() :" + filteredDatabases);

    return filteredDatabases;
  }

};