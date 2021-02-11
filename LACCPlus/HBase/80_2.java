//,temp,TestRSGroupsBase.java,174,182,temp,IntegrationTestRSGroup.java,91,98
//,3
public class xxx {
      @Override
      public boolean evaluate() throws Exception {
        LOG.info("Waiting for regionservers to be registered "+ rsGroupAdmin.listRSGroups());
        //Might be greater since moving servers back to default
        //is after starting a server
        return rsGroupAdmin.getRSGroupInfo(RSGroupInfo.DEFAULT_GROUP).getServers().size()
            == getNumServers();
      }

};