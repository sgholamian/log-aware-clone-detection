//,temp,sample_2744.java,2,18,temp,sample_2742.java,2,16
//,3
public class xxx {
public void dummy_method(){
Configuration conf = MetastoreConf.newMetastoreConf();
boolean makeAcid = MetastoreConf.getBoolVar(conf, MetastoreConf.ConfVars.CREATE_TABLES_AS_ACID) && MetastoreConf.getBoolVar(conf, MetastoreConf.ConfVars.HIVE_SUPPORT_CONCURRENCY) && "org.apache.hadoop.hive.ql.lockmgr.DbTxnManager".equals( MetastoreConf.getVar(conf, MetastoreConf.ConfVars.HIVE_TXN_MANAGER) );
if(makeAcid) {
if(!conformToAcid(newTable)) {
return;
}
if(!TableType.MANAGED_TABLE.toString().equalsIgnoreCase(newTable.getTableType())) {
return;
}
if(newTable.getSd().getSortColsSize() > 0) {


log.info("could not make acid it s sorted");
}
}
}

};