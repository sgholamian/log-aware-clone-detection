//,temp,sample_5627.java,2,17,temp,sample_8854.java,2,11
//,3
public class xxx {
public void dummy_method(){
new AclPermissionParam( "user::rw-,group::rwx,other::rw-,user:user1:rwx,group:group1:rwx,other::rwx,mask::rwx,default:user:user1:rwx");
try {
new AclPermissionParam("user:r-,group:rwx,other:rw-");
Assert.fail();
} catch (IllegalArgumentException e) {
}
try {
new AclPermissionParam("default:::r-,default:group::rwx,other::rw-");
Assert.fail();
} catch (IllegalArgumentException e) {


log.info("expected");
}
}

};