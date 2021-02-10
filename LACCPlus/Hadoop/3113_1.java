//,temp,S3AFileSystem.java,3162,3200,temp,S3AFileSystem.java,1881,1921
//,3
public class xxx {
  @Override
  @Retries.OnceTranslated("s3guard not retrying")
  public RemoteIterator<LocatedFileStatus> listLocatedStatus(final Path f,
      final PathFilter filter)
      throws FileNotFoundException, IOException {
    entryPoint(INVOCATION_LIST_LOCATED_STATUS);
    Path path = qualify(f);
    LOG.debug("listLocatedStatus({}, {}", path, filter);
    return once("listLocatedStatus", path.toString(),
        () -> {
          // lookup dir triggers existence check
          final FileStatus fileStatus = getFileStatus(path);
          if (fileStatus.isFile()) {
            // simple case: File
            LOG.debug("Path is a file");
            return new Listing.SingleStatusRemoteIterator(
                filter.accept(path) ? toLocatedFileStatus(fileStatus) : null);
          } else {
            // directory: trigger a lookup
            final String key = maybeAddTrailingSlash(pathToKey(path));
            final Listing.FileStatusAcceptor acceptor =
                new Listing.AcceptAllButSelfAndS3nDirs(path);
            DirListingMetadata meta = metadataStore.listChildren(path);
            final RemoteIterator<FileStatus> cachedFileStatusIterator =
                listing.createProvidedFileStatusIterator(
                    S3Guard.dirMetaToStatuses(meta), filter, acceptor);
            return (allowAuthoritative && meta != null
                && meta.isAuthoritative())
                ? listing.createLocatedFileStatusIterator(
                cachedFileStatusIterator)
                : listing.createLocatedFileStatusIterator(
                    listing.createFileStatusListingIterator(path,
                        createListObjectsRequest(key, "/"),
                        filter,
                        acceptor,
                        cachedFileStatusIterator));
          }
        });
  }

};