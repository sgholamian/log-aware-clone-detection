//,temp,sample_4589.java,2,16,temp,sample_4590.java,2,17
//,3
public class xxx {
private void createDefaultRoles_core() throws MetaException {
RawStore ms = getMS();
try {
ms.addRole(ADMIN, ADMIN);
} catch (InvalidObjectException e) {
} catch (NoSuchObjectException e) {
}
try {
ms.addRole(PUBLIC, PUBLIC);
} catch (InvalidObjectException e) {


log.info("role already exists");
}
}

};