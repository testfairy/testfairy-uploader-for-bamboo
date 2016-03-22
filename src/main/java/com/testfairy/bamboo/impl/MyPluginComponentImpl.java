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

                final String apiKey = taskContext.getConfigurationMap().get(Strings.API_KEY);

                buildLogger.addBuildLogEntry(apiKey);

//                ConfigurationMap configurationMap = taskContext.getConfigurationMap();
//                String API_KEY = configurationMap.get(Strings.API_KEY);

                String API_KEY = "5f8d490c554f63cf7784174bcdcb3c87f2447709";
                String APK_PATH = "/tmp/Ham/out/ham.apk";
//        String KEYSTORE_PATH = "/tmp/Ham/out/debug.keystore";
//        String KEYSTORE_ALIAS = "androiddebugkey";
//        String STORE_PASSWORD = "android";
//        String KEY_PASSWORD = "";


                Options options = new Options.Builder()
                    .notifyTesters(true)
                    .setComment("Uploading New Build")
                    .build();

                AndroidUploader uploader = new AndroidUploader.Builder(API_KEY)
                    .setOptions(options)
                    .setApkPath(APK_PATH)
                    .enableInstrumentation(false)
//                .setKeystore(KEYSTORE_PATH, KEYSTORE_ALIAS, STORE_PASSWORD, KEY_PASSWORD)
                    .build();

                uploader.upload(new Listener() {

                        public void onUploadStarted() {
//                System.out.println("onUploadStarted");
                                buildLogger.addBuildLogEntry("onUploadStarted");
                        }

                        public void onUploadComplete(Build build) {
//                System.out.println("onUploadComplete");
//                System.out.println(build.appName());

                                buildLogger.addBuildLogEntry("onUploadComplete");

                                buildLogger.addBuildLogEntry("App name: " + build.appName());
                                buildLogger.addBuildLogEntry("buildUrl: " + build.buildUrl());

                        }

                        public void onUploadFailed(Throwable throwable) {
//                System.out.println("onUploadFailed");
                                buildLogger.addBuildLogEntry("onUploadFailed");
                                buildLogger.addBuildLogEntry(throwable.getMessage());
                        }

                        public void onProgress(float v) {

                        }
                });


//        final String say = taskContext.getConfigurationMap().get("say");

//        buildLogger.addBuildLogEntry(say);

                return TaskResultBuilder.create(taskContext).success().build();
        }
}