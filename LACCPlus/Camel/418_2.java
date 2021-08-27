//,temp,DirectoryExtension.java,231,240,temp,DirectoryExtension.java,222,229
//,3
public class xxx {
        private long getCurrentRevision(DirectoryService dirService) throws Exception {
            if ((dirService != null) && (dirService.getChangeLog().isEnabled())) {
                long revision = dirService.getChangeLog().getCurrentRevision();
                LOG.debug("Create revision {}", revision);
                return revision;
            }
            return 0;
        }

};