//,temp,sample_1328.java,2,19,temp,sample_3786.java,2,19
//,2
public class xxx {
public void dummy_method(){
fsShell.setConf(conf);
if (group != null && !group.isEmpty()) {
run(fsShell, new String[]{"-chgrp", "-R", group, target.toString()});
}
if (aclEnabled) {
if (null != aclEntries) {
try {
String aclEntry = Joiner.on(",").join(aclEntries);
run(fsShell, new String[]{"-setfacl", "-R", "--set", aclEntry, target.toString()});
} catch (Exception e) {


log.info("the details are");
}
}
}
}

};