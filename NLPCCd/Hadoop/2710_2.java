//,temp,sample_5621.java,2,17,temp,sample_5620.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
new PermissionParam("-1");
Assert.fail();
} catch(IllegalArgumentException e) {
}
new PermissionParam("1777");
try {
new PermissionParam("2000");
Assert.fail();
} catch(IllegalArgumentException e) {


log.info("expected");
}
}

};