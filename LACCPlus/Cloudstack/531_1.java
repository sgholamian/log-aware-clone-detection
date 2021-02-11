//,temp,VMTemplateHostDaoImpl.java,374,429,temp,TemplateDataStoreDaoImpl.java,165,223
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, Event event, State nextState, DataObjectInStore vo, Object data) {
        VMTemplateHostVO templateHost = (VMTemplateHostVO)vo;
        Long oldUpdated = templateHost.getUpdatedCount();
        Date oldUpdatedTime = templateHost.getUpdated();

        SearchCriteria<VMTemplateHostVO> sc = updateStateSearch.create();
        sc.setParameters("id", templateHost.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", templateHost.getUpdatedCount());

        templateHost.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "updated", new Date());

        int rows = update((VMTemplateHostVO)vo, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            VMTemplateHostVO dbVol = findByIdIncludingRemoved(templateHost.getId());
            if (dbVol != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                str.append(": DB Data={id=")
                    .append(dbVol.getId())
                    .append("; state=")
                    .append(dbVol.getState())
                    .append("; updatecount=")
                    .append(dbVol.getUpdatedCount())
                    .append(";updatedTime=")
                    .append(dbVol.getUpdated());
                str.append(": New Data={id=")
                    .append(templateHost.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(templateHost.getUpdatedCount())
                    .append("; updatedTime=")
                    .append(templateHost.getUpdated());
                str.append(": stale Data={id=")
                    .append(templateHost.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(oldUpdated)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update objectIndatastore: id=" + templateHost.getId() + ", as there is no such object exists in the database anymore");
            }
        }
        return rows > 0;
    }

};