//,temp,VMSnapshotDaoImpl.java,128,183,temp,VMTemplateDaoImpl.java,1043,1102
//,3
public class xxx {
    @Override
    public boolean updateState(
            com.cloud.template.VirtualMachineTemplate.State currentState,
            com.cloud.template.VirtualMachineTemplate.Event event,
            com.cloud.template.VirtualMachineTemplate.State nextState,
            VirtualMachineTemplate vo, Object data) {

        Long oldUpdated = vo.getUpdatedCount();
        Date oldUpdatedTime = vo.getUpdated();

        SearchCriteria<VMTemplateVO> sc = AllFieldsSearch.create();
        sc.setParameters("id", vo.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", vo.getUpdatedCount());

        vo.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "updated", new Date());

        int rows = update((VMTemplateVO)vo, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            VMTemplateVO dbTemplate = findByIdIncludingRemoved(vo.getId());
            if (dbTemplate != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                str.append(": DB Data={id=")
                    .append(dbTemplate.getId())
                    .append("; state=")
                    .append(dbTemplate.getState())
                    .append("; updatecount=")
                    .append(dbTemplate.getUpdatedCount())
                    .append(";updatedTime=")
                    .append(dbTemplate.getUpdated());
                str.append(": New Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(vo.getUpdatedCount())
                    .append("; updatedTime=")
                    .append(vo.getUpdated());
                str.append(": stale Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(oldUpdated)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update template: id=" + vo.getId() + ", as no such template exists in the database anymore");
            }
        }
        return rows > 0;
    }

};