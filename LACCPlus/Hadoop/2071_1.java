//,temp,NativeAzureFileSystem.java,1579,1628,temp,NativeS3FileSystem.java,464,508
//,3
public class xxx {
  @Override
  public FileStatus getFileStatus(Path f) throws IOException {

    if (LOG.isDebugEnabled()) {
      LOG.debug("Getting the file status for " + f.toString());
    }

    // Capture the absolute path and the path to key.
    Path absolutePath = makeAbsolute(f);
    String key = pathToKey(absolutePath);
    if (key.length() == 0) { // root always exists
      return newDirectory(null, absolutePath);
    }

    // The path is either a folder or a file. Retrieve metadata to
    // determine if it is a directory or file.
    FileMetadata meta = store.retrieveMetadata(key);
    if (meta != null) {
      if (meta.isDir()) {
        // The path is a folder with files in it.
        //
        if (LOG.isDebugEnabled()) {
          LOG.debug("Path " + f.toString() + "is a folder.");
        }

        // If a rename operation for the folder was pending, redo it.
        // Then the file does not exist, so signal that.
        if (conditionalRedoFolderRename(f)) {
          throw new FileNotFoundException(
              absolutePath + ": No such file or directory.");
        }

        // Return reference to the directory object.
        return newDirectory(meta, absolutePath);
      }

      // The path is a file.
      if (LOG.isDebugEnabled()) {
        LOG.debug("Found the path: " + f.toString() + " as a file.");
      }

      // Return with reference to a file object.
      return newFile(meta, absolutePath);
    }

    // File not found. Throw exception no such file or directory.
    //
    throw new FileNotFoundException(
        absolutePath + ": No such file or directory.");
  }

};