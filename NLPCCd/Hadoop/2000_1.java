//,temp,sample_2335.java,2,19,temp,sample_3487.java,2,15
//,3
public class xxx {
public void dummy_method(){
if (!disallowFallbackToRandomSecretProvider && "file".equals(name) && config.getProperty(SIGNATURE_SECRET_FILE) == null) {
name = "random";
}
SignerSecretProvider provider;
if ("file".equals(name)) {
provider = new FileSignerSecretProvider();
try {
provider.init(config, ctx, validity);
} catch (Exception e) {
if (!disallowFallbackToRandomSecretProvider) {


log.info("unable to initialize filesignersecretprovider falling back to use random secrets");
}
}
}
}

};