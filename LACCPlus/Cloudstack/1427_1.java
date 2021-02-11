//,temp,NiciraNvpConfigureStaticNatRulesCommandWrapper.java,45,113,temp,NiciraNvpConfigurePortForwardingRulesCommandWrapper.java,45,121
//,3
public class xxx {
    @Override
    public Answer execute(final ConfigureStaticNatRulesOnLogicalRouterCommand command, final NiciraNvpResource niciraNvpResource) {
        final NiciraNvpApi niciraNvpApi = niciraNvpResource.getNiciraNvpApi();

        try {
            final List<NatRule> existingRules = niciraNvpApi.findNatRulesByLogicalRouterUuid(command.getLogicalRouterUuid());
            // Rules of the game (also known as assumptions-that-will-make-stuff-break-later-on)
            // A SourceNat rule with a match other than a /32 cidr is assumed to be the "main" SourceNat rule
            // Any other SourceNat rule should have a corresponding DestinationNat rule

            for (final StaticNatRuleTO rule : command.getRules()) {

                final NatRule[] rulepair = niciraNvpResource.generateStaticNatRulePair(rule.getDstIp(), rule.getSrcIp());

                NatRule incoming = null;
                NatRule outgoing = null;

                for (final NatRule storedRule : existingRules) {
                    if (storedRule.equalsIgnoreUuid(rulepair[1])) {
                        // The outgoing rule exists
                        outgoing = storedRule;
                        s_logger.debug("Found matching outgoing rule " + outgoing.getUuid());
                        if (incoming != null) {
                            break;
                        }
                    } else if (storedRule.equalsIgnoreUuid(rulepair[0])) {
                        // The incoming rule exists
                        incoming = storedRule;
                        s_logger.debug("Found matching incoming rule " + incoming.getUuid());
                        if (outgoing != null) {
                            break;
                        }
                    }
                }
                if (incoming != null && outgoing != null) {
                    if (rule.revoked()) {
                        s_logger.debug("Deleting incoming rule " + incoming.getUuid());
                        niciraNvpApi.deleteLogicalRouterNatRule(command.getLogicalRouterUuid(), incoming.getUuid());

                        s_logger.debug("Deleting outgoing rule " + outgoing.getUuid());
                        niciraNvpApi.deleteLogicalRouterNatRule(command.getLogicalRouterUuid(), outgoing.getUuid());
                    }
                } else {
                    if (rule.revoked()) {
                        s_logger.warn("Tried deleting a rule that does not exist, " + rule.getSrcIp() + " -> " + rule.getDstIp());
                        break;
                    }

                    rulepair[0] = niciraNvpApi.createLogicalRouterNatRule(command.getLogicalRouterUuid(), rulepair[0]);
                    s_logger.debug("Created " + niciraNvpResource.natRuleToString(rulepair[0]));

                    try {
                        rulepair[1] = niciraNvpApi.createLogicalRouterNatRule(command.getLogicalRouterUuid(), rulepair[1]);
                        s_logger.debug("Created " + niciraNvpResource.natRuleToString(rulepair[1]));
                    } catch (final NiciraNvpApiException ex) {
                        s_logger.debug("Failed to create SourceNatRule, rolling back DestinationNatRule");
                        niciraNvpApi.deleteLogicalRouterNatRule(command.getLogicalRouterUuid(), rulepair[0].getUuid());
                        throw ex; // Rethrow original exception
                    }

                }
            }
            return new ConfigureStaticNatRulesOnLogicalRouterAnswer(command, true, command.getRules().size() + " StaticNat rules applied");
        } catch (final NiciraNvpApiException e) {
            final CommandRetryUtility retryUtility = niciraNvpResource.getRetryUtility();
            retryUtility.addRetry(command, NUM_RETRIES);
            return retryUtility.retry(command, ConfigureStaticNatRulesOnLogicalRouterAnswer.class, e);
        }
    }

};