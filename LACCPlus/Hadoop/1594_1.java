//,temp,RpcProgramNfs3.java,1993,2040,temp,RpcProgramNfs3.java,1928,1986
//,3
public class xxx {
  @VisibleForTesting
  PATHCONF3Response pathconf(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    PATHCONF3Response response = new PATHCONF3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    DFSClient dfsClient = clientCache.getDfsClient(securityHandler.getUser());
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    PATHCONF3Request request;
    try {
      request = PATHCONF3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid PATHCONF request");
      return new PATHCONF3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    Nfs3FileAttributes attrs;

    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS PATHCONF fileId: " + handle.getFileId() + " client: "
          + remoteAddress);
    }

    try {
      attrs = Nfs3Utils.getFileAttr(dfsClient, Nfs3Utils.getFileIdPath(handle),
          iug);
      if (attrs == null) {
        LOG.info("Can't get path for fileId: " + handle.getFileId());
        return new PATHCONF3Response(Nfs3Status.NFS3ERR_STALE);
      }

      return new PATHCONF3Response(Nfs3Status.NFS3_OK, attrs, 0,
          HdfsServerConstants.MAX_PATH_LENGTH, true, false, false, true);
    } catch (IOException e) {
      LOG.warn("Exception ", e);
      int status = mapErrorStatus(e);
      return new PATHCONF3Response(status);
    }
  }

};