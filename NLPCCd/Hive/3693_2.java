//,temp,sample_4058.java,2,14,temp,sample_4057.java,2,13
//,3
public class xxx {
public static ClassLoader getSessionSpecifiedClassLoader() {
SessionState state = SessionState.get();
if (state == null || state.getConf() == null) {
return JavaUtils.getClassLoader();
}
ClassLoader sessionCL = state.getConf().getClassLoader();
if (sessionCL != null) {


log.info("use session specified class loader");
}
}

};