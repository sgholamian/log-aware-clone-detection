//,temp,NativeS3FileSystem.java,617,628,temp,S3AFileSystem.java,371,384
//,3
public class xxx {
  @Override
  public FSDataInputStream open(Path f, int bufferSize) throws IOException {
    FileStatus fs = getFileStatus(f); // will throw if the file doesn't exist
    if (fs.isDirectory()) {
      throw new FileNotFoundException("'" + f + "' is a directory");
    }
    LOG.info("Opening '" + f + "' for reading");
    Path absolutePath = makeAbsolute(f);
    String key = pathToKey(absolutePath);
    return new FSDataInputStream(new BufferedFSInputStream(
        new NativeS3FsInputStream(store, statistics, store.retrieve(key), key), bufferSize));
  }

};