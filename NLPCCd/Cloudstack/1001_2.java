//,temp,sample_100.java,2,17,temp,sample_2152.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (port == null) {
System.exit(1);
}
if (url == null) {
System.exit(1);
}
if (secretkey == null) {
System.exit(1);
}
if (prop.get("apikey") == null) {


log.info("please set apikey in tool properties file");
}
}

};