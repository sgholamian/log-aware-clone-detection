//,temp,DomainManagerImpl.java,344,349,temp,ProjectManagerImpl.java,722,726
//,3
public class xxx {
    protected void rollbackDomainState(DomainVO domain) {
        s_logger.debug("Changing domain id=" + domain.getId() + " state back to " + Domain.State.Active +
                " because it can't be removed due to resources referencing to it");
        domain.setState(Domain.State.Active);
        _domainDao.update(domain.getId(), domain);
    }

};