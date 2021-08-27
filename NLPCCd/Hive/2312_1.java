//,temp,sample_3091.java,2,16,temp,sample_3090.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (conf.getBoolean(SHUFFLE_DIR_WATCHER_ENABLED, SHUFFLE_DIR_WATCHER_ENABLED_DEFAULT)) {
DirWatcher localDirWatcher = null;
try {
localDirWatcher = new DirWatcher(this);
} catch (IOException e) {
}
dirWatcher = localDirWatcher;
} else {
dirWatcher = null;
}


log.info("manageoscache shouldalwaysevictoscache readaheadlength maxshuffleconnections localdirs shufflebuffersize shuffletransfertoallowed connectionkeepaliveenabled connectionkeepalivetimeout mapoutputmetainfocachesize sslfilebuffersize");
}

};