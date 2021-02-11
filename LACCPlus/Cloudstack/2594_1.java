//,temp,ServerEventHandlerImpl.java,208,230,temp,ServerEventHandlerImpl.java,185,206
//,3
public class xxx {
    public void onProjectCreate(String subject, String topic, org.apache.cloudstack.framework.events.Event event) {
        s_logger.info("onProjectCreate; topic: " + topic + "; subject: " + subject);
        try {
            long id = parseForId(event.getResourceType(), event.getDescription());
            if (id != 0) {
                ProjectVO project = _projectDao.findById(id);
                if (project != null) {
                    s_logger.info("createProject for name: " + project.getName() + "; uuid: " + project.getUuid());
                    StringBuffer logMesg = new StringBuffer();
                    _dbSync.createProject(project, logMesg);
                } else {
                    /* could not find db record, resync complete class */
                    _dbSync.syncClass(net.juniper.contrail.api.types.Project.class);
                }
            } else {
                /* Unknown id, resync complete class */
                _dbSync.syncClass(net.juniper.contrail.api.types.Project.class);
            }
        } catch (Exception e) {
            s_logger.info(e);
        }

    }

};