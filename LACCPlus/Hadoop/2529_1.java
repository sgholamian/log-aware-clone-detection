//,temp,RpcProgramNfs3.java,1875,1945,temp,RpcProgramNfs3.java,1245,1324
//,3
public class xxx {
  @VisibleForTesting
  FSSTAT3Response fsstat(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    FSSTAT3Response response = new FSSTAT3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }


    FSSTAT3Request request;
    try {
      request = FSSTAT3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid FSSTAT request");
      return new FSSTAT3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS FSSTAT fileHandle: {} client: {}",
          handle.dumpFileHandle(), remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    try {
      FsStatus fsStatus = dfsClient.getDiskStatus();
      long totalBytes = fsStatus.getCapacity();
      long freeBytes = fsStatus.getRemaining();

      Nfs3FileAttributes attrs = writeManager.getFileAttr(dfsClient, handle,
          iug);
      if (attrs == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        return new FSSTAT3Response(Nfs3Status.NFS3ERR_STALE);
      }

      long maxFsObjects = config.getLong("dfs.max.objects", 0);
      if (maxFsObjects == 0) {
        // A value of zero in HDFS indicates no limit to the number
        // of objects that dfs supports. Using Integer.MAX_VALUE instead of
        // Long.MAX_VALUE so 32bit client won't complain.
        maxFsObjects = Integer.MAX_VALUE;
      }

      return new FSSTAT3Response(Nfs3Status.NFS3_OK, attrs, totalBytes,
          freeBytes, freeBytes, maxFsObjects, maxFsObjects, maxFsObjects, 0);
    } catch (RemoteException r) {
      LOG.warn("Exception", r);
      IOException io = r.unwrapRemoteException();
      /**
       * AuthorizationException can be thrown if the user can't be proxy'ed.
       */
      if (io instanceof AuthorizationException) {
        return new FSSTAT3Response(Nfs3Status.NFS3ERR_ACCES);
      } else {
        return new FSSTAT3Response(Nfs3Status.NFS3ERR_IO);
      }
    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      return new FSSTAT3Response(status);
    }
  }

};