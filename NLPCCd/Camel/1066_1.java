//,temp,sample_8496.java,2,16,temp,sample_6349.java,2,12
//,3
public class xxx {
public void dummy_method(){
String tmfAlgorithm = this.parsePropertyValue(this.getAlgorithm());
if (tmfAlgorithm == null) {
tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
}
TrustManagerFactory tmf;
if (this.getProvider() == null) {
tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
} else {
tmf = TrustManagerFactory.getInstance(tmfAlgorithm, this.parsePropertyValue(this.getProvider()));
}


log.info("trustmanagerfactory is using provider and algorithm");
}

};