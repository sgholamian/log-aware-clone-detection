//,temp,sample_3749.java,2,17,temp,sample_3747.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (fileStatus.getLen() > 0) {
try {
OrcFile.createReader(fs, fileStatus.getPath());
console.printError("-- BEGIN ORC FILE DUMP --");
FileDump.main(new String[]{fileStatus.getPath().toString(), "--rowindex=*"});
console.printError("-- END ORC FILE DUMP --");
} catch (FileFormatException e) {
} catch (IOException e) {
}
} else {


log.info("zero length file encountered skip printing orc file dump");
}
}

};