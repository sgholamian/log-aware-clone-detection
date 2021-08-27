//,temp,HiveMaterializedViewsRegistry.java,274,299,temp,HiveMaterializedViewsRegistry.java,244,269
//,3
public class xxx {
  public void createMaterializedView(HiveConf conf, Table materializedViewTable) {
    final boolean cache = !conf.get(HiveConf.ConfVars.HIVE_SERVER2_MATERIALIZED_VIEWS_REGISTRY_IMPL.varname)
        .equals("DUMMY");
    if (!cache) {
      // Nothing to do, bail out
      return;
    }

    // Bail out if it is not enabled for rewriting
    if (!materializedViewTable.isRewriteEnabled()) {
      LOG.debug("Materialized view " + materializedViewTable.getCompleteName() +
          " ignored; it is not rewrite enabled");
      return;
    }

    HiveRelOptMaterialization materialization = createMaterialization(conf, materializedViewTable);
    if (materialization == null) {
      return;
    }

    materializedViewsCache.putIfAbsent(materializedViewTable, materialization);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Created materialized view for rewriting: " + materializedViewTable.getFullyQualifiedName());
    }
  }

};