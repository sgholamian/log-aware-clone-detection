//,temp,ConsoleProxyServlet.java,418,484,temp,ConsoleProxyServlet.java,370,416
//,3
public class xxx {
    private String composeConsoleAccessUrl(String rootUrl, VirtualMachine vm, HostVO hostVo) {
        StringBuffer sb = new StringBuffer(rootUrl);
        String host = hostVo.getPrivateIpAddress();

        Pair<String, Integer> portInfo;
        if (hostVo.getResourceState().equals(ResourceState.ErrorInMaintenance)) {
            UserVmDetailVO detailAddress = _userVmDetailsDao.findDetail(vm.getId(), "kvm.vnc.address");
            UserVmDetailVO detailPort = _userVmDetailsDao.findDetail(vm.getId(), "kvm.vnc.port");
            portInfo = new Pair<>(detailAddress.getValue(), Integer.valueOf(detailPort.getValue()));
        } else {
            portInfo = _ms.getVncPort(vm);
        }
        if (s_logger.isDebugEnabled())
            s_logger.debug("Port info " + portInfo.first());

        Ternary<String, String, String> parsedHostInfo = parseHostInfo(portInfo.first());

        int port = -1;
        if (portInfo.second() == -9) {
            //for hyperv
            port = Integer.parseInt(_ms.findDetail(hostVo.getId(), "rdp.server.port").getValue());
        } else {
            port = portInfo.second();
        }

        String sid = vm.getVncPassword();
        UserVmDetailVO details = _userVmDetailsDao.findDetail(vm.getId(), "keyboard");

        String tag = vm.getUuid();

        String ticket = genAccessTicket(parsedHostInfo.first(), String.valueOf(port), sid, tag);
        ConsoleProxyPasswordBasedEncryptor encryptor = new ConsoleProxyPasswordBasedEncryptor(getEncryptorPassword());
        ConsoleProxyClientParam param = new ConsoleProxyClientParam();
        param.setClientHostAddress(parsedHostInfo.first());
        param.setClientHostPort(port);
        param.setClientHostPassword(sid);
        param.setClientTag(tag);
        param.setTicket(ticket);

        if (details != null) {
            param.setLocale(details.getValue());
        }

        if (portInfo.second() == -9) {
            //For Hyperv Clinet Host Address will send Instance id
            param.setHypervHost(host);
            param.setUsername(_ms.findDetail(hostVo.getId(), "username").getValue());
            param.setPassword(_ms.findDetail(hostVo.getId(), "password").getValue());
        }
        if (parsedHostInfo.second() != null  && parsedHostInfo.third() != null) {
            param.setClientTunnelUrl(parsedHostInfo.second());
            param.setClientTunnelSession(parsedHostInfo.third());
        }

        sb.append("/ajax?token=" + encryptor.encryptObject(ConsoleProxyClientParam.class, param));

        // for console access, we need guest OS type to help implement keyboard
        long guestOs = vm.getGuestOSId();
        GuestOSVO guestOsVo = _ms.getGuestOs(guestOs);
        if (guestOsVo.getCategoryId() == 6)
            sb.append("&guest=windows");

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Compose console url: " + sb.toString());
        }
        return sb.toString();
    }

};