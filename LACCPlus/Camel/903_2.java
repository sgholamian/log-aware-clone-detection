//,temp,DirectoryExtension.java,198,210,temp,DirectoryExtension.java,174,196
//,3
public class xxx {
        public void afterEach(ExtensionContext context) throws Exception {
            if (oldLdapServerDirService != null) {
                ldapServer.setDirectoryService(oldLdapServerDirService);
            }
            if (oldKdcServerDirService != null) {
                kdcServer.setDirectoryService(oldKdcServerDirService);
            }
            if (methodLdapServer != null) {
                methodLdapServer.stop();
            }
            if (methodKdcServer != null) {
                methodKdcServer.stop();
            }
            // Cleanup the methodDS if it has been created
            if (methodDirectoryService != null) {
                LOG.debug("Shuting down DS for {}", methodDirectoryService.getInstanceId());
                methodDirectoryService.shutdown();
                FileUtils.deleteDirectory(methodDirectoryService.getInstanceLayout().getInstanceDirectory());
            } else {
                // We use a class or suite DS, just revert the current test's modifications
                revert(classDirectoryService, revision);
            }
        }

};