//,temp,HiveMaterializedViewsRegistry.java,274,299,temp,HiveMaterializedViewsRegistry.java,244,269
//,3
public class xxx {
  public void refreshMaterializedView(HiveConf conf, Table oldMaterializedViewTable, Table materializedViewTable) {
    final boolean cache = !conf.get(HiveConf.ConfVars.HIVE_SERVER2_MATERIALIZED_VIEWS_REGISTRY_IMPL.varname)
        .equals("DUMMY");
    if (!cache) {
      // Nothing to do, bail out
      return;
    }

    // Bail out if it is not enabled for rewriting
    if (!materializedViewTable.isRewriteEnabled()) {
      dropMaterializedView(oldMaterializedViewTable);
      LOG.debug("Materialized view " + materializedViewTable.getCompleteName() +
          " dropped; it is not rewrite enabled");
      return;
    }

    final HiveRelOptMaterialization newMaterialization = createMaterialization(conf, materializedViewTable);
    if (newMaterialization == null) {
      return;
    }
    materializedViewsCache.refresh(oldMaterializedViewTable, materializedViewTable, newMaterialization);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Materialized view refreshed: " + materializedViewTable.getFullyQualifiedName());
    }
  }

};