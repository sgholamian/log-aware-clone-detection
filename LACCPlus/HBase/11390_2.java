//,temp,RSGroupAdminEndpoint.java,400,425,temp,RSGroupAdminEndpoint.java,256,274
//,3
public class xxx {
    @Override
    public void addRSGroup(RpcController controller, AddRSGroupRequest request,
        RpcCallback<AddRSGroupResponse> done) {
      AddRSGroupResponse.Builder builder = AddRSGroupResponse.newBuilder();
      LOG.info(master.getClientIdAuditPrefix() + " add rsgroup " + request.getRSGroupName());
      try {
        if (master.getMasterCoprocessorHost() != null) {
          master.getMasterCoprocessorHost().preAddRSGroup(request.getRSGroupName());
        }
        checkPermission("addRSGroup");
        groupAdminServer.addRSGroup(request.getRSGroupName());
        if (master.getMasterCoprocessorHost() != null) {
          master.getMasterCoprocessorHost().postAddRSGroup(request.getRSGroupName());
        }
      } catch (IOException e) {
        CoprocessorRpcUtils.setControllerException(controller, e);
      }
      done.run(builder.build());
    }

};