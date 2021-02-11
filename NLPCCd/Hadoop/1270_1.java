//,temp,sample_370.java,2,17,temp,sample_4301.java,2,13
//,3
public class xxx {
public void dummy_method(){
if (serverProviderUri == null) {
return null;
}
try {
return cache.get(serverProviderUri, new Callable<KeyProvider>() {
public KeyProvider call() throws Exception {
return KMSUtil.createKeyProviderFromUri(conf, serverProviderUri);
}
});
} catch (Exception e) {


log.info("could not create keyprovider for dfsclient");
}
}

};