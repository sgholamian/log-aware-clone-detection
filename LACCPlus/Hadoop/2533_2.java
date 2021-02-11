//,temp,RpcProgramNfs3.java,2077,2141,temp,RpcProgramNfs3.java,1432,1493
//,3
public class xxx {
  @VisibleForTesting
  SYMLINK3Response symlink(XDR xdr, SecurityHandler securityHandler,
      SocketAddress remoteAddress) {
    SYMLINK3Response response = new SYMLINK3Response(Nfs3Status.NFS3_OK);

    if (!checkAccessPrivilege(remoteAddress, AccessPrivilege.READ_WRITE)) {
      response.setStatus(Nfs3Status.NFS3ERR_ACCES);
      return response;
    }

    SYMLINK3Request request;
    try {
      request = SYMLINK3Request.deserialize(xdr);
    } catch (IOException e) {
      LOG.error("Invalid SYMLINK request");
      response.setStatus(Nfs3Status.NFS3ERR_INVAL);
      return response;
    }

    FileHandle dirHandle = request.getHandle();
    String name = request.getName();
    String symData = request.getSymData();
    String linkDirIdPath = Nfs3Utils.getFileIdPath(dirHandle);
    int namenodeId = dirHandle.getNamenodeId();

    // Don't do any name check to source path, just leave it to HDFS
    String linkIdPath = linkDirIdPath + "/" + name;
    LOG.debug("NFS SYMLINK, target: {} link: {} namenodeId: {} client: {}",
        symData, linkIdPath, namenodeId, remoteAddress);

    DFSClient dfsClient =
        clientCache.getDfsClient(securityHandler.getUser(), namenodeId);
    if (dfsClient == null) {
      response.setStatus(Nfs3Status.NFS3ERR_SERVERFAULT);
      return response;
    }

    try {
      WccData dirWcc = response.getDirWcc();
      WccAttr preOpAttr = Nfs3Utils.getWccAttr(dfsClient, linkDirIdPath);
      dirWcc.setPreOpAttr(preOpAttr);

      dfsClient.createSymlink(symData, linkIdPath, false);
      // Set symlink attr is considered as to change the attr of the target
      // file. So no need to set symlink attr here after it's created.

      HdfsFileStatus linkstat = dfsClient.getFileLinkInfo(linkIdPath);
      Nfs3FileAttributes objAttr = Nfs3Utils.getNfs3FileAttrFromFileStatus(
          linkstat, iug);
      dirWcc
          .setPostOpAttr(Nfs3Utils.getFileAttr(dfsClient, linkDirIdPath, iug));

      return new SYMLINK3Response(Nfs3Status.NFS3_OK, new FileHandle(
          objAttr.getFileId(), namenodeId), objAttr, dirWcc);

    } catch (IOException e) {
      LOG.warn("Exception", e);
      int status = mapErrorStatus(e);
      response.setStatus(status);
      return response;
    }
  }

};