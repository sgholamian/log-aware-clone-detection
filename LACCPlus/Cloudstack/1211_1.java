//,temp,HostDatastoreBrowserMO.java,82,104,temp,HostDatastoreBrowserMO.java,51,72
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    public ArrayList<HostDatastoreBrowserSearchResults> searchDatastoreSubFolders(String datastorePath, HostDatastoreBrowserSearchSpec searchSpec) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - searchDatastoreSubFolders(). target mor: " + _mor.getValue() + ", file datastore path: " + datastorePath);

        try {
            ManagedObjectReference morTask = _context.getService().searchDatastoreSubFoldersTask(_mor, datastorePath, searchSpec);

            boolean result = _context.getVimClient().waitForTask(morTask);
            if (result) {
                _context.waitForTaskProgressDone(morTask);

                return (ArrayList<HostDatastoreBrowserSearchResults>)_context.getVimClient().getDynamicProperty(morTask, "info.result");
            } else {
                s_logger.error("VMware searchDaastoreSubFolders_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }
        } finally {
            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - searchDatastoreSubFolders() done");
        }

        return null;
    }

};