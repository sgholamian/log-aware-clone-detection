//,temp,sample_211.java,2,21,temp,sample_212.java,2,18
//,3
public class xxx {
public void dummy_method(){
boolean isMmTable = fileSinkDesc.isMmTable();
if (chDir) {
dest = fileSinkDesc.getMergeInputDirName();
if (!isMmTable) {
Context baseCtx = parseCtx.getContext();
Path tmpDir = baseCtx.getTempDirForFinalJobPath(fileSinkDesc.getDestPath());
if (fileSinkDesc.isLinkedFileSink()) {
for (FileSinkDesc fsConf : fileSinkDesc.getLinkedFileSinkDesc()) {
fsConf.setDirName(new Path(tmpDir, fsConf.getDirName().getName()));
if (Utilities.FILE_OP_LOGGER.isTraceEnabled()) {


log.info("createmovetask setting tmpdir for linkedfilesink chdir dest was");
}
}
}
}
}
}

};