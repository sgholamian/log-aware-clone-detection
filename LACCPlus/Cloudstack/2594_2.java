//,temp,ServerEventHandlerImpl.java,208,230,temp,ServerEventHandlerImpl.java,185,206
//,3
public class xxx {
    public void onDomainCreate(String subject, String topic, org.apache.cloudstack.framework.events.Event event) {
        s_logger.info("onDomainCreate; topic: " + topic + "; subject: " + subject);
        try {
            long id = parseForId(event.getResourceType(), event.getDescription());
            if (id != 0) {
                DomainVO domain = _domainDao.findById(id);
                if (domain != null) {
                    s_logger.info("createDomain for name: " + domain.getName() + "; uuid: " + domain.getUuid());
                    StringBuffer logMesg = new StringBuffer();
                    _dbSync.createDomain(domain, logMesg);
                } else {
                    /* could not find db record, resync complete class */
                    _dbSync.syncClass(net.juniper.contrail.api.types.Domain.class);
                }
            } else {
                /* Unknown id, resync complete class */
                _dbSync.syncClass(net.juniper.contrail.api.types.Domain.class);
            }
        } catch (Exception e) {
            s_logger.debug(e);
        }
    }

};