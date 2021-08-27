//,temp,sample_1945.java,2,10,temp,sample_1944.java,2,8
//,3
public class xxx {
protected void doStart() throws Exception {
if (Security.getProvider(BC) == null && BC.equals(getProvider())) {


log.info("adding bouncycastleprovider as security provider");
}
}

};