//,temp,sample_2744.java,2,18,temp,sample_2742.java,2,16
//,3
public class xxx {
private void makeAcid(Table newTable) throws MetaException {
if(newTable.getParameters() != null && newTable.getParameters().containsKey(hive_metastoreConstants.TABLE_IS_TRANSACTIONAL)) {
LOG.info("Could not make " + Warehouse.getQualifiedName(newTable) + " acid: already has " + hive_metastoreConstants.TABLE_IS_TRANSACTIONAL + "=" + newTable.getParameters().get(hive_metastoreConstants.TABLE_IS_TRANSACTIONAL));
return;
}
Configuration conf = MetastoreConf.newMetastoreConf();
boolean makeAcid = MetastoreConf.getBoolVar(conf, MetastoreConf.ConfVars.CREATE_TABLES_AS_ACID) && MetastoreConf.getBoolVar(conf, MetastoreConf.ConfVars.HIVE_SUPPORT_CONCURRENCY) && "org.apache.hadoop.hive.ql.lockmgr.DbTxnManager".equals( MetastoreConf.getVar(conf, MetastoreConf.ConfVars.HIVE_TXN_MANAGER) );
if(makeAcid) {
if(!conformToAcid(newTable)) {


log.info("could not make acid wrong io format");
}
}
}

};