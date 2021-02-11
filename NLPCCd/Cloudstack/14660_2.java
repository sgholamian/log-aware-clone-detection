//,temp,sample_6823.java,2,17,temp,sample_8337.java,2,17
//,2
public class xxx {
public void dummy_method(){
long mshostId = ManagementServerNode.getManagementServerId();
for (int i = 0; i < 10; i++) {
String mntPt = parent + File.separator + String.valueOf(mshostId) + "." + Integer.toHexString(_rand.nextInt(Integer.MAX_VALUE));
File file = new File(mntPt);
if (!file.exists()) {
if (_storage.mkdir(mntPt)) {
mountPoint = mntPt;
break;
}
}


log.info("unable to create mount");
}
}

};