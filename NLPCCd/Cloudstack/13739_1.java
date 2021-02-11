//,temp,sample_2837.java,2,15,temp,sample_2836.java,2,14
//,3
public class xxx {
private String createOVAFromMetafile(String metafileName) throws Exception {
File ova_metafile = new File(metafileName);
Properties props = null;
String ovaFileName = "";
try (FileInputStream strm = new FileInputStream(ova_metafile);) {
props = new Properties();
props.load(strm);
ovaFileName = props.getProperty("ova.filename");
String ovfFileName = props.getProperty("ovf");


log.info("ovffilename");
}
}

};