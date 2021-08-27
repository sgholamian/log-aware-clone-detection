//,temp,sample_743.java,2,15,temp,sample_1987.java,2,13
//,3
public class xxx {
public static TxnStore getTxnStore(Configuration conf) {
String className = MetastoreConf.getVar(conf, ConfVars.TXN_STORE_IMPL);
try {
TxnStore handler = JavaUtils.getClass(className, TxnStore.class).newInstance();
handler.setConf(conf);
return handler;
} catch (Exception e) {


log.info("unable to instantiate raw store directly in fastpath mode");
}
}

};