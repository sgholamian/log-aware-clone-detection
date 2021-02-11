//,temp,VolumeDataStoreDaoImpl.java,124,182,temp,VMTemplatePoolDaoImpl.java,263,318
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, Event event, State nextState, DataObjectInStore vo, Object data) {
        VMTemplateStoragePoolVO templatePool = (VMTemplateStoragePoolVO)vo;
        Long oldUpdated = templatePool.getUpdatedCount();
        Date oldUpdatedTime = templatePool.getUpdated();

        SearchCriteria<VMTemplateStoragePoolVO> sc = updateStateSearch.create();
        sc.setParameters("id", templatePool.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", templatePool.getUpdatedCount());

        templatePool.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "updated", new Date());

        int rows = update((VMTemplateStoragePoolVO)vo, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            VMTemplateStoragePoolVO dbVol = findByIdIncludingRemoved(templatePool.getId());
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
                    .append(templatePool.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(templatePool.getUpdatedCount())
                    .append("; updatedTime=")
                    .append(templatePool.getUpdated());
                str.append(": stale Data={id=")
                    .append(templatePool.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(oldUpdated)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update objectIndatastore: id=" + templatePool.getId() + ", as there is no such object exists in the database anymore");
            }
        }
        return rows > 0;
    }

};