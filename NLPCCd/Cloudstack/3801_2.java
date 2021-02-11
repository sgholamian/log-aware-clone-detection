//,temp,sample_2232.java,2,18,temp,sample_2230.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (sp != null) {
try {
if (sp.isPersistent() == 1) {
sp.destroy();
sp.undefine();
} else {
sp.destroy();
}
sp.free();
} catch (LibvirtException l) {


log.info("failed to undefine storage pool with");
}
}
}

};