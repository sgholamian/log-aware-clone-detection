//,temp,RpcProgramNfs3.java,629,697,temp,RpcProgramNfs3.java,310,369
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

    GETATTR3Request request;
    try {
      request = GETATTR3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid GETATTR request");
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();
    if (LOG.isDebugEnabled()) {
      LOG.debug("GETATTR for fileHandle: {} client: {}",
          handle.dumpFileHandle(), remoteAddress);
    }
    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    Nfs3FileAttributes attrs = null;
    try {
      attrs = writeManager.getFileAttr(dfsClient, handle, iug);
    } catch (RemoteException r) {
      LOG.warn("Exception", r);
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
      LOG.info("Can't get file attribute, fileId={}", handle.getFileId(), e);
      int status = mapErrorStatus(e);
      response.setStatus(status);
      return response;
    }
    if (attrs == null) {
      LOG.error("Can't get path for fileId: {}", handle.getFileId());
      response.setStatus(Nfs3Status.NFS3ERR_STALE);
      return response;
    }
    response.setPostOpAttr(attrs);
    return response;
  }

};