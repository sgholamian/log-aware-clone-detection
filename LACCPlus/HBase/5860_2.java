//,temp,MasterRpcServices.java,1137,1162,temp,MasterRpcServices.java,917,939
//,3
public class xxx {
  @Override
  public ExecProcedureResponse execProcedureWithRet(RpcController controller,
      ExecProcedureRequest request) throws ServiceException {
    rpcPreCheck("execProcedureWithRet");
    try {
      ProcedureDescription desc = request.getProcedure();
      MasterProcedureManager mpm =
        master.getMasterProcedureManagerHost().getProcedureManager(desc.getSignature());
      if (mpm == null) {
        throw new ServiceException("The procedure is not registered: " + desc.getSignature());
      }
      LOG.info(master.getClientIdAuditPrefix() + " procedure request for: " + desc.getSignature());
      byte[] data = mpm.execProcedureWithRet(desc);
      ExecProcedureResponse.Builder builder = ExecProcedureResponse.newBuilder();
      // set return data if available
      if (data != null) {
        builder.setReturnData(UnsafeByteOperations.unsafeWrap(data));
      }
      return builder.build();
    } catch (IOException e) {
      throw new ServiceException(e);
    }
  }

};