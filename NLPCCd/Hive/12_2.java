//,temp,sample_4587.java,2,12,temp,sample_4588.java,2,12
//,2
public class xxx {
private void createDefaultRoles_core() throws MetaException {
RawStore ms = getMS();
try {
ms.addRole(ADMIN, ADMIN);
} catch (InvalidObjectException e) {
} catch (NoSuchObjectException e) {
}


log.info("added role in metastore");
}

};