//,temp,sample_8332.java,2,18,temp,sample_6821.java,2,18
//,2
public class xxx {
public void dummy_method(){
String mountPoint = null;
synchronized (_storageMounts) {
mountPoint = _storageMounts.get(storageUrl);
if (mountPoint != null) {
return mountPoint;
}
URI uri;
try {
uri = new URI(storageUrl);
} catch (URISyntaxException e) {


log.info("invalid storage url format");
}
}
}

};