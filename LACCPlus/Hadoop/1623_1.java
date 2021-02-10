//,temp,RpcProgramNfs3.java,629,697,temp,RpcProgramNfs3.java,488,549
//,3
public class xxx {
  @VisibleForTesting
  READLINK3Response readlink(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    READLINK3Response response = new READLINK3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    DFSClient dfsClient = clientCache.getDfsClient(securityHandler.getUser());
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    READLINK3Request request;

    try {
      request = READLINK3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid READLINK request");
      return new READLINK3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS READLINK fileId: " + handle.getFileId() + " client: "
          + remoteAddress);
    }

    String fileIdPath = Nfs3Utils.getFileIdPath(handle);
    try {
      String target = dfsClient.getLinkTarget(fileIdPath);

      Nfs3FileAttributes postOpAttr = Nfs3Utils.getFileAttr(dfsClient,
          fileIdPath, iug);
      if (postOpAttr == null) {
        LOG.info("Can't get path for fileId: " + handle.getFileId());
        return new READLINK3Response(Nfs3Status.NFS3ERR_STALE);
      }
      if (postOpAttr.getType() != NfsFileType.NFSLNK.toValue()) {
        LOG.error("Not a symlink, fileId: " + handle.getFileId());
        return new READLINK3Response(Nfs3Status.NFS3ERR_INVAL);
      }
      if (target == null) {
        LOG.error("Symlink target should not be null, fileId: "
            + handle.getFileId());
        return new READLINK3Response(Nfs3Status.NFS3ERR_SERVERFAULT);
      }
      int rtmax = config.getInt(NfsConfigKeys.DFS_NFS_MAX_READ_TRANSFER_SIZE_KEY,
          NfsConfigKeys.DFS_NFS_MAX_READ_TRANSFER_SIZE_DEFAULT);
      if (rtmax < target.getBytes(Charset.forName("UTF-8")).length) {
        LOG.error("Link size: "
            + target.getBytes(Charset.forName("UTF-8")).length
            + " is larger than max transfer size: " + rtmax);
        return new READLINK3Response(Nfs3Status.NFS3ERR_IO, postOpAttr,
            new byte[0]);
      }

      return new READLINK3Response(Nfs3Status.NFS3_OK, postOpAttr,
          target.getBytes(Charset.forName("UTF-8")));

    } catch (IOException e) {
      LOG.warn("Readlink error: " + e.getClass(), e);
      int status = mapErrorStatus(e);
      return new READLINK3Response(status);
    }
  }

};