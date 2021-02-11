//,temp,sample_6730.java,2,16,temp,sample_6733.java,2,16
//,3
public class xxx {
public ArrayList<HostDatastoreBrowserSearchResults> searchDatastoreSubFolders(String datastorePath, HostDatastoreBrowserSearchSpec searchSpec) throws Exception {
try {
ManagedObjectReference morTask = _context.getService().searchDatastoreSubFoldersTask(_mor, datastorePath, searchSpec);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return (ArrayList<HostDatastoreBrowserSearchResults>)_context.getVimClient().getDynamicProperty(morTask, "info.result");
} else {
}
} finally {


log.info("vcenter api trace searchdatastore done");
}
}

};