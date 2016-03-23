package com.testfairy.bamboo.impl;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.testfairy.bamboo.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                return config;
        }

//        @Override
//        public void populateContextForCreate(@NotNull final Map<String, Object> context) {
//                super.populateContextForCreate(context);
//                context.put(Strings.API_KEY, "Enter api-key");
//        }

        @Override
        public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
                super.populateContextForEdit(context, taskDefinition);
                context.put(Strings.API_KEY, taskDefinition.getConfiguration().get(Strings.API_KEY));
                context.put(Strings.APP_FILE, taskDefinition.getConfiguration().get(Strings.APP_FILE));
                context.put(Strings.PROGUARD_FILE, taskDefinition.getConfiguration().get(Strings.PROGUARD_FILE));
                context.put(Strings.TESTERS_GROUPS, taskDefinition.getConfiguration().get(Strings.TESTERS_GROUPS));
                context.put(Strings.SEND_EMAILS, taskDefinition.getConfiguration().get(Strings.SEND_EMAILS));

        }

        @Override
        public void populateContextForView(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {

                super.populateContextForView(context, taskDefinition);
                context.put(Strings.API_KEY, taskDefinition.getConfiguration().get(Strings.API_KEY));
                context.put(Strings.APP_FILE, taskDefinition.getConfiguration().get(Strings.APP_FILE));
                context.put(Strings.PROGUARD_FILE, taskDefinition.getConfiguration().get(Strings.PROGUARD_FILE));
                context.put(Strings.TESTERS_GROUPS, taskDefinition.getConfiguration().get(Strings.TESTERS_GROUPS));
                context.put(Strings.SEND_EMAILS, taskDefinition.getConfiguration().get(Strings.SEND_EMAILS));
        }
}