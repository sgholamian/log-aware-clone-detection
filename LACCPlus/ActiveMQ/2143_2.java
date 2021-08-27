//,temp,SimpleCachedLDAPAuthorizationMap.java,825,839,temp,SimpleCachedLDAPAuthorizationMap.java,795,813
//,3
public class xxx {
    public void objectAdded(NamingEvent namingEvent, DestinationType destinationType, PermissionType permissionType) {
        LOG.debug("Adding object: {}", namingEvent.getNewBinding());
        SearchResult result = (SearchResult) namingEvent.getNewBinding();

        try {
            DefaultAuthorizationMap map = this.map.get();
            LdapName name = new LdapName(result.getName());
            AuthorizationEntry entry = getEntry(map, name, destinationType);

            applyACL(entry, result, permissionType);
            if (!(entry instanceof TempDestinationAuthorizationEntry)) {
                map.put(entry.getDestination(), entry);
            }
        } catch (InvalidNameException e) {
            LOG.error("Policy not applied!  Error parsing DN for addition of {}", result.getName(), e);
        } catch (Exception e) {
            LOG.error("Policy not applied!  Error processing object addition for addition of {}", result.getName(), e);
        }
    }

};