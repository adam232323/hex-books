package as.books.app;

import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

public class ActiveProfileResolver implements ActiveProfilesResolver {

    private final DefaultActiveProfilesResolver defaultActiveProfilesResolver = new DefaultActiveProfilesResolver();

    @Override
    public String[] resolve(final Class<?> testClass) {

        if (System.getProperties().containsKey("spring.profiles.active")) {

            final String profiles = System.getProperty("spring.profiles.active");
            return profiles.split("\\s*,\\s*");
        } else {

            return defaultActiveProfilesResolver.resolve(testClass);
        }
    }
}
