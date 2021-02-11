//,temp,S3AFileSystem.java,3162,3200,temp,S3AFileSystem.java,1881,1921
//,3
public class xxx {
  public FileStatus[] innerListStatus(Path f) throws FileNotFoundException,
      IOException, AmazonClientException {
    Path path = qualify(f);
    String key = pathToKey(path);
    LOG.debug("List status for path: {}", path);
    entryPoint(INVOCATION_LIST_STATUS);

    List<FileStatus> result;
    final FileStatus fileStatus =  getFileStatus(path);

    if (fileStatus.isDirectory()) {
      if (!key.isEmpty()) {
        key = key + '/';
      }

      DirListingMetadata dirMeta = metadataStore.listChildren(path);
      if (allowAuthoritative && dirMeta != null && dirMeta.isAuthoritative()) {
        return S3Guard.dirMetaToStatuses(dirMeta);
      }

      S3ListRequest request = createListObjectsRequest(key, "/");
      LOG.debug("listStatus: doing listObjects for directory {}", key);

      Listing.FileStatusListingIterator files =
          listing.createFileStatusListingIterator(path,
              request,
              ACCEPT_ALL,
              new Listing.AcceptAllButSelfAndS3nDirs(path));
      result = new ArrayList<>(files.getBatchSize());
      while (files.hasNext()) {
        result.add(files.next());
      }
      return S3Guard.dirListingUnion(metadataStore, path, result, dirMeta,
          allowAuthoritative);
    } else {
      LOG.debug("Adding: rd (not a dir): {}", path);
      FileStatus[] stats = new FileStatus[1];
      stats[0]= fileStatus;
      return stats;
    }
  }

};