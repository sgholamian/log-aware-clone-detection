//,temp,DirectoryExtension.java,198,210,temp,DirectoryExtension.java,174,196
//,3
public class xxx {
        public void afterAll(ExtensionContext context) throws Exception {
            if (classLdapServer != null) {
                classLdapServer.stop();
            }
            if (classKdcServer != null) {
                classKdcServer.stop();
            }
            if (classDirectoryService != null) {
                LOG.debug("Shuting down DS for {}", classDirectoryService.getInstanceId());
                classDirectoryService.shutdown();
                FileUtils.deleteDirectory(classDirectoryService.getInstanceLayout().getInstanceDirectory());
            }
        }

};