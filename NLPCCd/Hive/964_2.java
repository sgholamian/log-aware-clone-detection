//,temp,sample_3749.java,2,17,temp,sample_3747.java,2,19
//,3
public class xxx {
public void dummy_method(){
FileSystem fs = dir.getFileSystem(conf);
List<FileStatus> fileList = HdfsUtils.listLocatedStatus(fs, dir, hiddenFileFilter);
for (FileStatus fileStatus : fileList) {
if (fileStatus.getLen() > 0) {
try {
OrcFile.createReader(fs, fileStatus.getPath());
console.printError("-- BEGIN ORC FILE DUMP --");
FileDump.main(new String[]{fileStatus.getPath().toString(), "--rowindex=*"});
console.printError("-- END ORC FILE DUMP --");
} catch (FileFormatException e) {


log.info("file is not orc skip printing orc file dump");
}
}
}
}

};