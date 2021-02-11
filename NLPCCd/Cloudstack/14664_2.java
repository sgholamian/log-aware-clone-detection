//,temp,sample_6916.java,2,17,temp,sample_6880.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (cmd.getLoadInfo() == null) {
return;
}
ConsoleProxyStatus status = null;
try {
GsonBuilder gb = new GsonBuilder();
gb.setVersion(1.3);
Gson gson = gb.create();
status = gson.fromJson(cmd.getLoadInfo(), ConsoleProxyStatus.class);
} catch (Throwable e) {


log.info("unable to parse load info from proxy proxy vm id info");
}
}

};