//,temp,sample_6819.java,2,17,temp,sample_8338.java,2,17
//,2
public class xxx {
public void dummy_method(){
URL url = this.getClass().getClassLoader().getResource("vms/systemvm.iso");
File isoFile = null;
if (url != null) {
isoFile = new File(url.getPath());
}
if (isoFile == null || !isoFile.exists()) {
isoFile = new File("/usr/share/cloudstack-common/vms/systemvm.iso");
}
assert (isoFile != null);
if (!isoFile.exists()) {


log.info("unable to locate systemvm iso in your setup at");
}
}

};