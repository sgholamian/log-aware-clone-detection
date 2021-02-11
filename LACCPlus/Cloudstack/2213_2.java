//,temp,ClusterMO.java,640,650,temp,HostDatastoreBrowserMO.java,41,49
//,3
public class xxx {
    public void DeleteFile(String datastoreFullPath) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - deleteFile(). target mor: " + _mor.getValue() + ", file datastore path: " + datastoreFullPath);

        _context.getService().deleteFile(_mor, datastoreFullPath);

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - deleteFile() done");
    }

};