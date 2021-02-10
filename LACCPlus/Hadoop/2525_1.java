//,temp,RpcProgramNfs3.java,1058,1150,temp,RpcProgramNfs3.java,406,481
//,3
public class xxx {
  @VisibleForTesting
  MKDIR3Response mkdir(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    MKDIR3Response response = new MKDIR3Response(Nfs3Status.NFS3_OK);

    MKDIR3Request request;

    try {
      request = MKDIR3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid MKDIR request");
      return new MKDIR3Response(Nfs3Status.NFS3ERR_INVAL);
    }
    FileHandle dirHandle = request.getHandle();
    String fileName = request.getName();
    int namenodeId = dirHandle.getNamenodeId();

    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS MKDIR dirHandle: {} filename: {} client: {}",
          dirHandle.dumpFileHandle(), fileName, remoteAddress);
    }
    if (request.getObjAttr().getUpdateFields().contains(SetAttrField.SIZE)) {
      LOG.error("Setting file size is not supported when mkdir: " +
          "{} in dirHandle {}", fileName, dirHandle);
      return new MKDIR3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    String dirFileIdPath = Nfs3Utils.getFileIdPath(dirHandle);
    Nfs3FileAttributes preOpDirAttr = null;
    Nfs3FileAttributes postOpDirAttr = null;
    Nfs3FileAttributes postOpObjAttr = null;
    FileHandle objFileHandle = null;
    try {
      preOpDirAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
      if (preOpDirAttr == null) {
        LOG.info("Can't get path for dir fileId: {}", dirHandle.getFileId());
        return new MKDIR3Response(Nfs3Status.NFS3ERR_STALE);
      }

      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new MKDIR3Response(Nfs3Status.NFS3ERR_ACCES, null, preOpDirAttr,
            new WccData(Nfs3Utils.getWccAttr(preOpDirAttr), preOpDirAttr));
      }

      final String fileIdPath = dirFileIdPath + "/" + fileName;
      SetAttr3 setAttr3 = request.getObjAttr();
      FsPermission permission = setAttr3.getUpdateFields().contains(
          SetAttrField.MODE) ? new FsPermission((short) setAttr3.getMode())
          : FsPermission.getDefault().applyUMask(umask);

      if (!dfsClient.mkdirs(fileIdPath, permission, false)) {
        WccData dirWcc = Nfs3Utils.createWccData(
            Nfs3Utils.getWccAttr(preOpDirAttr), dfsClient, dirFileIdPath, iug);
        return new MKDIR3Response(Nfs3Status.NFS3ERR_IO, null, null, dirWcc);
      }

      // Set group if it's not specified in the request.
      if (!setAttr3.getUpdateFields().contains(SetAttrField.GID)) {
        setAttr3.getUpdateFields().add(SetAttrField.GID);
        setAttr3.setGid(securityHandler.getGid());
      }
      setattrInternal(dfsClient, fileIdPath, setAttr3, false);

      postOpObjAttr = Nfs3Utils.getFileAttr(dfsClient, fileIdPath, iug);
      objFileHandle = new FileHandle(postOpObjAttr.getFileId(), namenodeId);
      WccData dirWcc = Nfs3Utils.createWccData(
          Nfs3Utils.getWccAttr(preOpDirAttr), dfsClient, dirFileIdPath, iug);
      return new MKDIR3Response(Nfs3Status.NFS3_OK, new FileHandle(
          postOpObjAttr.getFileId(), namenodeId), postOpObjAttr, dirWcc);
    } catch (IOException e) {
      LOG.warn("Exception", e);
      // Try to return correct WccData
      if (postOpDirAttr == null) {
        try {
          postOpDirAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
        } catch (IOException e1) {
          LOG.info("Can't get postOpDirAttr for {}", dirFileIdPath, e);
        }
      }

      WccData dirWcc = new WccData(Nfs3Utils.getWccAttr(preOpDirAttr),
          postOpDirAttr);
      int status = mapErrorStatus(e);
      return new MKDIR3Response(status, objFileHandle, postOpObjAttr, dirWcc);
    }
  }

};