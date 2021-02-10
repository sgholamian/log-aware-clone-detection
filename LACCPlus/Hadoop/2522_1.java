//,temp,RpcProgramNfs3.java,555,622,temp,RpcProgramNfs3.java,488,548
//,3
public class xxx {
  @VisibleForTesting
  ACCESS3Response access(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    ACCESS3Response response = new ACCESS3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_ONLY)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    ACCESS3Request request;
    try {
      request = ACCESS3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid ACCESS request");
      return new ACCESS3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    int namenodeId = handle.getNamenodeId();

    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS ACCESS fileHandle: {} client: {}",
          handle.dumpFileHandle(), remoteAddress);
    }
    Nfs3FileAttributes attrs;
    try {
      attrs = writeManager.getFileAttr(dfsClient, handle, iug);

      if (attrs == null) {
        LOG.error("Can't get path for fileId: {}", handle.getFileId());
        return new ACCESS3Response(Nfs3Status.NFS3ERR_STALE);
      }
      if(iug.getUserName(securityHandler.getUid(), "unknown").equals(superuser)) {
        int access = Nfs3Constant.ACCESS3_LOOKUP | Nfs3Constant.ACCESS3_DELETE
            | Nfs3Constant.ACCESS3_EXECUTE | Nfs3Constant.ACCESS3_EXTEND
            | Nfs3Constant.ACCESS3_MODIFY | Nfs3Constant.ACCESS3_READ;
        return new ACCESS3Response(Nfs3Status.NFS3_OK, attrs, access);
      }
      int access = Nfs3Utils.getAccessRightsForUserGroup(
          securityHandler.getUid(), securityHandler.getGid(),
          securityHandler.getAuxGids(), attrs);

      return new ACCESS3Response(Nfs3Status.NFS3_OK, attrs, access);
    } catch (RemoteException r) {
      LOG.warn("Exception", r);
      IOException io = r.unwrapRemoteException();
      /**
       * AuthorizationException can be thrown if the user can't be proxy'ed.
       */
      if (io instanceof AuthorizationException) {
        return new ACCESS3Response(Nfs3Status.NFS3ERR_ACCES);
      } else {
        return new ACCESS3Response(Nfs3Status.NFS3ERR_IO);
      }
    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      return new ACCESS3Response(status);
    }
  }

};