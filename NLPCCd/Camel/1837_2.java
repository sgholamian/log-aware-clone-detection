//,temp,sample_1073.java,2,12,temp,sample_1510.java,2,12
//,2
public class xxx {
protected boolean isMatched(GenericFile<FTPFile> file, String doneFileName, List<FTPFile> files) {
String onlyName = FileUtil.stripPath(doneFileName);
for (FTPFile f : files) {
if (f.getName().equals(onlyName)) {
return true;
}
}


log.info("done file does not exist");
}

};