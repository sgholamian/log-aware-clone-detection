//,temp,sample_5797.java,2,18,temp,sample_2228.java,2,18
//,3
public class xxx {
public void dummy_method(){
s_logger.error(e.toString());
if (e.toString().contains("already mounted")) {
String result = Script.runSimpleBashScript("umount -l " + targetPath);
if (result == null) {
try {
conn.storagePoolCreateXML(spd.toString(), 0);
return true;
} catch (LibvirtException l) {
}
} else {


log.info("failed in unmounting and redefining storage");
}
}
}

};