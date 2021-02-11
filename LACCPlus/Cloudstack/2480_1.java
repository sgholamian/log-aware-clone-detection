//,temp,HypervisorTemplateAdapter.java,139,152,temp,OCFS2ManagerImpl.java,105,120
//,3
public class xxx {
    private Long performDirectDownloadUrlValidation(final String url) {
        HostVO host = resourceManager.findOneRandomRunningHostByHypervisor(Hypervisor.HypervisorType.KVM);
        if (host == null) {
            throw new CloudRuntimeException("Couldn't find a host to validate URL " + url);
        }
        CheckUrlCommand cmd = new CheckUrlCommand(url);
        s_logger.debug("Performing URL " + url + " validation on host " + host.getId());
        Answer answer = _agentMgr.easySend(host.getId(), cmd);
        if (answer == null || !answer.getResult()) {
            throw new CloudRuntimeException("URL: " + url + " validation failed on host id " + host.getId());
        }
        CheckUrlAnswer ans = (CheckUrlAnswer) answer;
        return ans.getTemplateSize();
    }

};