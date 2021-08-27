//,temp,sample_1912.java,2,11,temp,sample_1910.java,2,11
//,3
public class xxx {
public String getAppID() {
Future<String> getAppID = sparkClient.run(new GetAppIDJob());
try {
return getAppID.get(sparkClientTimeoutInSeconds, TimeUnit.SECONDS);
} catch (Exception e) {


log.info("failed to get app id");
}
}

};