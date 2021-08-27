//,temp,SimpleCachedLDAPAuthorizationMap.java,825,839,temp,SimpleCachedLDAPAuthorizationMap.java,795,813
//,3
public class xxx {
    public void objectRemoved(NamingEvent namingEvent, DestinationType destinationType, PermissionType permissionType) {
        LOG.debug("Removing object: {}", namingEvent.getOldBinding());
        Binding result = namingEvent.getOldBinding();

        try {
            DefaultAuthorizationMap map = this.map.get();
            LdapName name = new LdapName(result.getName());
            AuthorizationEntry entry = getEntry(map, name, destinationType);
            applyAcl(entry, permissionType, new HashSet<Object>());
        } catch (InvalidNameException e) {
            LOG.error("Policy not applied!  Error parsing DN for object removal for removal of {}", result.getName(), e);
        } catch (Exception e) {
            LOG.error("Policy not applied!  Error processing object removal for removal of {}", result.getName(), e);
        }
    }

};