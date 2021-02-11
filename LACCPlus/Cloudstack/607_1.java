//,temp,EngineHostDaoImpl.java,419,460,temp,VMTemplateDaoImpl.java,1043,1102
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, DataCenterResourceEntity.State.Event event, State nextState, DataCenterResourceEntity hostEntity, Object data) {
        EngineHostVO vo = findById(hostEntity.getId());
        Date oldUpdatedTime = vo.getLastUpdated();

        SearchCriteria<EngineHostVO> sc = StateChangeSearch.create();
        sc.setParameters("id", hostEntity.getId());
        sc.setParameters("state", currentState);

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "lastUpdated", new Date());

        int rows = update(vo, sc);

        if (rows == 0 && s_logger.isDebugEnabled()) {
            EngineHostVO dbHost = findByIdIncludingRemoved(vo.getId());
            if (dbHost != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                str.append(": DB Data={id=").append(dbHost.getId()).append("; state=").append(dbHost.getState()).append(";updatedTime=").append(dbHost.getLastUpdated());
                str.append(": New Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatedTime=")
                    .append(vo.getLastUpdated());
                str.append(": stale Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update dataCenter: id=" + vo.getId() + ", as there is no such dataCenter exists in the database anymore");
            }
        }
        return rows > 0;
    }

};