//,temp,RpcProgramNfs3.java,1693,1868,temp,RpcProgramNfs3.java,1528,1686
//,3
public class xxx {
  public READDIR3Response readdir(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    READDIR3Response response = new READDIR3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    READDIR3Request request;
    try {
      request = READDIR3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid READDIR request");
      return new READDIR3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();

    long cookie = request.getCookie();
    if (cookie < 0) {
      LOG.error("Invalid READDIR request, with negative cookie: {}", cookie);
      return new READDIR3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    long count = request.getCount();
    if (count <= 0) {
      LOG.info("Nonpositive count in invalid READDIR request: {}", count);
      return new READDIR3Response(Nfs3Status.NFS3_OK);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS READDIR fileHandle: {} cookie: {} count: {} client: {}",
          handle.dumpFileHandle(), cookie, count, remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    HdfsFileStatus dirStatus;
    DirectoryListing dlisting;
    Nfs3FileAttributes postOpAttr;
    long dotdotFileId = 0;
    try {
      String dirFileIdPath = Nfs3Utils.getFileIdPath(handle);
      dirStatus = dfsClient.getFileInfo(dirFileIdPath);
      if (dirStatus == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        return new READDIR3Response(Nfs3Status.NFS3ERR_STALE);
      }
      if (!dirStatus.isDirectory()) {
        LOG.error("Can't readdir for regular file, fileId: {}",
            handle.getFileId());
        return new READDIR3Response(Nfs3Status.NFS3ERR_NOTDIR);
      }
      long cookieVerf = request.getCookieVerf();
      if ((cookieVerf != 0) && (cookieVerf != dirStatus.getModificationTime())) {
        if (aixCompatMode) {
          // The AIX NFS client misinterprets RFC-1813 and will repeatedly send
          // the same cookieverf value even across VFS-level readdir calls,
          // instead of getting a new cookieverf for every VFS-level readdir
          // call, and reusing the cookieverf only in the event that multiple
          // incremental NFS-level readdir calls must be made to fetch all of
          // the directory entries. This means that whenever a readdir call is
          // made by an AIX NFS client for a given directory, and that directory
          // is subsequently modified, thus changing its mtime, no later readdir
          // calls will succeed from AIX for that directory until the FS is
          // unmounted/remounted. See HDFS-6549 for more info.
          LOG.warn("AIX compatibility mode enabled, ignoring cookieverf " +
              "mismatches.");
        } else {
          LOG.error("CookieVerf mismatch. request cookieVerf: {} " +
              "dir cookieVerf: {}",
              cookieVerf, dirStatus.getModificationTime());
          return new READDIR3Response(
              Nfs3Status.NFS3ERR_BAD_COOKIE,
              Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug));
        }
      }

      if (cookie == 0) {
        // Get dotdot fileId
        String dotdotFileIdPath = dirFileIdPath + "/..";
        HdfsFileStatus dotdotStatus = dfsClient.getFileInfo(dotdotFileIdPath);

        if (dotdotStatus == null) {
          // This should not happen
          throw new IOException("Can't get path for handle path: "
              + dotdotFileIdPath);
        }
        dotdotFileId = dotdotStatus.getFileId();
      }

      // Get the list from the resume point
      byte[] startAfter;
      if(cookie == 0 ) {
        startAfter = HdfsFileStatus.EMPTY_NAME;
      } else {
        String inodeIdPath = Nfs3Utils.getFileIdPath(cookie);
        startAfter = inodeIdPath.getBytes(Charset.forName("UTF-8"));
      }

      dlisting = listPaths(dfsClient, dirFileIdPath, startAfter);
      postOpAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
      if (postOpAttr == null) {
        LOG.error("Can't get path for fileId: {}", handle.getFileId());
        return new READDIR3Response(Nfs3Status.NFS3ERR_STALE);
      }
    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      return new READDIR3Response(status);
    }

    /**
     * Set up the dirents in the response. fileId is used as the cookie with one
     * exception. Linux client can either be stuck with "ls" command (on REHL)
     * or report "Too many levels of symbolic links" (Ubuntu).
     *
     * The problem is that, only two items returned, "." and ".." when the
     * namespace is empty. Both of them are "/" with the same cookie(root
     * fileId). Linux client doesn't think such a directory is a real directory.
     * Even though NFS protocol specifies cookie is an opaque data, Linux client
     * somehow doesn't like an empty dir returns same cookie for both "." and
     * "..".
     *
     * The workaround is to use 0 as the cookie for "." and always return "." as
     * the first entry in readdir/readdirplus response.
     */
    HdfsFileStatus[] fstatus = dlisting.getPartialListing();    
    int n = (int) Math.min(fstatus.length, count-2);
    boolean eof = (n >= fstatus.length) && !dlisting.hasMore();

    Entry3[] entries;
    if (cookie == 0) {
      entries = new Entry3[n + 2];
      entries[0] = new READDIR3Response.Entry3(postOpAttr.getFileId(), ".", 0);
      entries[1] = new READDIR3Response.Entry3(dotdotFileId, "..", dotdotFileId);

      for (int i = 2; i < n + 2; i++) {
        entries[i] = new READDIR3Response.Entry3(fstatus[i - 2].getFileId(),
            fstatus[i - 2].getLocalName(), fstatus[i - 2].getFileId());
      }
    } else {
      // Resume from last readdirplus. If the cookie is "..", the result
      // list is up the directory content since HDFS uses name as resume point.    
      entries = new Entry3[n];    
      for (int i = 0; i < n; i++) {
        entries[i] = new READDIR3Response.Entry3(fstatus[i].getFileId(),
            fstatus[i].getLocalName(), fstatus[i].getFileId());
      }
    }

    DirList3 dirList = new READDIR3Response.DirList3(entries, eof);
    return new READDIR3Response(Nfs3Status.NFS3_OK, postOpAttr,
        dirStatus.getModificationTime(), dirList);
  }

};