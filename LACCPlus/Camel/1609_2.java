//,temp,WordpressServicePostsAdapter.java,54,64,temp,WordpressServicePagesAdapter.java,56,64
//,3
public class xxx {
    @Override
    public List<Page> list(PageSearchCriteria c) {
        LOGGER.debug("Calling list pages: searchCriteria {}", c);
        checkNotNull(c, "Please provide a search criteria");
        return getSpi().list(this.getApiVersion(), c.getContext(), c.getPage(), c.getPerPage(), c.getSearch(), c.getAfter(),
                c.getAuthor(), c.getAuthorExclude(), c.getBefore(), c.getExclude(),
                c.getInclude(), c.getMenuOrder(), c.getOffset(), c.getOrder(), c.getOrderBy(), c.getParent(),
                c.getParentExclude(), c.getSlug(), c.getStatus(), c.getFilter());
    }

};