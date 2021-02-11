//,temp,DownloadManagerImpl.java,803,813,temp,DownloadManagerImpl.java,791,801
//,2
public class xxx {
    private List<String> listVolumes(String rootdir) {
        List<String> result = new ArrayList<String>();

        Script script = new Script(listVolScr, s_logger);
        script.add("-r", rootdir);
        ZfsPathParser zpp = new ZfsPathParser(rootdir);
        script.execute(zpp);
        result.addAll(zpp.getPaths());
        s_logger.info("found " + zpp.getPaths().size() + " volumes" + zpp.getPaths());
        return result;
    }

};