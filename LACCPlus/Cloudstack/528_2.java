//,temp,VirtualMachineMO.java,1590,1653,temp,VirtualMachineMO.java,210,258
//,3
public class xxx {
            @Override
            public void run() {
                s_logger.info("VM Question monitor started...");

                while (!flags[0]) {
                    try {
                        VirtualMachineRuntimeInfo runtimeInfo = vmMo.getRuntimeInfo();
                        VirtualMachineQuestionInfo question = runtimeInfo.getQuestion();
                        if (question != null) {
                            s_logger.info("Question id: " + question.getId());
                            s_logger.info("Question text: " + question.getText());
                            if (question.getMessage() != null) {
                                for (VirtualMachineMessage msg : question.getMessage()) {
                                    if (s_logger.isInfoEnabled()) {
                                        s_logger.info("msg id: " + msg.getId());
                                        s_logger.info("msg text: " + msg.getText());
                                    }
                                    if ("msg.uuid.altered".equalsIgnoreCase(msg.getId())) {
                                        s_logger.info("Found that VM has a pending question that we need to answer programmatically, question id: " + msg.getId()
                                                + ", we will automatically answer as 'moved it' to address out of band HA for the VM");
                                        vmMo.answerVM(question.getId(), "1");
                                        break;
                                    }
                                }
                            }

                            if (s_logger.isTraceEnabled())
                                s_logger.trace("These are the choices we can have just in case");
                            ChoiceOption choice = question.getChoice();
                            if (choice != null) {
                                for (ElementDescription info : choice.getChoiceInfo()) {
                                    if (s_logger.isTraceEnabled()) {
                                        s_logger.trace("Choice option key: " + info.getKey());
                                        s_logger.trace("Choice option label: " + info.getLabel());
                                    }
                                }
                            }
                        }
                    } catch (Throwable e) {
                        s_logger.error("Unexpected exception: ", e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        s_logger.debug("[ignored] interupted while dealing with vm questions.");
                    }
                }
                s_logger.info("VM Question monitor stopped");
            }

};