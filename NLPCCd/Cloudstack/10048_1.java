//,temp,sample_4076.java,2,15,temp,sample_4075.java,2,13
//,3
public class xxx {
private void getPifs() {
final File dir = new File("/sys/devices/virtual/net");
final File[] netdevs = dir.listFiles();
final List<String> bridges = new ArrayList<String>();
for (int i = 0; i < netdevs.length; i++) {
final File isbridge = new File(netdevs[i].getAbsolutePath() + "/bridge");
final String netdevName = netdevs[i].getName();
if (isbridge.exists()) {


log.info("found bridge");
}
}
}

};