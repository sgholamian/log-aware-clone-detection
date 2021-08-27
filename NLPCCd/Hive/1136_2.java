//,temp,sample_3805.java,2,9,temp,sample_3804.java,2,8
//,3
public class xxx {
public Class<? extends InputFormat> getInputFormatClass() {
if (HiveConf.getVar(jobConf, HiveConf.ConfVars.HIVE_HBASE_SNAPSHOT_NAME) != null) {


log.info("using tablesnapshotinputformat");
}
}

};