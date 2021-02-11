//,temp,NativeAzureFileSystem.java,2143,2376,temp,NativeAzureFileSystem.java,1905,2141
//,3
public class xxx {
  private boolean deleteWithAuthEnabled(Path f, boolean recursive,
      boolean skipParentFolderLastModifiedTimeUpdate) throws IOException {

    LOG.debug("Deleting file: {}", f);

    Path absolutePath = makeAbsolute(f);
    Path parentPath = absolutePath.getParent();

    // If delete is issued for 'root', parentPath will be null
    // In that case, we perform auth check for root itself before
    // proceeding for deleting contents under root.
    if (parentPath != null) {
      performAuthCheck(parentPath, WasbAuthorizationOperations.WRITE, "delete", absolutePath);
    } else {
      performAuthCheck(absolutePath, WasbAuthorizationOperations.WRITE, "delete", absolutePath);
    }

    String key = pathToKey(absolutePath);

    // Capture the metadata for the path.
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

    FileMetadata parentMetadata = null;
    String parentKey = null;
    if (parentPath != null) {
      parentKey = pathToKey(parentPath);

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

      // Same case as unable to retrieve metadata
      if (parentMetadata == null) {
          throw new IOException("File " + f + " has a parent directory "
              + parentPath + " whose metadata cannot be retrieved. Can't resolve");
      }

      if (!parentMetadata.isDirectory()) {
         // Invalid state: the parent path is actually a file. Throw.
         throw new AzureException("File " + f + " has a parent directory "
             + parentPath + " which is also a file. Can't resolve.");
      }
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

      if (parentPath != null && parentPath.getParent() != null) {// Not root

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

      // check if the file can be deleted based on sticky bit check
      // This check will be performed only when authorization is enabled
      if (isStickyBitCheckViolated(metaFile, parentMetadata)) {
        throw new WasbAuthorizationException(String.format("%s has sticky bit set. "
          + "File %s cannot be deleted.", parentPath, f));
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
      if (parentPath != null && parentPath.getParent() != null) {

        if (parentMetadata.getBlobMaterialization() == BlobMaterialization.Implicit) {
          LOG.debug("Found an implicit parent directory while trying to"
                  + " delete the directory {}. Creating the directory blob for"
                  + " it in {}. ", f, parentKey);

          store.storeEmptyFolder(parentKey,
                  createPermissionStatus(FsPermission.getDefault()));
        }
      }

      // check if the folder can be deleted based on sticky bit check on parent
      // This check will be performed only when authorization is enabled.
      if (!metaFile.getKey().equals("/")
          && isStickyBitCheckViolated(metaFile, parentMetadata)) {

        throw new WasbAuthorizationException(String.format("%s has sticky bit set. "
          + "File %s cannot be deleted.", parentPath, f));
      }

      // Iterate through folder contents and get the list of files
      // and folders that can be deleted. We might encounter IOException
      // while listing blobs. In such cases, we return false.
      ArrayList<FileMetadata> fileMetadataList = new ArrayList<>();
      boolean isPartialDelete = false;

      // Start time for list operation
      long start = Time.monotonicNow();

      try {
        // Get list of files/folders that can be deleted
        // based on authorization checks and stickybit checks
        isPartialDelete = getFolderContentsToDelete(metaFile, fileMetadataList);
      } catch (IOException e) {
        Throwable innerException = checkForAzureStorageException(e);

        if (innerException instanceof StorageException
            && isFileNotFoundException((StorageException) innerException)) {
            return false;
        }
        throw e;
      }

      long end = Time.monotonicNow();
      LOG.debug("Time taken to list {} blobs for delete operation: {} ms",
        fileMetadataList.size(), (end - start));

      // Here contents holds the list of metadata of the files and folders that can be deleted
      // under the path that is requested for delete(excluding itself).
      final FileMetadata[] contents = fileMetadataList.toArray(new FileMetadata[fileMetadataList.size()]);

      if (contents.length > 0 && !recursive) {
          // The folder is non-empty and recursive delete was not specified.
          // Throw an exception indicating that a non-recursive delete was
          // specified for a non-empty folder.
          throw new IOException("Non-recursive delete of non-empty directory "
              + f);
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

      if (metaFile.getKey().equals("/")) {
        LOG.error("Cannot delete root directory {}", f);
        return false;
      }

      // Delete the current directory if all underlying contents are deleted
      if (isPartialDelete || (store.retrieveMetadata(metaFile.getKey()) != null
          && !deleteFile(metaFile.getKey(), metaFile.isDirectory()))) {
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