//,temp,sample_7865.java,2,12,temp,sample_7866.java,2,12
//,2
public class xxx {
private List<String> listVolumes(String rootdir) {
List<String> result = new ArrayList<String>();
Script script = new Script(listVolScr, s_logger);
script.add("-r", rootdir);
ZfsPathParser zpp = new ZfsPathParser(rootdir);
script.execute(zpp);
result.addAll(zpp.getPaths());


log.info("found volumes");
}

};