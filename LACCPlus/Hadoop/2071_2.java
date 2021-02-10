//,temp,NativeAzureFileSystem.java,1579,1628,temp,NativeS3FileSystem.java,464,508
//,3
public class xxx {
  @Override
  public FileStatus getFileStatus(Path f) throws IOException {
    Path absolutePath = makeAbsolute(f);
    String key = pathToKey(absolutePath);
    
    if (key.length() == 0) { // root always exists
      return newDirectory(absolutePath);
    }
    
    if(LOG.isDebugEnabled()) {
      LOG.debug("getFileStatus retrieving metadata for key '" + key + "'");
    }
    FileMetadata meta = store.retrieveMetadata(key);
    if (meta != null) {
      if(LOG.isDebugEnabled()) {
        LOG.debug("getFileStatus returning 'file' for key '" + key + "'");
      }
      return newFile(meta, absolutePath);
    }
    if (store.retrieveMetadata(key + FOLDER_SUFFIX) != null) {
      if(LOG.isDebugEnabled()) {
        LOG.debug("getFileStatus returning 'directory' for key '" + key +
            "' as '" + key + FOLDER_SUFFIX + "' exists");
      }
      return newDirectory(absolutePath);
    }
    
    if(LOG.isDebugEnabled()) {
      LOG.debug("getFileStatus listing key '" + key + "'");
    }
    PartialListing listing = store.list(key, 1);
    if (listing.getFiles().length > 0 ||
        listing.getCommonPrefixes().length > 0) {
      if(LOG.isDebugEnabled()) {
        LOG.debug("getFileStatus returning 'directory' for key '" + key +
            "' as it has contents");
      }
      return newDirectory(absolutePath);
    }
    
    if(LOG.isDebugEnabled()) {
      LOG.debug("getFileStatus could not find key '" + key + "'");
    }
    throw new FileNotFoundException("No such file or directory '" + absolutePath + "'");
  }

};