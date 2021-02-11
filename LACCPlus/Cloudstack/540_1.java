//,temp,DomainDaoImpl.java,144,203,temp,DomainDaoImpl.java,94,142
//,3
public class xxx {
    @Override
    @DB
    public boolean remove(Long id) {
        // check for any active users / domains assigned to the given domain id and don't remove the domain if there are any
        if (id != null && id.longValue() == Domain.ROOT_DOMAIN) {
            s_logger.error("Can not remove domain " + id + " as it is ROOT domain");
            return false;
        } else {
            if(id == null) {
                s_logger.error("Can not remove domain without id.");
                return false;
            }
        }

        DomainVO domain = findById(id);
        if (domain == null) {
            s_logger.info("Unable to remove domain as domain " + id + " no longer exists");
            return true;
        }

        if (domain.getParent() == null) {
            s_logger.error("Invalid domain " + id + ", orphan?");
            return false;
        }

        String sql = "SELECT * from account where domain_id = " + id + " and removed is null";
        String sql1 = "SELECT * from domain where parent = " + id + " and removed is null";

        boolean success = false;
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            DomainVO parentDomain = super.lockRow(domain.getParent(), true);
            if (parentDomain == null) {
                s_logger.error("Unable to load parent domain: " + domain.getParent());
                return false;
            }

            PreparedStatement stmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }
            stmt = txn.prepareAutoCloseStatement(sql1);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            parentDomain.setChildCount(parentDomain.getChildCount() - 1);
            update(parentDomain.getId(), parentDomain);
            success = super.remove(id);
            txn.commit();
        } catch (SQLException ex) {
            success = false;
            s_logger.error("error removing domain: " + id, ex);
            txn.rollback();
        }
        return success;
    }

};