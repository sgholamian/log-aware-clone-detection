//,temp,EngineHostDaoImpl.java,419,460,temp,HostDaoImpl.java,1014,1054
//,3
public class xxx {
    @Override
    public boolean updateResourceState(ResourceState oldState, ResourceState.Event event, ResourceState newState, Host vo) {
        HostVO host = (HostVO)vo;
        SearchBuilder<HostVO> sb = createSearchBuilder();
        sb.and("resource_state", sb.entity().getResourceState(), SearchCriteria.Op.EQ);
        sb.and("id", sb.entity().getId(), SearchCriteria.Op.EQ);
        sb.done();

        SearchCriteria<HostVO> sc = sb.create();

        sc.setParameters("resource_state", oldState);
        sc.setParameters("id", host.getId());

        UpdateBuilder ub = getUpdateBuilder(host);
        ub.set(host, _resourceStateAttr, newState);
        int result = update(ub, sc, null);
        assert result <= 1 : "How can this update " + result + " rows? ";

        if (state_logger.isDebugEnabled() && result == 0) {
            HostVO ho = findById(host.getId());
            assert ho != null : "How how how? : " + host.getId();

            StringBuilder str = new StringBuilder("Unable to update resource state: [");
            str.append("m = " + host.getId());
            str.append("; name = " + host.getName());
            str.append("; old state = " + oldState);
            str.append("; event = " + event);
            str.append("; new state = " + newState + "]");
            state_logger.debug(str.toString());
        } else {
            StringBuilder msg = new StringBuilder("Resource state update: [");
            msg.append("id = " + host.getId());
            msg.append("; name = " + host.getName());
            msg.append("; old state = " + oldState);
            msg.append("; event = " + event);
            msg.append("; new state = " + newState + "]");
            state_logger.debug(msg.toString());
        }

        return result > 0;
    }

};