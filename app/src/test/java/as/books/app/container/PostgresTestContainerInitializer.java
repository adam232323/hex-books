package as.books.app.container;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresTestContainerInitializer extends DbContainerInitializer {

    private static PostgreSQLContainer container;

    @Override
    protected String profile() {
        return "test-postgres";
    }

    @Override
    protected boolean containerIsRunning() {
        return container != null;
    }

    @Override
    protected void initContainer() {
        container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2")).withDatabaseName("hex_books")
        .withUrlParam("characterEncoding", "utf8");

        container.start();
    }

    @Override
    protected String getUrl() {
        return container.getJdbcUrl();
    }

    @Override
    protected String getUsername() {
        return container.getUsername();
    }

    @Override
    protected String getPassword() {
        return container.getPassword();
    }
}
