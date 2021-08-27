//,temp,sample_3051.java,2,17,temp,sample_3050.java,2,17
//,2
public class xxx {
public void dummy_method(){
ConfigMap configMap;
try {
configMap = pullConfigMap();
} catch (Throwable e) {
return false;
}
Set<String> members;
try {
members = Objects.requireNonNull(pullClusterMembers(), "Retrieved a null set of members");
} catch (Throwable e) {


log.info("exception thrown during pod list lookup");
}
}

};