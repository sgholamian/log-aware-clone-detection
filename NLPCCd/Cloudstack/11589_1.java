//,temp,sample_8834.java,2,19,temp,sample_8833.java,2,19
//,3
public class xxx {
public void dummy_method(){
try {
if (!startVpc(vpc, dest, context)) {
result = false;
}
} catch (final Exception ex) {
result = false;
} finally {
if (!result && destroyOnFailure) {
if (destroyVpc(vpc, caller, callerUser.getId())) {
} else {


log.info("failed to destroy vpc that failed to start");
}
}
}
}

};