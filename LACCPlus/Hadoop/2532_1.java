//,temp,RpcProgramNfs3.java,2077,2141,temp,RpcProgramNfs3.java,1952,2011
//,3
public class xxx {
  @VisibleForTesting
  COMMIT3Response commit(XDR xdr, Channel channel, int xid,
      SecurityHandler securityHandler, SocketAddress remoteAddress) {
    COMMIT3Response response = new COMMIT3Response(Nfs3Status.NFS3_OK);

    COMMIT3Request request;
    try {
      request = COMMIT3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid COMMIT request");
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS COMMIT fileHandle: {} offset={} count={} client: {}",
          handle.dumpFileHandle(), request.getOffset(), request.getCount(),
          remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    String fileIdPath = Nfs3Utils.getFileIdPath(handle);
    Nfs3FileAttributes preOpAttr = null;
    try {
      preOpAttr = Nfs3Utils.getFileAttr(dfsClient, fileIdPath, iug);
      if (preOpAttr == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        return new COMMIT3Response(Nfs3Status.NFS3ERR_STALE);
      }

      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new COMMIT3Response(Nfs3Status.NFS3ERR_ACCES, new WccData(
            Nfs3Utils.getWccAttr(preOpAttr), preOpAttr),
            Nfs3Constant.WRITE_COMMIT_VERF);
      }

      long commitOffset = (request.getCount() == 0) ? 0
          : (request.getOffset() + request.getCount());

      // Insert commit as an async request
      writeManager.handleCommit(dfsClient, handle, commitOffset, channel, xid,
          preOpAttr, namenodeId);
      return null;
    } catch (IOException e) {
      LOG.warn("Exception", e);
      Nfs3FileAttributes postOpAttr = null;
      try {
        postOpAttr = writeManager.getFileAttr(dfsClient, handle, iug);
      } catch (IOException e1) {
        LOG.info("Can't get postOpAttr for fileId: {}", handle.getFileId(), e1);
      }

      WccData fileWcc = new WccData(Nfs3Utils.getWccAttr(preOpAttr), postOpAttr);
      int status = mapErrorStatus(e);
      return new COMMIT3Response(status, fileWcc,
          Nfs3Constant.WRITE_COMMIT_VERF);
    }
  }

};