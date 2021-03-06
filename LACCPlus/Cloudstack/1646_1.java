//,temp,LdapUserSearchCmd.java,70,86,temp,DeleteUcsManagerCmd.java,54,67
//,3
public class xxx {
    @Override
    public void execute() {
        final ListResponse<LdapUserResponse> response = new ListResponse<LdapUserResponse>();
        List<LdapUser> users = null;

        try {
            users = _ldapManager.searchUsers(query);
        } catch (final NoLdapUserMatchingQueryException e) {
            s_logger.debug(e.getMessage());
        }

        final List<LdapUserResponse> ldapUserResponses = createLdapUserResponse(users);

        response.setResponses(ldapUserResponses);
        response.setResponseName(getCommandName());
        setResponseObject(response);
    }

};