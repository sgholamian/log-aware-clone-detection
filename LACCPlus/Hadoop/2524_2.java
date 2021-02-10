//,temp,RpcProgramNfs3.java,1058,1150,temp,RpcProgramNfs3.java,924,1051
//,3
public class xxx {
  @VisibleForTesting
  CREATE3Response create(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    CREATE3Response response = new CREATE3Response(Nfs3Status.NFS3_OK);

    CREATE3Request request;

    try {
      request = CREATE3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid CREATE request");
      return new CREATE3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle dirHandle = request.getHandle();
    String fileName = request.getName();
    int namenodeId = dirHandle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS CREATE dir fileHandle: {} filename: {} client: {}",
          dirHandle.dumpFileHandle(), fileName, remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    int createMode = request.getMode();
    if ((createMode != Nfs3Constant.CREATE_EXCLUSIVE)
        && request.getObjAttr().getUpdateFields().contains(SetAttrField.SIZE)
        && request.getObjAttr().getSize() != 0) {
      LOG.error("Setting file size is not supported when creating file: {} " +
          "dir fileId: {}", fileName, dirHandle.getFileId());
      return new CREATE3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    HdfsDataOutputStream fos = null;
    String dirFileIdPath = Nfs3Utils.getFileIdPath(dirHandle);
    Nfs3FileAttributes preOpDirAttr = null;
    Nfs3FileAttributes postOpObjAttr = null;
    FileHandle fileHandle = null;
    WccData dirWcc = null;
    try {
      preOpDirAttr = Nfs3Utils.getFileAttr(dfsClient, dirFileIdPath, iug);
      if (preOpDirAttr == null) {
        LOG.error("Can't get path for dirHandle: {}", dirHandle);
        return new CREATE3Response(Nfs3Status.NFS3ERR_STALE);
      }

      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new CREATE3Response(Nfs3Status.NFS3ERR_ACCES, null,
            preOpDirAttr, new WccData(Nfs3Utils.getWccAttr(preOpDirAttr),
                preOpDirAttr));
      }

      String fileIdPath = Nfs3Utils.getFileIdPath(dirHandle) + "/" + fileName;
      SetAttr3 setAttr3 = request.getObjAttr();
      assert (setAttr3 != null);
      FsPermission permission = setAttr3.getUpdateFields().contains(
          SetAttrField.MODE) ? new FsPermission((short) setAttr3.getMode())
          : FsPermission.getDefault().applyUMask(umask);

      EnumSet<CreateFlag> flag = (createMode != Nfs3Constant.CREATE_EXCLUSIVE) ? 
          EnumSet.of(CreateFlag.CREATE, CreateFlag.OVERWRITE) : 
          EnumSet.of(CreateFlag.CREATE);

      fos = dfsClient.createWrappedOutputStream(
          dfsClient.create(fileIdPath, permission, flag, false, replication,
              blockSize, null, bufferSize, null),
          null);

      if ((createMode == Nfs3Constant.CREATE_UNCHECKED)
          || (createMode == Nfs3Constant.CREATE_GUARDED)) {
        // Set group if it's not specified in the request.
        if (!setAttr3.getUpdateFields().contains(SetAttrField.GID)) {
          setAttr3.getUpdateFields().add(SetAttrField.GID);
          setAttr3.setGid(securityHandler.getGid());
        }
        setattrInternal(dfsClient, fileIdPath, setAttr3, false);
      }

      postOpObjAttr = Nfs3Utils.getFileAttr(dfsClient, fileIdPath, iug);
      dirWcc = Nfs3Utils.createWccData(Nfs3Utils.getWccAttr(preOpDirAttr),
          dfsClient, dirFileIdPath, iug);

      // Add open stream
      OpenFileCtx openFileCtx = new OpenFileCtx(fos, postOpObjAttr,
          writeDumpDir + "/" + postOpObjAttr.getFileId(), dfsClient, iug,
          aixCompatMode, config);
      fileHandle = new FileHandle(postOpObjAttr.getFileId(), namenodeId);
      if (!writeManager.addOpenFileStream(fileHandle, openFileCtx)) {
        LOG.warn("Can't add more stream, close it."
            + " Future write will become append");
        fos.close();
        fos = null;
      } else {
        LOG.debug("Opened stream for file: {}, fileId: {}",
            fileName, fileHandle.getFileId());
      }

    } catch (IOException e) {
      LOG.error("Exception", e);
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e1) {
          LOG.error("Can't close stream for dirFileId: {} filename: {}",
              dirHandle.getFileId(), fileName, e1);
        }
      }
      if (dirWcc == null) {
        try {
          dirWcc = Nfs3Utils.createWccData(Nfs3Utils.getWccAttr(preOpDirAttr),
              dfsClient, dirFileIdPath, iug);
        } catch (IOException e1) {
          LOG.error("Can't get postOpDirAttr for dirFileId: {}",
              dirHandle.getFileId(), e1);
        }
      }

      int status = mapErrorStatus(e);
      return new CREATE3Response(status, fileHandle, postOpObjAttr, dirWcc);
    }

    return new CREATE3Response(Nfs3Status.NFS3_OK, fileHandle, postOpObjAttr,
        dirWcc);
  }

};