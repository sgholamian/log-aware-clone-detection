//,temp,sample_4392.java,2,17,temp,sample_1351.java,2,17
//,3
public class xxx {
public void dummy_method(){
parseContext = pctx;
hiveConf = parseContext.getConf();
try {
hiveDb = Hive.get(hiveConf);
} catch (HiveException e) {
LOG.error(org.apache.hadoop.util.StringUtils.stringifyException(e));
throw new SemanticException(e.getMessage(), e);
}
HiveConf.setBoolVar(hiveConf, HiveConf.ConfVars.HIVEOPTINDEXFILTER, false);
if (shouldApplyOptimization()) {


log.info("rewriting original query using optimization");
}
}

};