//,temp,sample_5281.java,2,18,temp,sample_4121.java,2,18
//,2
public class xxx {
public void dummy_method(){
try {
dm = conn.domainLookupByName(vmName);
if (attach) {
dm.attachDevice(xml);
} else {
dm.detachDevice(xml);
}
} catch (final LibvirtException e) {
if (attach) {
} else {


log.info("failed to detach device from");
}
}
}

};