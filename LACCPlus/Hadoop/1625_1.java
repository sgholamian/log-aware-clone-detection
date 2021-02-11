//,temp,RpcProgramNfs3.java,1243,1321,temp,RpcProgramNfs3.java,408,481
//,3
public class xxx {
  @VisibleForTesting
  RMDIR3Response rmdir(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    RMDIR3Response response = new RMDIR3Response(Nfs3Status.NFS3_OK);
    DFSClient dfsClient = clientCache.getDfsClient(securityHandler.getUser());
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    RMDIR3Request request;
    try {
      request = RMDIR3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid RMDIR request");
      return new RMDIR3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    FileHandle dirHandle = request.getHandle();
    String fileName = request.getName();

    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS RMDIR dir fileId: " + dirHandle.getFileId()
          + " fileName: " + fileName + " client: " + remoteAddress);
    }

    String dirFileIdPath = Nfs3Utils.getFileIdPath(dirHandle);
    Nfs3FileAttributes preOpDirAttr = null;
    Nfs3FileAttributes postOpDirAttr = null;
    try {
      preOpDirAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
      if (preOpDirAttr == null) {
        LOG.info("Can't get path for dir fileId: " + dirHandle.getFileId());
        return new RMDIR3Response(Nfs3Status.NFS3ERR_STALE);
      }

      WccData errWcc = new WccData(Nfs3Utils.getWccAttr(preOpDirAttr),
          preOpDirAttr);
      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new RMDIR3Response(Nfs3Status.NFS3ERR_ACCES, errWcc); 
      }

      String fileIdPath = dirFileIdPath + "/" + fileName;
      HdfsFileStatus fstat = Nfs3Utils.getFileStatus(dfsClient, fileIdPath);
      if (fstat == null) {
        return new RMDIR3Response(Nfs3Status.NFS3ERR_NOENT, errWcc);
      }
      if (!fstat.isDir()) {
        return new RMDIR3Response(Nfs3Status.NFS3ERR_NOTDIR, errWcc);
      }

      if (fstat.getChildrenNum() > 0) {
        return new RMDIR3Response(Nfs3Status.NFS3ERR_NOTEMPTY, errWcc);
      }

      boolean result = dfsClient.delete(fileIdPath, false);
      WccData dirWcc = Nfs3Utils.createWccData(
          Nfs3Utils.getWccAttr(preOpDirAttr), dfsClient, dirFileIdPath, iug);
      if (!result) {
        return new RMDIR3Response(Nfs3Status.NFS3ERR_ACCES, dirWcc);
      }

      return new RMDIR3Response(Nfs3Status.NFS3_OK, dirWcc);
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
      return new RMDIR3Response(status, dirWcc);
    }
  }

};