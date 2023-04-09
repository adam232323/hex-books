package as.books.app.container;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

abstract class DbContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ac) {
        final Set<String> activeProfiles = Set.of(ac.getEnvironment().getActiveProfiles());
        if (!activeProfiles.contains(profile())) {
            return;
        }

        if (!containerIsRunning()) {
            initContainer();
            runMigrations();
        }

        TestPropertyValues values = TestPropertyValues.of("spring.datasource.url=" + getUrl(),
                                                          "spring.datasource.password=" + getUsername(),
                                                          "spring.datasource.username=" + getPassword());
        values.applyTo(ac);
    }

    protected abstract String profile();

    protected abstract boolean containerIsRunning();

    protected abstract void initContainer();

    protected abstract String getUrl();

    protected abstract String getUsername();

    protected abstract String getPassword();

    private void runMigrations() {
        try (final Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            Database
                    database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("db/liquibase-changelog.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
