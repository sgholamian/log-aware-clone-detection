//,temp,NativeAzureFileSystem.java,2143,2376,temp,NativeAzureFileSystem.java,1905,2141
//,3
public class xxx {
  private boolean deleteWithoutAuth(Path f, boolean recursive,
      boolean skipParentFolderLastModifiedTimeUpdate) throws IOException {

    LOG.debug("Deleting file: {}", f);

    Path absolutePath = makeAbsolute(f);
    Path parentPath = absolutePath.getParent();

    String key = pathToKey(absolutePath);

    // Capture the metadata for the path.
    //
    FileMetadata metaFile = null;
    try {
      metaFile = store.retrieveMetadata(key);
    } catch (IOException e) {

      Throwable innerException = checkForAzureStorageException(e);

      if (innerException instanceof StorageException
          && isFileNotFoundException((StorageException) innerException)) {

        return false;
      }
      throw e;
    }

    if (null == metaFile) {
      // The path to be deleted does not exist.
      return false;
    }

    // The path exists, determine if it is a folder containing objects,
    // an empty folder, or a simple file and take the appropriate actions.
    if (!metaFile.isDirectory()) {
      // The path specifies a file. We need to check the parent path
      // to make sure it's a proper materialized directory before we
      // delete the file. Otherwise we may get into a situation where
      // the file we were deleting was the last one in an implicit directory
      // (e.g. the blob store only contains the blob a/b and there's no
      // corresponding directory blob a) and that would implicitly delete
      // the directory as well, which is not correct.

      if (parentPath.getParent() != null) {// Not root
        String parentKey = pathToKey(parentPath);

        FileMetadata parentMetadata = null;
        try {
          parentMetadata = store.retrieveMetadata(parentKey);
        } catch (IOException e) {

          Throwable innerException = checkForAzureStorageException(e);

          if (innerException instanceof StorageException) {
            // Invalid State.
            // A FileNotFoundException is not thrown here as the API returns false
            // if the file not present. But not retrieving metadata here is an
            // unrecoverable state and can only happen if there is a race condition
            // hence throwing a IOException
            if (isFileNotFoundException((StorageException) innerException)) {
              throw new IOException("File " + f + " has a parent directory "
                  + parentPath + " whose metadata cannot be retrieved. Can't resolve");
            }
          }
          throw e;
        }

        // Invalid State.
        // A FileNotFoundException is not thrown here as the API returns false
        // if the file not present. But not retrieving metadata here is an
        // unrecoverable state and can only happen if there is a race condition
        // hence throwing a IOException
        if (parentMetadata == null) {
          throw new IOException("File " + f + " has a parent directory "
              + parentPath + " whose metadata cannot be retrieved. Can't resolve");
        }

        if (!parentMetadata.isDirectory()) {
          // Invalid state: the parent path is actually a file. Throw.
          throw new AzureException("File " + f + " has a parent directory "
              + parentPath + " which is also a file. Can't resolve.");
        }

        if (parentMetadata.getBlobMaterialization() == BlobMaterialization.Implicit) {
          LOG.debug("Found an implicit parent directory while trying to"
              + " delete the file {}. Creating the directory blob for"
              + " it in {}.", f, parentKey);

          store.storeEmptyFolder(parentKey,
              createPermissionStatus(FsPermission.getDefault()));
        } else {
          if (!skipParentFolderLastModifiedTimeUpdate) {
            updateParentFolderLastModifiedTime(key);
          }
        }
      }

      try {
        if (store.delete(key)) {
          instrumentation.fileDeleted();
        } else {
          return false;
        }
      } catch(IOException e) {

        Throwable innerException = checkForAzureStorageException(e);

        if (innerException instanceof StorageException
            && isFileNotFoundException((StorageException) innerException)) {
          return false;
        }

       throw e;
      }
    } else {
      // The path specifies a folder. Recursively delete all entries under the
      // folder.
      LOG.debug("Directory Delete encountered: {}", f);
      if (parentPath.getParent() != null) {
        String parentKey = pathToKey(parentPath);
        FileMetadata parentMetadata = null;

        try {
          parentMetadata = store.retrieveMetadata(parentKey);
        } catch (IOException e) {

          Throwable innerException = checkForAzureStorageException(e);

          if (innerException instanceof StorageException) {
            // Invalid State.
            // A FileNotFoundException is not thrown here as the API returns false
            // if the file not present. But not retrieving metadata here is an
            // unrecoverable state and can only happen if there is a race condition
            // hence throwing a IOException
            if (isFileNotFoundException((StorageException) innerException)) {
              throw new IOException("File " + f + " has a parent directory "
                  + parentPath + " whose metadata cannot be retrieved. Can't resolve");
            }
          }
          throw e;
        }

        // Invalid State.
        // A FileNotFoundException is not thrown here as the API returns false
        // if the file not present. But not retrieving metadata here is an
        // unrecoverable state and can only happen if there is a race condition
        // hence throwing a IOException
        if (parentMetadata == null) {
          throw new IOException("File " + f + " has a parent directory "
              + parentPath + " whose metadata cannot be retrieved. Can't resolve");
        }

        if (parentMetadata.getBlobMaterialization() == BlobMaterialization.Implicit) {
          LOG.debug("Found an implicit parent directory while trying to"
              + " delete the directory {}. Creating the directory blob for"
              + " it in {}. ", f, parentKey);

          store.storeEmptyFolder(parentKey,
              createPermissionStatus(FsPermission.getDefault()));
        }
      }

      // Start time for list operation
      long start = Time.monotonicNow();
      final FileMetadata[] contents;

      // List all the files in the folder with AZURE_UNBOUNDED_DEPTH depth.
      try {
        contents = store.list(key, AZURE_LIST_ALL,
            AZURE_UNBOUNDED_DEPTH);
      } catch (IOException e) {
        Throwable innerException = checkForAzureStorageException(e);

        if (innerException instanceof StorageException
            && isFileNotFoundException((StorageException) innerException)) {
          return false;
        }

        throw e;
      }

      long end = Time.monotonicNow();
      LOG.debug("Time taken to list {} blobs for delete operation: {} ms", contents.length, (end - start));

      if (contents.length > 0) {
        if (!recursive) {
          // The folder is non-empty and recursive delete was not specified.
          // Throw an exception indicating that a non-recursive delete was
          // specified for a non-empty folder.
          throw new IOException("Non-recursive delete of non-empty directory "+ f);
        }
      }

      // Delete all files / folders in current directory stored as list in 'contents'.
      AzureFileSystemThreadTask task = new AzureFileSystemThreadTask() {
        @Override
        public boolean execute(FileMetadata file) throws IOException{
          if (!deleteFile(file.getKey(), file.isDirectory())) {
            LOG.warn("Attempt to delete non-existent {} {}",
                file.isDirectory() ? "directory" : "file",
                file.getKey());
          }
          return true;
        }
      };

      AzureFileSystemThreadPoolExecutor executor = getThreadPoolExecutor(this.deleteThreadCount,
          "AzureBlobDeleteThread", "Delete", key, AZURE_DELETE_THREADS);

      if (!executor.executeParallel(contents, task)) {
        LOG.error("Failed to delete files / subfolders in blob {}", key);
        return false;
      }

      // Delete the current directory
      if (store.retrieveMetadata(metaFile.getKey()) != null
          && !deleteFile(metaFile.getKey(), metaFile.isDirectory())) {
        LOG.error("Failed delete directory : {}", f);
        return false;
      }

      // Update parent directory last modified time
      Path parent = absolutePath.getParent();
      if (parent != null && parent.getParent() != null) { // not root
        if (!skipParentFolderLastModifiedTimeUpdate) {
          updateParentFolderLastModifiedTime(key);
        }
      }
    }

    // File or directory was successfully deleted.
    LOG.debug("Delete Successful for : {}", f);
    return true;
  }

};