//,temp,RpcProgramNfs3.java,1853,1921,temp,RpcProgramNfs3.java,1163,1236
//,3
public class xxx {
  @VisibleForTesting
  REMOVE3Response remove(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    REMOVE3Response response = new REMOVE3Response(Nfs3Status.NFS3_OK);
    DFSClient dfsClient = clientCache.getDfsClient(securityHandler.getUser());
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    REMOVE3Request request;
    try {
      request = REMOVE3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid REMOVE request");
      return new REMOVE3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    FileHandle dirHandle = request.getHandle();
    String fileName = request.getName();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS REMOVE dir fileId: " + dirHandle.getFileId()
          + " fileName: " + fileName + " client: " + remoteAddress);
    }

    String dirFileIdPath = Nfs3Utils.getFileIdPath(dirHandle);
    Nfs3FileAttributes preOpDirAttr = null;
    Nfs3FileAttributes postOpDirAttr = null;
    try {
      preOpDirAttr =  Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
      if (preOpDirAttr == null) {
        LOG.info("Can't get path for dir fileId: " + dirHandle.getFileId());
        return new REMOVE3Response(Nfs3Status.NFS3ERR_STALE);
      }

      WccData errWcc = new WccData(Nfs3Utils.getWccAttr(preOpDirAttr),
          preOpDirAttr);
      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new REMOVE3Response(Nfs3Status.NFS3ERR_ACCES, errWcc);
      }

      String fileIdPath = dirFileIdPath + "/" + fileName;
      HdfsFileStatus fstat = Nfs3Utils.getFileStatus(dfsClient, fileIdPath);
      if (fstat == null) {
        return new REMOVE3Response(Nfs3Status.NFS3ERR_NOENT, errWcc);
      }
      if (fstat.isDir()) {
        return new REMOVE3Response(Nfs3Status.NFS3ERR_ISDIR, errWcc);
      }

      boolean result = dfsClient.delete(fileIdPath, false);
      WccData dirWcc = Nfs3Utils.createWccData(
          Nfs3Utils.getWccAttr(preOpDirAttr), dfsClient, dirFileIdPath, iug);

      if (!result) {
        return new REMOVE3Response(Nfs3Status.NFS3ERR_ACCES, dirWcc);
      }
      return new REMOVE3Response(Nfs3Status.NFS3_OK, dirWcc);
    } catch (IOException e) {
      LOG.warn("Exception ", e);
      // Try to return correct WccData
      if (postOpDirAttr == null) {
        try {
          postOpDirAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
        } catch (IOException e1) {
          LOG.info("Can't get postOpDirAttr for " + dirFileIdPath, e1);
        }
      }

      WccData dirWcc = new WccData(Nfs3Utils.getWccAttr(preOpDirAttr),
          postOpDirAttr);
      int status = mapErrorStatus(e);
      return new REMOVE3Response(status, dirWcc);
    }
  }

};