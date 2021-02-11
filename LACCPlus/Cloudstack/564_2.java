//,temp,SecurityGroupUsageParser.java,58,124,temp,VMSanpshotOnPrimaryParser.java,57,101
//,3
public class xxx {
    public static boolean parse(AccountVO account, Date startDate, Date endDate) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Parsing all VmSnapshot on primary usage events for account: " + account.getId());
        }
        if ((endDate == null) || endDate.after(new Date())) {
            endDate = new Date();
        }

        List<UsageSnapshotOnPrimaryVO> usageUsageVMSnapshots = s_usageSnapshotOnPrimaryDao.getUsageRecords(account.getId(), account.getDomainId(), startDate, endDate);

        if (usageUsageVMSnapshots.isEmpty()) {
            s_logger.debug("No VM snapshot on primary usage events for this period");
            return true;
        }

        Map<String, UsageSnapshotOnPrimaryVO> unprocessedUsage = new HashMap<String, UsageSnapshotOnPrimaryVO>();
        for (UsageSnapshotOnPrimaryVO usageRec : usageUsageVMSnapshots) {
            s_logger.debug("usageRec for VMsnap on primary " + usageRec.toString());
            String key = usageRec.getName();
            if (usageRec.getPhysicalSize() == 0) {
                usageRec.setDeleted(new Date());
                s_usageSnapshotOnPrimaryDao.updateDeleted(usageRec);
            } else {
                unprocessedUsage.put(key, usageRec);
            }
        }

        for (String key : unprocessedUsage.keySet()) {
            UsageSnapshotOnPrimaryVO usageRec = unprocessedUsage.get(key);
            Date created = usageRec.getCreated();
            if (created.before(startDate)) {
                created = startDate;
            }
            Date endDateEffective = endDate;
            if (usageRec.getDeleted() != null && usageRec.getDeleted().before(endDate)){
                endDateEffective = usageRec.getDeleted();
                s_logger.debug("Remoevd vm snapshot found endDateEffective " + endDateEffective + " period end data " + endDate);
            }
            long duration = (endDateEffective.getTime() - created.getTime()) + 1;
            createUsageRecord(UsageTypes.VM_SNAPSHOT_ON_PRIMARY, duration, created, endDateEffective, account, usageRec.getId(), usageRec.getName(), usageRec.getZoneId(),
                    usageRec.getVirtualSize(), usageRec.getPhysicalSize());
        }

        return true;
    }

};