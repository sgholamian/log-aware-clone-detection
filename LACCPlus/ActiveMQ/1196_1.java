//,temp,LDAPAuthorizationMap.java,157,167,temp,LDAPAuthorizationMap.java,145,155
//,2
public class xxx {
    public Set<GroupPrincipal> getTempDestinationReadACLs() {
        try {
            context = open();
        } catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
        SearchControls constraints = new SearchControls();
        constraints.setReturningAttributes(new String[] {readAttribute});
        return getACLs(tempSearchBase, constraints, readBase, readAttribute);
    }

};