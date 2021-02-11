//,temp,QueryManagerImpl.java,2643,2790,temp,QueryManagerImpl.java,2500,2614
//,3
public class xxx {
    private Pair<List<DiskOfferingJoinVO>, Integer> searchForDiskOfferingsInternal(ListDiskOfferingsCmd cmd) {
        // Note
        // The list method for offerings is being modified in accordance with
        // discussion with Will/Kevin
        // For now, we will be listing the following based on the usertype
        // 1. For root, we will list all offerings
        // 2. For domainAdmin and regular users, we will list everything in
        // their domains+parent domains ... all the way
        // till
        // root

        Boolean isAscending = Boolean.parseBoolean(_configDao.getValue("sortkey.algorithm"));
        isAscending = (isAscending == null ? true : isAscending);
        Filter searchFilter = new Filter(DiskOfferingJoinVO.class, "sortKey", isAscending, cmd.getStartIndex(), cmd.getPageSizeVal());
        SearchCriteria<DiskOfferingJoinVO> sc = _diskOfferingJoinDao.createSearchCriteria();
        sc.addAnd("type", Op.EQ, DiskOfferingVO.Type.Disk);

        Account account = CallContext.current().getCallingAccount();
        Object name = cmd.getDiskOfferingName();
        Object id = cmd.getId();
        Object keyword = cmd.getKeyword();
        Long domainId = cmd.getDomainId();
        Boolean isRootAdmin = _accountMgr.isRootAdmin(account.getAccountId());
        Boolean isRecursive = cmd.isRecursive();
        // Keeping this logic consistent with domain specific zones
        // if a domainId is provided, we just return the disk offering
        // associated with this domain
        if (domainId != null) {
            if (_accountMgr.isRootAdmin(account.getId()) || isPermissible(account.getDomainId(), domainId)) {
                // check if the user's domain == do's domain || user's domain is
                // a child of so's domain for non-root users
                sc.addAnd("domainId", SearchCriteria.Op.EQ, domainId);
                if (!isRootAdmin) {
                    sc.addAnd("displayOffering", SearchCriteria.Op.EQ, 1);
                }
                return _diskOfferingJoinDao.searchAndCount(sc, searchFilter);
            } else {
                throw new PermissionDeniedException("The account:" + account.getAccountName() + " does not fall in the same domain hierarchy as the disk offering");
            }
        }

        List<Long> domainIds = null;
        // For non-root users, only return all offerings for the user's domain,
        // and everything above till root
        if ((_accountMgr.isNormalUser(account.getId()) || _accountMgr.isDomainAdmin(account.getId())) || account.getType() == Account.ACCOUNT_TYPE_RESOURCE_DOMAIN_ADMIN) {
            if (isRecursive) { // domain + all sub-domains
                if (account.getType() == Account.ACCOUNT_TYPE_NORMAL) {
                    throw new InvalidParameterValueException("Only ROOT admins and Domain admins can list disk offerings with isrecursive=true");
                }
                DomainVO domainRecord = _domainDao.findById(account.getDomainId());
                sc.addAnd("domainPath", SearchCriteria.Op.LIKE, domainRecord.getPath() + "%");
            } else { // domain + all ancestors
                // find all domain Id up to root domain for this account
                domainIds = new ArrayList<Long>();
                DomainVO domainRecord = _domainDao.findById(account.getDomainId());
                if (domainRecord == null) {
                    s_logger.error("Could not find the domainId for account:" + account.getAccountName());
                    throw new CloudAuthenticationException("Could not find the domainId for account:" + account.getAccountName());
                }
                domainIds.add(domainRecord.getId());
                while (domainRecord.getParent() != null) {
                    domainRecord = _domainDao.findById(domainRecord.getParent());
                    domainIds.add(domainRecord.getId());
                }

                SearchCriteria<DiskOfferingJoinVO> spc = _diskOfferingJoinDao.createSearchCriteria();

                spc.addOr("domainId", SearchCriteria.Op.IN, domainIds.toArray());
                spc.addOr("domainId", SearchCriteria.Op.NULL); // include public offering as where
                sc.addAnd("domainId", SearchCriteria.Op.SC, spc);
                sc.addAnd("systemUse", SearchCriteria.Op.EQ, false); // non-root users should not see system offering at all
            }

        }

        if (keyword != null) {
            SearchCriteria<DiskOfferingJoinVO> ssc = _diskOfferingJoinDao.createSearchCriteria();
            ssc.addOr("displayText", SearchCriteria.Op.LIKE, "%" + keyword + "%");
            ssc.addOr("name", SearchCriteria.Op.LIKE, "%" + keyword + "%");

            sc.addAnd("name", SearchCriteria.Op.SC, ssc);
        }

        if (id != null) {
            sc.addAnd("id", SearchCriteria.Op.EQ, id);
        }

        if (name != null) {
            sc.addAnd("name", SearchCriteria.Op.EQ, name);
        }

        // FIXME: disk offerings should search back up the hierarchy for
        // available disk offerings...
        /*
         * sb.addAnd("domainId", sb.entity().getDomainId(),
         * SearchCriteria.Op.EQ); if (domainId != null) {
         * SearchBuilder<DomainVO> domainSearch =
         * _domainDao.createSearchBuilder(); domainSearch.addAnd("path",
         * domainSearch.entity().getPath(), SearchCriteria.Op.LIKE);
         * sb.join("domainSearch", domainSearch, sb.entity().getDomainId(),
         * domainSearch.entity().getId()); }
         */

        // FIXME: disk offerings should search back up the hierarchy for
        // available disk offerings...
        /*
         * if (domainId != null) { sc.setParameters("domainId", domainId); //
         * //DomainVO domain = _domainDao.findById((Long)domainId); // // I want
         * to join on user_vm.domain_id = domain.id where domain.path like
         * 'foo%' //sc.setJoinParameters("domainSearch", "path",
         * domain.getPath() + "%"); // }
         */

        return _diskOfferingJoinDao.searchAndCount(sc, searchFilter);
    }

};