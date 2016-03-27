package com.testfairy.bamboo.impl;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.testfairy.bamboo.Strings;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

public class RequirementTaskConfigurator extends AbstractTaskConfigurator
{
        public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
                final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
                config.put(Strings.API_KEY, params.getString(Strings.API_KEY));
                config.put(Strings.APP_FILE, params.getString(Strings.APP_FILE));
                config.put(Strings.PROGUARD_FILE, params.getString(Strings.PROGUARD_FILE));
                config.put(Strings.TESTERS_GROUPS, params.getString(Strings.TESTERS_GROUPS));
                config.put(Strings.SEND_EMAILS, String.valueOf(params.getBoolean(Strings.SEND_EMAILS)));
                config.put(Strings.AUTO_UPDATE, String.valueOf(params.getBoolean(Strings.AUTO_UPDATE)));
                config.put(Strings.SHAKE_FOR_FEEDBACK, String.valueOf(params.getBoolean(Strings.SHAKE_FOR_FEEDBACK)));
                return config;
        }

        @Override
        public void validate(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection) {
                super.validate(params, errorCollection);

                /*validate API_KEY*/
                String apiKey = params.getString(Strings.API_KEY);
                if (StringUtils.isEmpty(apiKey)) {
                        errorCollection.addErrorMessage("Please set an TestFairy API KEY ");

                } else if (apiKey.length() != 40) {
                        errorCollection.addErrorMessage("TestFairy API KEY is invalid");

                }

                /*validate APP_FILE*/
                String appFile = params.getString(Strings.APP_FILE);
                File app = new File(appFile);
                if (!app.exists()) {
                        errorCollection.addErrorMessage("Path to app file (" + appFile + ") is invalid. Please make sure that this file exists.");
                }

                /*validate PROGUARD_FILE*/
                String proguardFile = params.getString(Strings.PROGUARD_FILE);
                if (StringUtils.isEmpty(proguardFile) == false) {
                        // only if there is an input, make sure the file
                        File proguard = new File(proguardFile);
                        if (!proguard.exists()) {
                                errorCollection.addErrorMessage("Path to proguard file (" + proguardFile + ") is invalid. Please make sure that this file exists.");
                        }
                }
        }

        @Override
        public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
                super.populateContextForEdit(context, taskDefinition);
                context.put(Strings.API_KEY, taskDefinition.getConfiguration().get(Strings.API_KEY));
                context.put(Strings.APP_FILE, taskDefinition.getConfiguration().get(Strings.APP_FILE));
                context.put(Strings.PROGUARD_FILE, taskDefinition.getConfiguration().get(Strings.PROGUARD_FILE));
                context.put(Strings.TESTERS_GROUPS, taskDefinition.getConfiguration().get(Strings.TESTERS_GROUPS));
                context.put(Strings.SEND_EMAILS, taskDefinition.getConfiguration().get(Strings.SEND_EMAILS));
                context.put(Strings.AUTO_UPDATE, taskDefinition.getConfiguration().get(Strings.AUTO_UPDATE));
                context.put(Strings.SHAKE_FOR_FEEDBACK, taskDefinition.getConfiguration().get(Strings.SHAKE_FOR_FEEDBACK));
        }

        @Override
        public void populateContextForView(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {

                super.populateContextForView(context, taskDefinition);
                context.put(Strings.API_KEY, taskDefinition.getConfiguration().get(Strings.API_KEY));
                context.put(Strings.APP_FILE, taskDefinition.getConfiguration().get(Strings.APP_FILE));
                context.put(Strings.PROGUARD_FILE, taskDefinition.getConfiguration().get(Strings.PROGUARD_FILE));
                context.put(Strings.TESTERS_GROUPS, taskDefinition.getConfiguration().get(Strings.TESTERS_GROUPS));
                context.put(Strings.SEND_EMAILS, taskDefinition.getConfiguration().get(Strings.SEND_EMAILS));
                context.put(Strings.AUTO_UPDATE, taskDefinition.getConfiguration().get(Strings.AUTO_UPDATE));
                context.put(Strings.SHAKE_FOR_FEEDBACK, taskDefinition.getConfiguration().get(Strings.SHAKE_FOR_FEEDBACK));
        }
}