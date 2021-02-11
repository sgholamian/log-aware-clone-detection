//,temp,QueryManagerImpl.java,2643,2790,temp,QueryManagerImpl.java,2500,2614
//,3
public class xxx {
    private Pair<List<ServiceOfferingJoinVO>, Integer> searchForServiceOfferingsInternal(ListServiceOfferingsCmd cmd) {
        // Note
        // The filteredOfferings method for offerings is being modified in accordance with
        // discussion with Will/Kevin
        // For now, we will be listing the following based on the usertype
        // 1. For root, we will filteredOfferings all offerings
        // 2. For domainAdmin and regular users, we will filteredOfferings everything in
        // their domains+parent domains ... all the way
        // till
        // root
        Boolean isAscending = Boolean.parseBoolean(_configDao.getValue("sortkey.algorithm"));
        isAscending = (isAscending == null ? true : isAscending);
        Filter searchFilter = new Filter(ServiceOfferingJoinVO.class, "sortKey", isAscending, cmd.getStartIndex(), cmd.getPageSizeVal());

        Account caller = CallContext.current().getCallingAccount();
        Object name = cmd.getServiceOfferingName();
        Object id = cmd.getId();
        Object keyword = cmd.getKeyword();
        Long vmId = cmd.getVirtualMachineId();
        Long domainId = cmd.getDomainId();
        Boolean isSystem = cmd.getIsSystem();
        String vmTypeStr = cmd.getSystemVmType();
        ServiceOfferingVO currentVmOffering = null;
        Boolean isRecursive = cmd.isRecursive();

        SearchCriteria<ServiceOfferingJoinVO> sc = _srvOfferingJoinDao.createSearchCriteria();
        if (!_accountMgr.isRootAdmin(caller.getId()) && isSystem) {
            throw new InvalidParameterValueException("Only ROOT admins can access system's offering");
        }

        // Keeping this logic consistent with domain specific zones
        // if a domainId is provided, we just return the so associated with this
        // domain
        if (domainId != null && !_accountMgr.isRootAdmin(caller.getId())) {
            // check if the user's domain == so's domain || user's domain is a
            // child of so's domain
            if (!isPermissible(caller.getDomainId(), domainId)) {
                throw new PermissionDeniedException("The account:" + caller.getAccountName() + " does not fall in the same domain hierarchy as the service offering");
            }
        }

        if (vmId != null) {
            VMInstanceVO vmInstance = _vmInstanceDao.findById(vmId);
            if ((vmInstance == null) || (vmInstance.getRemoved() != null)) {
                InvalidParameterValueException ex = new InvalidParameterValueException("unable to find a virtual machine with specified id");
                ex.addProxyObject(vmId.toString(), "vmId");
                throw ex;
            }

            _accountMgr.checkAccess(caller, null, true, vmInstance);

            currentVmOffering = _srvOfferingDao.findByIdIncludingRemoved(vmInstance.getId(), vmInstance.getServiceOfferingId());
            sc.addAnd("id", SearchCriteria.Op.NEQ, currentVmOffering.getId());

            // 1. Only return offerings with the same storage type
            sc.addAnd("useLocalStorage", SearchCriteria.Op.EQ, currentVmOffering.isUseLocalStorage());

            // 2.In case vm is running return only offerings greater than equal to current offering compute.
            if (vmInstance.getState() == VirtualMachine.State.Running) {
                sc.addAnd("cpu", Op.GTEQ, currentVmOffering.getCpu());
                sc.addAnd("speed", Op.GTEQ, currentVmOffering.getSpeed());
                sc.addAnd("ramSize", Op.GTEQ, currentVmOffering.getRamSize());
            }
        }

        // boolean includePublicOfferings = false;
        if ((_accountMgr.isNormalUser(caller.getId()) || _accountMgr.isDomainAdmin(caller.getId())) || caller.getType() == Account.ACCOUNT_TYPE_RESOURCE_DOMAIN_ADMIN) {
            // For non-root users.
            if (isSystem) {
                throw new InvalidParameterValueException("Only root admins can access system's offering");
            }
            if (isRecursive) { // domain + all sub-domains
                if (caller.getType() == Account.ACCOUNT_TYPE_NORMAL) {
                    throw new InvalidParameterValueException("Only ROOT admins and Domain admins can list service offerings with isrecursive=true");
                }
                DomainVO domainRecord = _domainDao.findById(caller.getDomainId());
                sc.addAnd("domainPath", SearchCriteria.Op.LIKE, domainRecord.getPath() + "%");
            } else { // domain + all ancestors
                // find all domain Id up to root domain for this account
                List<Long> domainIds = new ArrayList<Long>();
                DomainVO domainRecord;
                if (vmId != null) {
                    UserVmVO vmInstance = _userVmDao.findById(vmId);
                    domainRecord = _domainDao.findById(vmInstance.getDomainId());
                    if (domainRecord == null) {
                        s_logger.error("Could not find the domainId for vmId:" + vmId);
                        throw new CloudAuthenticationException("Could not find the domainId for vmId:" + vmId);
                    }
                } else {
                    domainRecord = _domainDao.findById(caller.getDomainId());
                    if (domainRecord == null) {
                        s_logger.error("Could not find the domainId for account:" + caller.getAccountName());
                        throw new CloudAuthenticationException("Could not find the domainId for account:" + caller.getAccountName());
                    }
                }
                domainIds.add(domainRecord.getId());
                while (domainRecord.getParent() != null) {
                    domainRecord = _domainDao.findById(domainRecord.getParent());
                    domainIds.add(domainRecord.getId());
                }

                SearchCriteria<ServiceOfferingJoinVO> spc = _srvOfferingJoinDao.createSearchCriteria();
                spc.addOr("domainId", SearchCriteria.Op.IN, domainIds.toArray());
                spc.addOr("domainId", SearchCriteria.Op.NULL); // include public offering as well
                sc.addAnd("domainId", SearchCriteria.Op.SC, spc);
            }
        } else {
            // for root users
            if (caller.getDomainId() != 1 && isSystem) { // NON ROOT admin
                throw new InvalidParameterValueException("Non ROOT admins cannot access system's offering");
            }
            if (domainId != null) {
                sc.addAnd("domainId", SearchCriteria.Op.EQ, domainId);
            }
        }

        if (keyword != null) {
            SearchCriteria<ServiceOfferingJoinVO> ssc = _srvOfferingJoinDao.createSearchCriteria();
            ssc.addOr("displayText", SearchCriteria.Op.LIKE, "%" + keyword + "%");
            ssc.addOr("name", SearchCriteria.Op.LIKE, "%" + keyword + "%");

            sc.addAnd("name", SearchCriteria.Op.SC, ssc);
        }

        if (id != null) {
            sc.addAnd("id", SearchCriteria.Op.EQ, id);
        }

        if (isSystem != null) {
            // note that for non-root users, isSystem is always false when
            // control comes to here
            sc.addAnd("systemUse", SearchCriteria.Op.EQ, isSystem);
        }

        if (name != null) {
            sc.addAnd("name", SearchCriteria.Op.EQ, name);
        }

        if (vmTypeStr != null) {
            sc.addAnd("vmType", SearchCriteria.Op.EQ, vmTypeStr);
        }

        Pair<List<ServiceOfferingJoinVO>, Integer> result = _srvOfferingJoinDao.searchAndCount(sc, searchFilter);

        //Couldn't figure out a smart way to filter offerings based on tags in sql so doing it in Java.
        List<ServiceOfferingJoinVO> filteredOfferings = filterOfferingsOnCurrentTags(result.first(), currentVmOffering);
        return new Pair<>(filteredOfferings, result.second());
    }

};