//,temp,sample_3731.java,2,16,temp,sample_3732.java,2,16
//,2
public class xxx {
public void dummy_method(){
FileStatus[] outFileStatuses = fs.listStatus(inputDir, new Utils.OutputFileUtils.OutputFilesFilter());
for (FileStatus status : outFileStatuses) {
if (compressionCodecs != null) {
CompressionCodec codec = compressionCodecs.getCodec(status.getPath());
if (codec != null) {
++numCompressedFiles;
compressedDataSize += status.getLen();
}
}
}


log.info("total size of compressed input data");
}

};