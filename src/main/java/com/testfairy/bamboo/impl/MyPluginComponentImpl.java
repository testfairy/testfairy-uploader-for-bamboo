package com.testfairy.bamboo.impl;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.ApplicationProperties;
import com.testfairy.bamboo.Strings;
import com.testfairy.bamboo.api.MyPluginComponent;
import com.testfairy.uploader.AndroidUploader;
import com.testfairy.uploader.Build;
import com.testfairy.uploader.Listener;
import com.testfairy.uploader.Options;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

@ExportAsService({MyPluginComponent.class})
@Named("myPluginComponent")
public class MyPluginComponentImpl implements MyPluginComponent, TaskType {
        @ComponentImport
        private final ApplicationProperties applicationProperties;

        @Inject
        public MyPluginComponentImpl(final ApplicationProperties applicationProperties) {
                this.applicationProperties = applicationProperties;
        }

        public String getName() {
                if (null != applicationProperties) {
                        return "myComponent:" + applicationProperties.getDisplayName();
                }

                return "myComponent";
        }

        @NotNull
        public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {

                final BuildLogger buildLogger = taskContext.getBuildLogger();

                final Boolean autoUpdate = null; //= taskContext.getConfigurationMap().get(Strings.autoUpdate);
                final Boolean shakeForBugReports = null; //= taskContext.getConfigurationMap().get(Strings.autoUpdate);

                final String apiKey = taskContext.getConfigurationMap().get(Strings.API_KEY);
                final String appFile = taskContext.getConfigurationMap().get(Strings.APP_FILE);
                final String proguardFile = taskContext.getConfigurationMap().get(Strings.PROGUARD_FILE);
                final String testersGroups = taskContext.getConfigurationMap().get(Strings.TESTERS_GROUPS);
                final Boolean shouldSendEmails = taskContext.getConfigurationMap().getAsBoolean(Strings.SEND_EMAILS);

                buildLogger.addBuildLogEntry("apiKey: " + apiKey);
                buildLogger.addBuildLogEntry("appFile: " + appFile);
                buildLogger.addBuildLogEntry("proguardFile: " + proguardFile);
                buildLogger.addBuildLogEntry("testersGroups: " + testersGroups);
                buildLogger.addBuildLogEntry("SHOULD_SEND_EMAILS " + (shouldSendEmails == false ?  "false" : "true"));

                Options options = new Options.Builder()
                    .notifyTesters(true)
                    .setComment("Uploading New Build")
                    .addTesterGroup(testersGroups)
                    .notifyTesters(shouldSendEmails)
//                    .setAutoUpdate(autoUpdate)
//                    .shakeForBugReports(shakeForBugReports)
                    .build();

                AndroidUploader uploader = new AndroidUploader.Builder(apiKey)
                    .setOptions(options)
                    .setApkPath(appFile)
                    .enableInstrumentation(false)
//                    .setHttpUserAgent(Strings.USER_AGENT)
                    .build();

                uploader.upload(new Listener() {

                        public void onUploadStarted() {
                                buildLogger.addBuildLogEntry("onUploadStarted");
                        }

                        public void onUploadComplete(Build build) {
                                buildLogger.addBuildLogEntry("onUploadComplete");
                                buildLogger.addBuildLogEntry("App name: " + build.appName());
                                buildLogger.addBuildLogEntry("buildUrl: " + build.buildUrl());

                        }

                        public void onUploadFailed(Throwable throwable) {
                                buildLogger.addBuildLogEntry("onUploadFailed");
                                buildLogger.addBuildLogEntry(throwable.getMessage());
                        }

                        public void onProgress(float v) {

                        }
                });

                return TaskResultBuilder.create(taskContext).success().build();
        }
}