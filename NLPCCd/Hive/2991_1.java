//,temp,sample_2378.java,2,17,temp,sample_5412.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (conf.isSparkDPPAny()) {
physicalCtx = new SparkDynamicPartitionPruningResolver().resolve(physicalCtx);
}
if (conf.getBoolVar(HiveConf.ConfVars.HIVENULLSCANOPTIMIZE)) {
physicalCtx = new NullScanOptimizer().resolve(physicalCtx);
} else {
}
if (conf.getBoolVar(HiveConf.ConfVars.HIVEMETADATAONLYQUERIES)) {
physicalCtx = new MetadataOnlyOptimizer().resolve(physicalCtx);
} else {


log.info("skipping metadata only query optimization");
}
}

};