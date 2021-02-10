//,temp,RpcProgramNfs3.java,1331,1425,temp,RpcProgramNfs3.java,1162,1238
//,3
public class xxx {
  @VisibleForTesting
  RENAME3Response rename(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    RENAME3Response response = new RENAME3Response(Nfs3Status.NFS3_OK);

    RENAME3Request request = null;
    try {
      request = RENAME3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid RENAME request");
      return new RENAME3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle fromHandle = request.getFromDirHandle();
    int fromNamenodeId = fromHandle.getNamenodeId();
    String fromName = request.getFromName();
    FileHandle toHandle = request.getToDirHandle();
    int toNamenodeId = toHandle.getNamenodeId();
    String toName = request.getToName();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS RENAME from: {}/{} to: {}/{} client: {}",
          fromHandle.dumpFileHandle(), fromName, toHandle.dumpFileHandle(),
          toName, remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), fromNamenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    if (fromNamenodeId != toNamenodeId) {
      // renaming file from one namenode to another is not supported
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    String fromDirFileIdPath = Nfs3Utils.getFileIdPath(fromHandle);
    String toDirFileIdPath = Nfs3Utils.getFileIdPath(toHandle);
    Nfs3FileAttributes fromPreOpAttr = null;
    Nfs3FileAttributes toPreOpAttr = null;
    WccData fromDirWcc = null;
    WccData toDirWcc = null;
    try {
      fromPreOpAttr = Nfs3Utils.getFileAttr(dfsClient, fromDirFileIdPath, iug);
      if (fromPreOpAttr == null) {
        LOG.info("Can't get path for fromHandle fileId: {}",
            fromHandle.getFileId());
        return new RENAME3Response(Nfs3Status.NFS3ERR_STALE);
      }

      toPreOpAttr = Nfs3Utils.getFileAttr(dfsClient, toDirFileIdPath, iug);
      if (toPreOpAttr == null) {
        LOG.info("Can't get path for toHandle fileId: {}",
            toHandle.getFileId());
        return new RENAME3Response(Nfs3Status.NFS3ERR_STALE);
      }

      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        WccData fromWcc = new WccData(Nfs3Utils.getWccAttr(fromPreOpAttr),
            fromPreOpAttr);
        WccData toWcc = new WccData(Nfs3Utils.getWccAttr(toPreOpAttr),
            toPreOpAttr);
        return new RENAME3Response(Nfs3Status.NFS3ERR_ACCES, fromWcc, toWcc);
      }

      String src = fromDirFileIdPath + "/" + fromName;
      String dst = toDirFileIdPath + "/" + toName;

      dfsClient.rename(src, dst, Options.Rename.NONE);

      // Assemble the reply
      fromDirWcc = Nfs3Utils.createWccData(Nfs3Utils.getWccAttr(fromPreOpAttr),
          dfsClient, fromDirFileIdPath, iug);
      toDirWcc = Nfs3Utils.createWccData(Nfs3Utils.getWccAttr(toPreOpAttr),
          dfsClient, toDirFileIdPath, iug);
      return new RENAME3Response(Nfs3Status.NFS3_OK, fromDirWcc, toDirWcc);
    } catch (IOException e) {
      LOG.warn("Exception", e);
      // Try to return correct WccData
      try {
        fromDirWcc = Nfs3Utils.createWccData(
            Nfs3Utils.getWccAttr(fromPreOpAttr), dfsClient, fromDirFileIdPath,
            iug);
        toDirWcc = Nfs3Utils.createWccData(Nfs3Utils.getWccAttr(toPreOpAttr),
            dfsClient, toDirFileIdPath, iug);
      } catch (IOException e1) {
        LOG.info("Can't get postOpDirAttr for {} or {}",
            fromDirFileIdPath, toDirFileIdPath, e1);
      }

      int status = mapErrorStatus(e);
      return new RENAME3Response(status, fromDirWcc, toDirWcc);
    }
  }

};