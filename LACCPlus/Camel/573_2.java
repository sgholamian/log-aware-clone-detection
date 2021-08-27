//,temp,UserConsumerHandler.java,38,43,temp,UserListConsumerHandler.java,37,42
//,3
public class xxx {
    @Override
    protected List<Status> doPoll() throws TwitterException {
        Paging paging = getLastIdPaging();
        log.trace("doPoll.getUserListStatuses(user={}, list={}, sinceId={})", user, list, paging.getSinceId());
        return getTwitter().getUserListStatuses(user, list, paging);
    }

};