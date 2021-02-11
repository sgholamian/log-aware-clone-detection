//,temp,sample_6820.java,2,17,temp,sample_5223.java,2,17
//,3
public class xxx {
public void dummy_method(){
final URL url = this.getClass().getClassLoader().getResource("scripts/vm/systemvm/id_rsa.cloud");
File keyFile = null;
if (url != null) {
keyFile = new File(url.getPath());
}
if (keyFile == null || !keyFile.exists()) {
keyFile = new File("/usr/share/cloudstack-common/scripts/vm/systemvm/id_rsa.cloud");
}
assert keyFile != null;
if (!keyFile.exists()) {


log.info("unable to locate id rsa cloud in your setup at");
}
}

};