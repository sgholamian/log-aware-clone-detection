//,temp,sample_7217.java,2,16,temp,sample_1297.java,2,15
//,3
public class xxx {
public void dummy_method(){
if (keyvalues != null) {
for (String prop : keyvalues) {
String[] keyval = prop.split("=", 2);
if (keyval.length == 2) {
conf.set(keyval[0], keyval[1]);
num_confs_updated++;
} else {
}
}
}


log.info("updated configuration settings from command line");
}

};