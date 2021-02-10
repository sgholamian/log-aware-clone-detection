//,temp,RpcProgramNfs3.java,555,622,temp,RpcProgramNfs3.java,488,548
//,3
public class xxx {
  @VisibleForTesting
  LOOKUP3Response lookup(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    LOOKUP3Response response = new LOOKUP3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    LOOKUP3Request request;
    try {
      request = LOOKUP3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid LOOKUP request");
      return new LOOKUP3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle dirHandle = request.getHandle();
    String fileName = request.getName();
    int namenodeId = dirHandle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS LOOKUP dir fileHandle: {} name: {} client: {}",
          dirHandle.dumpFileHandle(), fileName, remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }
    try {
      String dirFileIdPath = Nfs3Utils.getFileIdPath(dirHandle);
      Nfs3FileAttributes postOpObjAttr = writeManager.getFileAttr(dfsClient,
          dirHandle, fileName, namenodeId);
      if (postOpObjAttr == null) {
        LOG.debug("NFS LOOKUP fileId: {} name: {} does not exist",
            dirHandle.getFileId(), fileName);
        Nfs3FileAttributes postOpDirAttr = Nfs3Utils.getFileAttr(dfsClient,
            dirFileIdPath, iug);
        return new LOOKUP3Response(Nfs3Status.NFS3ERR_NOENT, null, null,
            postOpDirAttr);
      }

      Nfs3FileAttributes postOpDirAttr = Nfs3Utils.getFileAttr(dfsClient,
          dirFileIdPath, iug);
      if (postOpDirAttr == null) {
        LOG.info("Can't get path for dir fileId: {}", dirHandle.getFileId());
        return new LOOKUP3Response(Nfs3Status.NFS3ERR_STALE);
      }
      FileHandle fileHandle =
          new FileHandle(postOpObjAttr.getFileId(), namenodeId);
      return new LOOKUP3Response(Nfs3Status.NFS3_OK, fileHandle, postOpObjAttr,
          postOpDirAttr);

    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      return new LOOKUP3Response(status);
    }
  }

};