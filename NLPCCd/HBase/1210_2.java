//,temp,sample_2308.java,2,10,temp,sample_325.java,2,11
//,3
public class xxx {
public void start(CoprocessorEnvironment env) throws IOException {
CompoundConfiguration conf = new CompoundConfiguration();
conf.add(env.getConfiguration());
authorizationEnabled = AccessChecker.isAuthorizationSupported(conf);
if (!authorizationEnabled) {


log.info("the accesscontroller has been loaded with authorization checks disabled");
}
}

};