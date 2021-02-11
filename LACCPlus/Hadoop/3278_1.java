//,temp,RpcProgramNfs3.java,1952,2011,temp,RpcProgramNfs3.java,555,622
//,3
public class xxx {
  @VisibleForTesting
  FSINFO3Response fsinfo(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    FSINFO3Response response = new FSINFO3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    FSINFO3Request request;
    try {
      request = FSINFO3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid FSINFO request");
      return new FSINFO3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS FSINFO fileHandle: {} client: {}", remoteAddress,
          handle.dumpFileHandle(), remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    try {
      int rtmax = config.getInt(
          NfsConfigKeys.DFS_NFS_MAX_READ_TRANSFER_SIZE_KEY,
          NfsConfigKeys.DFS_NFS_MAX_READ_TRANSFER_SIZE_DEFAULT);
      int wtmax = config.getInt(
          NfsConfigKeys.DFS_NFS_MAX_WRITE_TRANSFER_SIZE_KEY,
          NfsConfigKeys.DFS_NFS_MAX_WRITE_TRANSFER_SIZE_DEFAULT);
      int dtperf = config.getInt(
          NfsConfigKeys.DFS_NFS_MAX_READDIR_TRANSFER_SIZE_KEY,
          NfsConfigKeys.DFS_NFS_MAX_READDIR_TRANSFER_SIZE_DEFAULT);

      Nfs3FileAttributes attrs = Nfs3Utils.getFileAttr(dfsClient,
          Nfs3Utils.getFileIdPath(handle), iug);
      if (attrs == null) {
        LOG.info("Can't get path for fileId: {}", handle.getFileId());
        return new FSINFO3Response(Nfs3Status.NFS3ERR_STALE);
      }

      int fsProperty = Nfs3Constant.FSF3_CANSETTIME
          | Nfs3Constant.FSF3_HOMOGENEOUS;

      return new FSINFO3Response(Nfs3Status.NFS3_OK, attrs, rtmax, rtmax, 1,
          wtmax, wtmax, 1, dtperf, Long.MAX_VALUE, new NfsTime(1), fsProperty);
    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      return new FSINFO3Response(status);
    }
  }

};