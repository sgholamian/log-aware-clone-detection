//,temp,sample_129.java,2,13,temp,sample_1450.java,2,10
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
if (folder == null) {
return;
}
File dir = new File(folder);
if (dir.exists() && dir.isDirectory()) {


log.info("starting reloadstrategy to watch directory");
}
}

};