//,temp,WordpressServicePostsAdapter.java,54,64,temp,WordpressServicePagesAdapter.java,56,64
//,3
public class xxx {
    @Override
    public List<Post> list(PostSearchCriteria criteria) {
        LOGGER.debug("Calling list posts: searchCriteria {}", criteria);
        checkNotNull(criteria, "Please provide a search criteria");
        return getSpi().list(this.getApiVersion(), criteria.getContext(), criteria.getPage(), criteria.getPerPage(),
                criteria.getSearch(), criteria.getAfter(), criteria.getAuthor(),
                criteria.getAuthorExclude(), criteria.getBefore(), criteria.getExclude(), criteria.getInclude(),
                criteria.getOffset(), criteria.getOrder(), criteria.getOrderBy(),
                criteria.getSlug(), criteria.getStatus(), criteria.getCategories(), criteria.getCategoriesExclude(),
                criteria.getTags(), criteria.getTagsExclude(), criteria.getStick());
    }

};