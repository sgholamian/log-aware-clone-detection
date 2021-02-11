//,temp,RpcProgramNfs3.java,1853,1921,temp,RpcProgramNfs3.java,309,367
//,3
public class xxx {
  @VisibleForTesting
  GETATTR3Response getattr(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    GETATTR3Response response = new GETATTR3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    DFSClient dfsClient = clientCache.getDfsClient(securityHandler.getUser());
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    GETATTR3Request request;
    try {
      request = GETATTR3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid GETATTR request");
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    FileHandle handle = request.getHandle();
    if (LOG.isDebugEnabled()) {
      LOG.debug("GETATTR for fileId: " + handle.getFileId() + " client: "
          + remoteAddress);
    }

    Nfs3FileAttributes attrs = null;
    try {
      attrs = writeManager.getFileAttr(dfsClient, handle, iug);
    } catch (RemoteException r) {
      LOG.warn("Exception ", r);
      IOException io = r.unwrapRemoteException();
      /**
       * AuthorizationException can be thrown if the user can't be proxy'ed.
       */
      if (io instanceof AuthorizationException) {
        return new GETATTR3Response(Nfs3Status.NFS3ERR_ACCES);
      } else {
        return new GETATTR3Response(Nfs3Status.NFS3ERR_IO);
      }
    } catch (IOException e) {
      LOG.info("Can't get file attribute, fileId=" + handle.getFileId(), e);
      int status = mapErrorStatus(e);
      response.setStatus(status);
      return response;
    }
    if (attrs == null) {
      LOG.error("Can't get path for fileId: " + handle.getFileId());
      response.setStatus(Nfs3Status.NFS3ERR_STALE);
      return response;
    }
    response.setPostOpAttr(attrs);
    return response;
  }

};