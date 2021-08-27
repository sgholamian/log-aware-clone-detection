//,temp,DirectoryExtension.java,231,240,temp,DirectoryExtension.java,222,229
//,3
public class xxx {
        private void revert(DirectoryService dirService, long revision) throws Exception {
            if (dirService == null) {
                return;
            }
            ChangeLog cl = dirService.getChangeLog();
            if (cl.isEnabled() && (revision < cl.getCurrentRevision())) {
                LOG.debug("Revert revision {}", revision);
                dirService.revert(revision);
            }
        }

};