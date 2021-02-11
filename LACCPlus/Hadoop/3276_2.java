//,temp,RpcProgramNfs3.java,1331,1425,temp,RpcProgramNfs3.java,406,481
//,3
public class xxx {
  @VisibleForTesting
  SETATTR3Response setattr(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    SETATTR3Response response = new SETATTR3Response(Nfs3Status.NFS3_OK);

    SETATTR3Request request;
    try {
      request = SETATTR3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid SETATTR request");
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS SETATTR fileHandle: {} client: {}",
          handle.dumpFileHandle(), remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    if (request.getAttr().getUpdateFields().contains(SetAttrField.SIZE)) {
      LOG.error("Setting file size is not supported when setattr, fileId: {}",
          handle.getFileId());
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    String fileIdPath = Nfs3Utils.getFileIdPath(handle);
    Nfs3FileAttributes preOpAttr = null;
    try {
      preOpAttr = Nfs3Utils.getFileAttr(dfsClient, fileIdPath, iug);
      if (preOpAttr == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        response.setStatus(Nfs3Status.NFS3ERR_STALE);
        return response;
      }
      WccAttr preOpWcc = Nfs3Utils.getWccAttr(preOpAttr);
      if (request.isCheck()) {
        if (!preOpAttr.getCtime().equals(request.getCtime())) {
          WccData wccData = new WccData(preOpWcc, preOpAttr);
          return new SETATTR3Response(Nfs3Status.NFS3ERR_NOT_SYNC, wccData);
        }
      }

      // check the write access privilege
      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new SETATTR3Response(Nfs3Status.NFS3ERR_ACCES, new WccData(
            preOpWcc, preOpAttr));
      }

      setattrInternal(dfsClient, fileIdPath, request.getAttr(), true);
      Nfs3FileAttributes postOpAttr = Nfs3Utils.getFileAttr(dfsClient,
          fileIdPath, iug);
      WccData wccData = new WccData(preOpWcc, postOpAttr);
      return new SETATTR3Response(Nfs3Status.NFS3_OK, wccData);
    } catch (IOException e) {
      LOG.warn("Exception", e);
      WccData wccData = null;
      try {
        wccData = Nfs3Utils.createWccData(Nfs3Utils.getWccAttr(preOpAttr),
            dfsClient, fileIdPath, iug);
      } catch (IOException e1) {
        LOG.info("Can't get postOpAttr for fileIdPath: {}", fileIdPath, e1);
      }

      int status = mapErrorStatus(e);
      return new SETATTR3Response(status, wccData);
    }
  }

};