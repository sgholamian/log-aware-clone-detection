//,temp,LibvirtPostCertificateRenewalCommandWrapper.java,35,47,temp,LibvirtComputingResource.java,1390,1401
//,3
public class xxx {
    @Override
    public Answer execute(final PostCertificateRenewalCommand command, final LibvirtComputingResource serverResource) {
        s_logger.info("Restarting libvirt after certificate provisioning/renewal");
        if (command != null) {
            final int timeout = 30000;
            Script script = new Script(true, "service", timeout, s_logger);
            script.add("libvirtd");
            script.add("restart");
            script.execute();
            return new SetupCertificateAnswer(true);
       }
        return new SetupCertificateAnswer(false);
    }

};