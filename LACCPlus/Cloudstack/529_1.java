//,temp,VirtualMachineMO.java,3108,3175,temp,VirtualMachineMO.java,1590,1653
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
                            encounterQuestion[0] = true;
                            if (s_logger.isTraceEnabled()) {
                                s_logger.trace("Question id: " + question.getId());
                                s_logger.trace("Question text: " + question.getText());
                            }

                            if (question.getMessage() != null) {
                                for (VirtualMachineMessage msg : question.getMessage()) {
                                    if (s_logger.isTraceEnabled()) {
                                        s_logger.trace("msg id: " + msg.getId());
                                        s_logger.trace("msg text: " + msg.getText());
                                    }
                                    if ("msg.cdromdisconnect.locked".equalsIgnoreCase(msg.getId())) {
                                        s_logger.info("Found that VM has a pending question that we need to answer programmatically, question id: " + msg.getId() +
                                                ", for safe operation we will automatically decline it");
                                        vmMo.answerVM(question.getId(), ANSWER_NO);
                                        break;
                                    }
                                }
                            } else if (question.getText() != null) {
                                String text = question.getText();
                                String msgId;
                                String msgText;
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("question text : " + text);
                                }
                                String[] tokens = text.split(":");
                                msgId = tokens[0];
                                msgText = tokens[1];
                                if ("msg.cdromdisconnect.locked".equalsIgnoreCase(msgId)) {
                                    s_logger.info("Found that VM has a pending question that we need to answer programmatically, question id: " + question.getId() +
                                            ". Message id : " + msgId + ". Message text : " + msgText + ", for safe operation we will automatically decline it.");
                                    vmMo.answerVM(question.getId(), ANSWER_NO);
                                }
                            }

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
                        s_logger.debug("[ignored] interupted while handling vm question about umount tools install.");
                    }
                }

                s_logger.info("VM Question monitor stopped");
            }

};