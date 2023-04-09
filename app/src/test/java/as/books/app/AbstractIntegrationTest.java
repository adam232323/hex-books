package as.books.app;

import as.books.app.container.PostgresTestContainerInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;


@ActiveProfiles(value = {"test", "test-default"}, resolver = ActiveProfileResolver.class)
@ContextConfiguration(initializers = {PostgresTestContainerInitializer.class})
@SpringBootTest
@Sql(scripts = "classpath:sql/clean_all_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class AbstractIntegrationTest {}
