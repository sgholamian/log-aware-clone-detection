//,temp,NativeS3FileSystem.java,617,628,temp,S3AFileSystem.java,371,384
//,3
public class xxx {
  public FSDataInputStream open(Path f, int bufferSize)
      throws IOException {

    if (LOG.isDebugEnabled()) {
      LOG.debug("Opening '{}' for reading.", f);
    }
    final FileStatus fileStatus = getFileStatus(f);
    if (fileStatus.isDirectory()) {
      throw new FileNotFoundException("Can't open " + f + " because it is a directory");
    }

    return new FSDataInputStream(new S3AInputStream(bucket, pathToKey(f), 
      fileStatus.getLen(), s3, statistics));
  }

};