//,temp,RpcProgramNfs3.java,1693,1868,temp,RpcProgramNfs3.java,1528,1686
//,3
public class xxx {
  @VisibleForTesting
  READDIRPLUS3Response readdirplus(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_ACCES);
    }


    READDIRPLUS3Request request = null;
    try {
      request = READDIRPLUS3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid READDIRPLUS request");
      return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();
    long cookie = request.getCookie();
    if (cookie < 0) {
      LOG.error("Invalid READDIRPLUS request, with negative cookie: {}",
          cookie);
      return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    long dirCount = request.getDirCount();
    if (dirCount <= 0) {
      LOG.info("Nonpositive dircount in invalid READDIRPLUS request: {}",
          dirCount);
      return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    int maxCount = request.getMaxCount();
    if (maxCount <= 0) {
      LOG.info("Nonpositive maxcount in invalid READDIRPLUS request: {}",
          maxCount);
      return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS READDIRPLUS fileHandle: {} cookie: {} dirCount: {} " +
              "maxCount: {} client: {}",
          handle.dumpFileHandle(), cookie, dirCount, maxCount, remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_SERVERFAULT);
    }

    HdfsFileStatus dirStatus;
    DirectoryListing dlisting;
    Nfs3FileAttributes postOpDirAttr;
    long dotdotFileId = 0;
    HdfsFileStatus dotdotStatus = null;
    try {
      String dirFileIdPath = Nfs3Utils.getFileIdPath(handle);
      dirStatus = dfsClient.getFileInfo(dirFileIdPath);
      if (dirStatus == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_STALE);
      }
      if (!dirStatus.isDirectory()) {
        LOG.error("Can't readdirplus for regular file, fileId: {}",
            handle.getFileId());
        return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_NOTDIR);
      }
      long cookieVerf = request.getCookieVerf();
      if ((cookieVerf != 0) && (cookieVerf != dirStatus.getModificationTime())) {
        if (aixCompatMode) {
          // The AIX NFS client misinterprets RFC-1813 and will repeatedly send
          // the same cookieverf value even across VFS-level readdir calls,
          // instead of getting a new cookieverf for every VFS-level readdir
          // call. This means that whenever a readdir call is made by an AIX NFS
          // client for a given directory, and that directory is subsequently
          // modified, thus changing its mtime, no later readdir calls will
          // succeed for that directory from AIX until the FS is
          // unmounted/remounted. See HDFS-6549 for more info.
          LOG.warn("AIX compatibility mode enabled, ignoring cookieverf " +
              "mismatches.");
        } else {
          LOG.error("cookieverf mismatch. request cookieverf: {} " +
                  "dir cookieverf: {}",
              cookieVerf, dirStatus.getModificationTime());
          return new READDIRPLUS3Response(
              Nfs3Status.NFS3ERR_BAD_COOKIE,
              Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug),
              0, null);
        }
      }

      if (cookie == 0) {
        // Get dotdot fileId
        String dotdotFileIdPath = dirFileIdPath + "/..";
        dotdotStatus = dfsClient.getFileInfo(dotdotFileIdPath);

        if (dotdotStatus == null) {
          // This should not happen
          throw new IOException("Can't get path for handle path: "
              + dotdotFileIdPath);
        }
        dotdotFileId = dotdotStatus.getFileId();
      }

      // Get the list from the resume point
      byte[] startAfter;
      if (cookie == 0) {
        startAfter = HdfsFileStatus.EMPTY_NAME;
      } else {
        String inodeIdPath = Nfs3Utils.getFileIdPath(cookie);
        startAfter = inodeIdPath.getBytes(Charset.forName("UTF-8"));
      }

      dlisting = listPaths(dfsClient, dirFileIdPath, startAfter);
      postOpDirAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
      if (postOpDirAttr == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        return new READDIRPLUS3Response(Nfs3Status.NFS3ERR_STALE);
      }
    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      return new READDIRPLUS3Response(status);
    }

    // Set up the dirents in the response
    HdfsFileStatus[] fstatus = dlisting.getPartialListing();
    int n = (int) Math.min(fstatus.length, dirCount-2);
    boolean eof = (n >= fstatus.length) && !dlisting.hasMore();

    READDIRPLUS3Response.EntryPlus3[] entries;
    if (cookie == 0) {
      entries = new READDIRPLUS3Response.EntryPlus3[n+2];

      entries[0] = new READDIRPLUS3Response.EntryPlus3(
          postOpDirAttr.getFileId(), ".", 0, postOpDirAttr, new FileHandle(
              postOpDirAttr.getFileId(), namenodeId));
      entries[1] = new READDIRPLUS3Response.EntryPlus3(dotdotFileId, "..",
          dotdotFileId, Nfs3Utils.getNfs3FileAttrFromFileStatus(dotdotStatus,
              iug), new FileHandle(dotdotFileId, namenodeId));

      for (int i = 2; i < n + 2; i++) {
        long fileId = fstatus[i - 2].getFileId();
        FileHandle childHandle = new FileHandle(fileId, namenodeId);
        Nfs3FileAttributes attr;
        try {
          attr = writeManager.getFileAttr(dfsClient, childHandle, iug);
        } catch (IOException e) {
          LOG.error("Can't get file attributes for fileId: {}", fileId, e);
          continue;
        }
        entries[i] = new READDIRPLUS3Response.EntryPlus3(fileId,
            fstatus[i - 2].getLocalName(), fileId, attr, childHandle);
      }
    } else {
      // Resume from last readdirplus. If the cookie is "..", the result
      // list is up the directory content since HDFS uses name as resume point.
      entries = new READDIRPLUS3Response.EntryPlus3[n]; 
      for (int i = 0; i < n; i++) {
        long fileId = fstatus[i].getFileId();
        FileHandle childHandle = new FileHandle(fileId, namenodeId);
        Nfs3FileAttributes attr;
        try {
          attr = writeManager.getFileAttr(dfsClient, childHandle, iug);
        } catch (IOException e) {
          LOG.error("Can't get file attributes for fileId: {}", fileId, e);
          continue;
        }
        entries[i] = new READDIRPLUS3Response.EntryPlus3(fileId,
            fstatus[i].getLocalName(), fileId, attr, childHandle);
      }
    }

    DirListPlus3 dirListPlus = new READDIRPLUS3Response.DirListPlus3(entries,
        eof);
    return new READDIRPLUS3Response(Nfs3Status.NFS3_OK, postOpDirAttr,
        dirStatus.getModificationTime(), dirListPlus);
  }

};