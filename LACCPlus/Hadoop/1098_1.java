//,temp,RpcProgramNfs3.java,845,921,temp,RpcProgramNfs3.java,488,549
//,3
public class xxx {
  @VisibleForTesting
  WRITE3Response write(XDR xdr, Channel channel, int xid,
      SecurityHandler securityHandler, SocketAddress remoteAddress) {
    WRITE3Response response = new WRITE3Response(Nfs3Status.NFS3_OK);

    DFSClient dfsClient = clientCache.getDfsClient(securityHandler.getUser());
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    WRITE3Request request;

    try {
      request = WRITE3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid WRITE request");
      return new WRITE3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    long offset = request.getOffset();
    int count = request.getCount();
    WriteStableHow stableHow = request.getStableHow();
    byte[] data = request.getData().array();
    if (data.length < count) {
      LOG.error("Invalid argument, data size is less than count in request");
      return new WRITE3Response(Nfs3Status.NFS3ERR_INVAL);
    }

    FileHandle handle = request.getHandle();
    if (LOG.isDebugEnabled()) {
      LOG.debug("NFS WRITE fileId: " + handle.getFileId() + " offset: "
          + offset + " length: " + count + " stableHow: " + stableHow.getValue()
          + " xid: " + xid + " client: " + remoteAddress);
    }

    Nfs3FileAttributes preOpAttr = null;
    try {
      preOpAttr = writeManager.getFileAttr(dfsClient, handle, iug);
      if (preOpAttr == null) {
        LOG.error("Can't get path for fileId: " + handle.getFileId());
        return new WRITE3Response(Nfs3Status.NFS3ERR_STALE);
      }

      if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
        return new WRITE3Response(Nfs3Status.NFS3ERR_ACCES, new WccData(
            Nfs3Utils.getWccAttr(preOpAttr), preOpAttr), 0, stableHow,
            Nfs3Constant.WRITE_COMMIT_VERF);
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("requested offset=" + offset + " and current filesize="
            + preOpAttr.getSize());
      }

      writeManager.handleWrite(dfsClient, request, channel, xid, preOpAttr);

    } catch (IOException e) {
      LOG.info("Error writing to fileId " + handle.getFileId() + " at offset "
          + offset + " and length " + data.length, e);
      // Try to return WccData
      Nfs3FileAttributes postOpAttr = null;
      try {
        postOpAttr = writeManager.getFileAttr(dfsClient, handle, iug);
      } catch (IOException e1) {
        LOG.info("Can't get postOpAttr for fileId: " + handle.getFileId(), e1);
      }
      WccAttr attr = preOpAttr == null ? null : Nfs3Utils.getWccAttr(preOpAttr);
      WccData fileWcc = new WccData(attr, postOpAttr);

      int status = mapErrorStatus(e);
      return new WRITE3Response(status, fileWcc, 0, request.getStableHow(),
          Nfs3Constant.WRITE_COMMIT_VERF);
    }

    return null;
  }

};