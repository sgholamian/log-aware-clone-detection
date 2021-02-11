//,temp,HostDatastoreBrowserMO.java,82,104,temp,HostDatastoreBrowserMO.java,51,72
//,3
public class xxx {
    public HostDatastoreBrowserSearchResults searchDatastore(String datastorePath, HostDatastoreBrowserSearchSpec searchSpec) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - searchDatastore(). target mor: " + _mor.getValue() + ", file datastore path: " + datastorePath);

        try {
            ManagedObjectReference morTask = _context.getService().searchDatastoreTask(_mor, datastorePath, searchSpec);

            boolean result = _context.getVimClient().waitForTask(morTask);
            if (result) {
                _context.waitForTaskProgressDone(morTask);

                return (HostDatastoreBrowserSearchResults)_context.getVimClient().getDynamicProperty(morTask, "info.result");
            } else {
                s_logger.error("VMware searchDaastore_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }
        } finally {
            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - searchDatastore() done");
        }

        return null;
    }

};