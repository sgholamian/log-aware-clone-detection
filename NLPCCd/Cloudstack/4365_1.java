//,temp,sample_4120.java,2,18,temp,sample_5281.java,2,18
//,3
public class xxx {
public void dummy_method(){
Domain dm = null;
try {
dm = conn.domainLookupByName(vmName);
if (attach) {
dm.attachDevice(xml);
} else {
dm.detachDevice(xml);
}
} catch (final LibvirtException e) {
if (attach) {


log.info("failed to attach device to");
}
}
}

};