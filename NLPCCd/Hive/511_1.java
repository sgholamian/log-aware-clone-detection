//,temp,sample_4586.java,2,11,temp,sample_4627.java,2,10
//,3
public class xxx {
private void createDefaultRoles_core() throws MetaException {
RawStore ms = getMS();
try {
ms.addRole(ADMIN, ADMIN);
} catch (InvalidObjectException e) {


log.info("role already exists");
}
}

};