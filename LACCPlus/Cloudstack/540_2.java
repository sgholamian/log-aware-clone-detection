//,temp,DomainDaoImpl.java,144,203,temp,DomainDaoImpl.java,94,142
//,3
public class xxx {
    @Override
    public synchronized DomainVO create(DomainVO domain) {
        // make sure domain name is valid
        String domainName = domain.getName();
        if (domainName != null) {
            if (domainName.contains("/")) {
                throw new IllegalArgumentException("Domain name contains one or more invalid characters.  Please enter a name without '/' characters.");
            }
        } else {
            throw new IllegalArgumentException("Domain name is null.  Please specify a valid domain name.");
        }

        long parent = Domain.ROOT_DOMAIN;
        if (domain.getParent() != null && domain.getParent().longValue() >= Domain.ROOT_DOMAIN) {
            parent = domain.getParent().longValue();
        }

        DomainVO parentDomain = findById(parent);
        if (parentDomain == null) {
            s_logger.error("Unable to load parent domain: " + parent);
            return null;
        }

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();

            parentDomain = this.lockRow(parent, true);
            if (parentDomain == null) {
                s_logger.error("Unable to lock parent domain: " + parent);
                return null;
            }

            domain.setPath(allocPath(parentDomain, domain.getName()));
            domain.setLevel(parentDomain.getLevel() + 1);

            parentDomain.setNextChildSeq(parentDomain.getNextChildSeq() + 1); // FIXME:  remove sequence number?
            parentDomain.setChildCount(parentDomain.getChildCount() + 1);
            persist(domain);
            update(parentDomain.getId(), parentDomain);

            txn.commit();
            return domain;
        } catch (Exception e) {
            s_logger.error("Unable to create domain due to " + e.getMessage(), e);
            txn.rollback();
            return null;
        }
    }

};