//,temp,RSGroupAdminEndpoint.java,400,425,temp,RSGroupAdminEndpoint.java,256,274
//,3
public class xxx {
    @Override
    public void removeServers(RpcController controller,
        RemoveServersRequest request,
        RpcCallback<RemoveServersResponse> done) {
      RemoveServersResponse.Builder builder =
          RemoveServersResponse.newBuilder();
      Set<Address> servers = Sets.newHashSet();
      for (HBaseProtos.ServerName el : request.getServersList()) {
        servers.add(Address.fromParts(el.getHostName(), el.getPort()));
      }
      LOG.info(master.getClientIdAuditPrefix()
          + " remove decommissioned servers from rsgroup: " + servers);
      try {
        if (master.getMasterCoprocessorHost() != null) {
          master.getMasterCoprocessorHost().preRemoveServers(servers);
        }
        checkPermission("removeServers");
        groupAdminServer.removeServers(servers);
        if (master.getMasterCoprocessorHost() != null) {
          master.getMasterCoprocessorHost().postRemoveServers(servers);
        }
      } catch (IOException e) {
        CoprocessorRpcUtils.setControllerException(controller, e);
      }
      done.run(builder.build());
    }

};