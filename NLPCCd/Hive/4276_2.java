//,temp,sample_65.java,2,17,temp,sample_66.java,2,17
//,3
public class xxx {
public void dummy_method(){
this.doAsEnabled = conf.getBoolVar(HiveConf.ConfVars.HIVE_SERVER2_ENABLE_DOAS);
final boolean llapMode = "llap".equalsIgnoreCase(HiveConf.getVar( conf, HiveConf.ConfVars.HIVE_EXECUTION_MODE));
UserGroupInformation ugi = Utils.getUGI();
user = ugi.getShortUserName();
tezScratchDir = createTezDir(sessionId, null);
if (resources != null) {
this.resources = resources;
} else {
this.resources = new HiveResources(createTezDir(sessionId, "resources"));
ensureLocalResources(conf, additionalFilesNotFromConf);


log.info("created new resources");
}
}

};