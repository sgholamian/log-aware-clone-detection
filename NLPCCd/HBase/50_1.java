//,temp,sample_134.java,2,13,temp,sample_3308.java,2,10
//,3
public class xxx {
protected  List<Object> getCredentialProviders(Configuration conf) {
Object providersObj = null;
try {
providersObj = getProvidersMethod.invoke(hadoopCredProviderFactory, conf);
} catch (IllegalArgumentException e) {
return null;
} catch (IllegalAccessException e) {


log.info("failed to invoke");
}
}

};