//,temp,SecondaryStorageVmAlertAdapter.java,49,149,temp,ConsoleProxyAlertAdapter.java,49,145
//,2
public class xxx {
    public void onProxyAlert(Object sender, ConsoleProxyAlertEventArgs args) {
        if (s_logger.isDebugEnabled())
            s_logger.debug("received console proxy alert");

        DataCenterVO dc = _dcDao.findById(args.getZoneId());
        ConsoleProxyVO proxy = args.getProxy();
        //FIXME - Proxy can be null in case of creation failure. Have a better fix than checking for != 0
        if (proxy == null && args.getProxyId() != 0)
            proxy = _consoleProxyDao.findById(args.getProxyId());

        if (proxy == null && args.getType() != ConsoleProxyAlertEventArgs.PROXY_CREATE_FAILURE) {
            throw new CloudRuntimeException("Invalid alert arguments, proxy must be set");
        }

        switch (args.getType()) {
        case ConsoleProxyAlertEventArgs.PROXY_CREATED:
            if (s_logger.isDebugEnabled())
                s_logger.debug("New console proxy created, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() +
                        ", private IP: " + proxy.getPrivateIpAddress());
            break;

        case ConsoleProxyAlertEventArgs.PROXY_UP:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy is up, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() +
                        ", private IP: " + proxy.getPrivateIpAddress());

            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY, args.getZoneId(), proxy.getPodIdToDeployIn(),
                    "Console proxy up in zone: " + dc.getName() +
                    ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() + ", private IP: " +
                    (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()), "Console proxy up (zone " + dc.getName() + ")");
            break;

        case ConsoleProxyAlertEventArgs.PROXY_DOWN:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy is down, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() +
                        ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()));

            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY, args.getZoneId(), proxy.getPodIdToDeployIn(),
                    "Console proxy down in zone: " + dc.getName() +
                    ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() + ", private IP: " +
                    (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()), "Console proxy down (zone " + dc.getName() + ")");
            break;

        case ConsoleProxyAlertEventArgs.PROXY_REBOOTED:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy is rebooted, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() +
                        ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()));

            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY, args.getZoneId(), proxy.getPodIdToDeployIn(),
                    "Console proxy rebooted in zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() +
                    ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()), "Console proxy rebooted (zone " + dc.getName() +
                    ")");
            break;

        case ConsoleProxyAlertEventArgs.PROXY_CREATE_FAILURE:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy creation failure, zone: " + dc.getName());
            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY, args.getZoneId(), null,
                    "Console proxy creation failure. zone: " + dc.getName() + ", error details: " + args.getMessage(),
                    "Console proxy creation failure (zone " + dc.getName() + ")");
            break;

        case ConsoleProxyAlertEventArgs.PROXY_START_FAILURE:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy startup failure, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " +
                        proxy.getPublicIpAddress() + ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()));

            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY, args.getZoneId(), proxy.getPodIdToDeployIn(),
                    "Console proxy startup failure. zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " + proxy.getPublicIpAddress() +
                    ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()) + ", error details: " + args.getMessage(),
                    "Console proxy startup failure (zone " + dc.getName() + ")");
            break;

        case ConsoleProxyAlertEventArgs.PROXY_FIREWALL_ALERT:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy firewall alert, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " +
                        proxy.getPublicIpAddress() + ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()));

            _alertMgr.sendAlert(
                    AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY,
                    args.getZoneId(),
                    proxy.getPodIdToDeployIn(),
                    "Failed to open console proxy firewall port. zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " +
                            proxy.getPublicIpAddress() + ", private IP: " + (proxy.getPrivateIpAddress() == null ? "N/A" : proxy.getPrivateIpAddress()),
                            "Console proxy alert (zone " + dc.getName() + ")");
            break;

        case ConsoleProxyAlertEventArgs.PROXY_STORAGE_ALERT:
            if (s_logger.isDebugEnabled())
                s_logger.debug("Console proxy storage alert, zone: " + dc.getName() + ", proxy: " + proxy.getHostName() + ", public IP: " +
                        proxy.getPublicIpAddress() + ", private IP: " + proxy.getPrivateIpAddress() + ", message: " + args.getMessage());

            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_STORAGE_MISC, args.getZoneId(), proxy.getPodIdToDeployIn(),
                    "Console proxy storage issue. zone: " + dc.getName() + ", message: " + args.getMessage(), "Console proxy alert (zone " + dc.getName() + ")");
            break;
        }
    }

};