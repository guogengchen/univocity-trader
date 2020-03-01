package com.univocity.trader.config;

import com.univocity.trader.indicators.base.*;

import java.util.*;

import static com.univocity.trader.indicators.base.TimeInterval.*;

public abstract class Configuration<C extends Configuration<C, T>, T extends AccountConfiguration<T>>
    implements ConfigurationGroup {

    private final List<ConfigurationGroup> configurationGroups = new ArrayList<>();
    private final ConfigurationManager<C> manager;

    private final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
    private final EmailConfiguration emailConfiguration = new EmailConfiguration();
    private final Simulation simulation = new Simulation();
    final AccountList<T> accountList = new AccountList<T>(this::newAccountConfiguration);
    private TimeInterval tickInterval = minutes(1);

    protected Configuration() {
        this("univocity-trader.properties");
    }

    final void loadConfigurationGroups() {
        this.addConfigurationGroups(configurationGroups);
    }

    final List<ConfigurationGroup> getConfigurationGroups() {
        return configurationGroups;
    }

    protected Configuration(String defaultConfigurationFile) {
        manager = new ConfigurationManager<C>((C)this, defaultConfigurationFile);
    }

    public C configure() {
        return manager.configure();
    }

    public C loadConfigurationFromProperties() {
        return manager.load();
    }

    public C loadConfigurationFromProperties(String filePath, String... alternativeFilePaths) {
        return manager.load(filePath, alternativeFilePaths);
    }

    protected final void addConfigurationGroups(List<ConfigurationGroup> groups) {
        groups.add(databaseConfiguration);
        groups.add(emailConfiguration);
        groups.add(accountList);
        groups.add(simulation);
        groups.add(this);

        ConfigurationGroup[] additionalGroups = getAdditionalConfigurationGroups();
        if (additionalGroups != null) {
            Collections.addAll(groups, additionalGroups);
        }
    }

    protected ConfigurationGroup[] getAdditionalConfigurationGroups() {
        return new ConfigurationGroup[0];
    }

    public DatabaseConfiguration database() {
        return databaseConfiguration;
    }

    public EmailConfiguration mailSender() {
        return emailConfiguration;
    }

    public Simulation simulation() {
        return simulation;
    }

    public T account() {
        return accountList.account();
    }

    public T account(String accountId) {
        return accountList.account(accountId);
    }

    public List<T> accounts() {
        return accountList.accounts();
    }

    public TimeInterval tickInterval() {
        return tickInterval;
    }

    public C tickInterval(TimeInterval tickInterval) {
        this.tickInterval = tickInterval;
        return (C)this;
    }

    @Override
    public boolean isConfigured() {
        return tickInterval != null;
    }

    @Override
    public final void readProperties(PropertyBasedConfiguration properties) {
        this.tickInterval = TimeInterval.fromString(properties.getProperty("tick.interval"));
    }

    protected abstract T newAccountConfiguration(String id);
}