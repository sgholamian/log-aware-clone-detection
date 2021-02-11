//,temp,sample_4736.java,2,14,temp,sample_4737.java,2,16
//,3
public class xxx {
public int run(String[] args) {
if (getConf() == null) {
throw new RuntimeException("A Configuration instance must be provided.");
}
try {
FSUtils.setFsDefault(getConf(), FSUtils.getRootDir(getConf()));
if (!parseOptions(args)) return 1;
} catch (IOException ex) {


log.info("error parsing command line options");
}
}

};