//,temp,HiveMetaStoreAuthorizer.java,178,196,temp,HiveMetaStoreAuthorizer.java,115,133
//,3
public class xxx {
  @Override
  public final List<Table> filterTables(List<Table> list) throws MetaException {
    LOG.debug("==> HiveMetaStoreAuthorizer.filterTables()");

    List<Table> filteredTables = null;

    if (list != null) {
      TableFilterContext tableFilterContext = new TableFilterContext(list);
      HiveMetaStoreAuthzInfo hiveMetaStoreAuthzInfo = tableFilterContext.getAuthzContext();
      filteredTables = filterTableObjects(hiveMetaStoreAuthzInfo, list);
      if (CollectionUtils.isEmpty(filteredTables)) {
        filteredTables = Collections.emptyList();
      }
    }

    LOG.debug("<== HiveMetaStoreAuthorizer.filterTables(): " + filteredTables);

    return filteredTables;
  }

};