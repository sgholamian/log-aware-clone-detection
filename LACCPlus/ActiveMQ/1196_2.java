//,temp,LDAPAuthorizationMap.java,157,167,temp,LDAPAuthorizationMap.java,145,155
//,2
public class xxx {
    public Set<GroupPrincipal> getTempDestinationAdminACLs() {
        try {
            context = open();
        } catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
        SearchControls constraints = new SearchControls();
        constraints.setReturningAttributes(new String[] {adminAttribute});
        return getACLs(tempSearchBase, constraints, adminBase, adminAttribute);
    }

};