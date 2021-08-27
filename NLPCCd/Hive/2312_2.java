//,temp,sample_3091.java,2,16,temp,sample_3090.java,2,17
//,3
public class xxx {
public void dummy_method(){
secretManager = new JobTokenSecretManager();
shuffle = new Shuffle(conf);
if (conf.getBoolean(SHUFFLE_DIR_WATCHER_ENABLED, SHUFFLE_DIR_WATCHER_ENABLED_DEFAULT)) {
DirWatcher localDirWatcher = null;
try {
localDirWatcher = new DirWatcher(this);
} catch (IOException e) {
}
dirWatcher = localDirWatcher;
} else {


log.info("dirwatcher disabled by config");
}
}

};