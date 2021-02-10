//,temp,HttpFSServer.java,482,664,temp,HttpFSServer.java,194,319
//,3
public class xxx {
  @PUT
  @Path("{path:.*}")
  @Consumes({"*/*"})
  @Produces({MediaType.APPLICATION_JSON})
  public Response put(InputStream is,
                       @Context UriInfo uriInfo,
                       @PathParam("path") String path,
                       @QueryParam(OperationParam.NAME) OperationParam op,
                       @Context Parameters params,
                       @Context HttpServletRequest request)
    throws IOException, FileSystemAccessException {
    UserGroupInformation user = HttpUserGroupInformation.get();
    Response response;
    path = makeAbsolute(path);
    MDC.put(HttpFSFileSystem.OP_PARAM, op.value().name());
    MDC.put("hostname", request.getRemoteAddr());
    switch (op.value()) {
      case CREATE: {
        Boolean hasData = params.get(DataParam.NAME, DataParam.class);
        if (!hasData) {
          response = Response.temporaryRedirect(
            createUploadRedirectionURL(uriInfo,
              HttpFSFileSystem.Operation.CREATE)).build();
        } else {
          Short permission = params.get(PermissionParam.NAME,
                                         PermissionParam.class);
          Boolean override = params.get(OverwriteParam.NAME,
                                        OverwriteParam.class);
          Short replication = params.get(ReplicationParam.NAME,
                                         ReplicationParam.class);
          Long blockSize = params.get(BlockSizeParam.NAME,
                                      BlockSizeParam.class);
          FSOperations.FSCreate command =
            new FSOperations.FSCreate(is, path, permission, override,
                                      replication, blockSize);
          fsExecute(user, command);
          AUDIT_LOG.info(
            "[{}] permission [{}] override [{}] replication [{}] blockSize [{}]",
            new Object[]{path, permission, override, replication, blockSize});
          response = Response.status(Response.Status.CREATED).build();
        }
        break;
      }
      case SETXATTR: {
        String xattrName = params.get(XAttrNameParam.NAME, 
            XAttrNameParam.class);
        String xattrValue = params.get(XAttrValueParam.NAME, 
            XAttrValueParam.class);
        EnumSet<XAttrSetFlag> flag = params.get(XAttrSetFlagParam.NAME, 
            XAttrSetFlagParam.class);

        FSOperations.FSSetXAttr command = new FSOperations.FSSetXAttr(
            path, xattrName, xattrValue, flag);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] to xAttr [{}]", path, xattrName);
        response = Response.ok().build();
        break;
      }
      case REMOVEXATTR: {
        String xattrName = params.get(XAttrNameParam.NAME, XAttrNameParam.class);
        FSOperations.FSRemoveXAttr command = new FSOperations.FSRemoveXAttr(
            path, xattrName);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] removed xAttr [{}]", path, xattrName);
        response = Response.ok().build();
        break;
      }
      case MKDIRS: {
        Short permission = params.get(PermissionParam.NAME,
                                       PermissionParam.class);
        FSOperations.FSMkdirs command =
          new FSOperations.FSMkdirs(path, permission);
        JSONObject json = fsExecute(user, command);
        AUDIT_LOG.info("[{}] permission [{}]", path, permission);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case RENAME: {
        String toPath = params.get(DestinationParam.NAME, DestinationParam.class);
        FSOperations.FSRename command =
          new FSOperations.FSRename(path, toPath);
        JSONObject json = fsExecute(user, command);
        AUDIT_LOG.info("[{}] to [{}]", path, toPath);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case SETOWNER: {
        String owner = params.get(OwnerParam.NAME, OwnerParam.class);
        String group = params.get(GroupParam.NAME, GroupParam.class);
        FSOperations.FSSetOwner command =
          new FSOperations.FSSetOwner(path, owner, group);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] to (O/G)[{}]", path, owner + ":" + group);
        response = Response.ok().build();
        break;
      }
      case SETPERMISSION: {
        Short permission = params.get(PermissionParam.NAME,
                                      PermissionParam.class);
        FSOperations.FSSetPermission command =
          new FSOperations.FSSetPermission(path, permission);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] to [{}]", path, permission);
        response = Response.ok().build();
        break;
      }
      case SETREPLICATION: {
        Short replication = params.get(ReplicationParam.NAME,
                                       ReplicationParam.class);
        FSOperations.FSSetReplication command =
          new FSOperations.FSSetReplication(path, replication);
        JSONObject json = fsExecute(user, command);
        AUDIT_LOG.info("[{}] to [{}]", path, replication);
        response = Response.ok(json).build();
        break;
      }
      case SETTIMES: {
        Long modifiedTime = params.get(ModifiedTimeParam.NAME,
                                       ModifiedTimeParam.class);
        Long accessTime = params.get(AccessTimeParam.NAME,
                                     AccessTimeParam.class);
        FSOperations.FSSetTimes command =
          new FSOperations.FSSetTimes(path, modifiedTime, accessTime);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] to (M/A)[{}]", path,
                       modifiedTime + ":" + accessTime);
        response = Response.ok().build();
        break;
      }
      case SETACL: {
        String aclSpec = params.get(AclPermissionParam.NAME,
                AclPermissionParam.class);
        FSOperations.FSSetAcl command =
                new FSOperations.FSSetAcl(path, aclSpec);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] to acl [{}]", path, aclSpec);
        response = Response.ok().build();
        break;
      }
      case REMOVEACL: {
        FSOperations.FSRemoveAcl command =
                new FSOperations.FSRemoveAcl(path);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] removed acl", path);
        response = Response.ok().build();
        break;
      }
      case MODIFYACLENTRIES: {
        String aclSpec = params.get(AclPermissionParam.NAME,
                AclPermissionParam.class);
        FSOperations.FSModifyAclEntries command =
                new FSOperations.FSModifyAclEntries(path, aclSpec);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] modify acl entry with [{}]", path, aclSpec);
        response = Response.ok().build();
        break;
      }
      case REMOVEACLENTRIES: {
        String aclSpec = params.get(AclPermissionParam.NAME,
                AclPermissionParam.class);
        FSOperations.FSRemoveAclEntries command =
                new FSOperations.FSRemoveAclEntries(path, aclSpec);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] remove acl entry [{}]", path, aclSpec);
        response = Response.ok().build();
        break;
      }
      case REMOVEDEFAULTACL: {
        FSOperations.FSRemoveDefaultAcl command =
                new FSOperations.FSRemoveDefaultAcl(path);
        fsExecute(user, command);
        AUDIT_LOG.info("[{}] remove default acl", path);
        response = Response.ok().build();
        break;
      }
      default: {
        throw new IOException(
          MessageFormat.format("Invalid HTTP PUT operation [{0}]",
                               op.value()));
      }
    }
    return response;
  }

};