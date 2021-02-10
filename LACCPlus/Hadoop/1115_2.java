//,temp,HttpFSServer.java,482,664,temp,HttpFSServer.java,194,319
//,3
public class xxx {
  @GET
  @Path("{path:.*}")
  @Produces({MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON})
  public Response get(@PathParam("path") String path,
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
      case OPEN: {
        //Invoking the command directly using an unmanaged FileSystem that is
        // released by the FileSystemReleaseFilter
        FSOperations.FSOpen command = new FSOperations.FSOpen(path);
        FileSystem fs = createFileSystem(user);
        InputStream is = command.execute(fs);
        Long offset = params.get(OffsetParam.NAME, OffsetParam.class);
        Long len = params.get(LenParam.NAME, LenParam.class);
        AUDIT_LOG.info("[{}] offset [{}] len [{}]",
                       new Object[]{path, offset, len});
        InputStreamEntity entity = new InputStreamEntity(is, offset, len);
        response =
          Response.ok(entity).type(MediaType.APPLICATION_OCTET_STREAM).build();
        break;
      }
      case GETFILESTATUS: {
        FSOperations.FSFileStatus command =
          new FSOperations.FSFileStatus(path);
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("[{}]", path);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case LISTSTATUS: {
        String filter = params.get(FilterParam.NAME, FilterParam.class);
        FSOperations.FSListStatus command = new FSOperations.FSListStatus(
          path, filter);
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("[{}] filter [{}]", path,
                       (filter != null) ? filter : "-");
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case GETHOMEDIRECTORY: {
        enforceRootPath(op.value(), path);
        FSOperations.FSHomeDir command = new FSOperations.FSHomeDir();
        JSONObject json = fsExecute(user, command);
        AUDIT_LOG.info("");
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case INSTRUMENTATION: {
        enforceRootPath(op.value(), path);
        Groups groups = HttpFSServerWebApp.get().get(Groups.class);
        List<String> userGroups = groups.getGroups(user.getShortUserName());
        if (!userGroups.contains(HttpFSServerWebApp.get().getAdminGroup())) {
          throw new AccessControlException(
            "User not in HttpFSServer admin group");
        }
        Instrumentation instrumentation =
          HttpFSServerWebApp.get().get(Instrumentation.class);
        Map snapshot = instrumentation.getSnapshot();
        response = Response.ok(snapshot).build();
        break;
      }
      case GETCONTENTSUMMARY: {
        FSOperations.FSContentSummary command =
          new FSOperations.FSContentSummary(path);
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("[{}]", path);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case GETFILECHECKSUM: {
        FSOperations.FSFileChecksum command =
          new FSOperations.FSFileChecksum(path);
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("[{}]", path);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case GETFILEBLOCKLOCATIONS: {
        response = Response.status(Response.Status.BAD_REQUEST).build();
        break;
      }
      case GETACLSTATUS: {
        FSOperations.FSAclStatus command =
                new FSOperations.FSAclStatus(path);
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("ACL status for [{}]", path);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case GETXATTRS: {
        List<String> xattrNames = params.getValues(XAttrNameParam.NAME, 
            XAttrNameParam.class);
        XAttrCodec encoding = params.get(XAttrEncodingParam.NAME, 
            XAttrEncodingParam.class);
        FSOperations.FSGetXAttrs command = new FSOperations.FSGetXAttrs(path, 
            xattrNames, encoding);
        @SuppressWarnings("rawtypes")
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("XAttrs for [{}]", path);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      case LISTXATTRS: {
        FSOperations.FSListXAttrs command = new FSOperations.FSListXAttrs(path);
        @SuppressWarnings("rawtypes")
        Map json = fsExecute(user, command);
        AUDIT_LOG.info("XAttr names for [{}]", path);
        response = Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        break;
      }
      default: {
        throw new IOException(
          MessageFormat.format("Invalid HTTP GET operation [{0}]",
                               op.value()));
      }
    }
    return response;
  }

};