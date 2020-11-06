package fr.dla.app.earthquakes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Earthquakes.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private UsgsEarthQuakesApi usgsEarthQuakesApi;

    public static class UsgsEarthQuakesApi {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public UsgsEarthQuakesApi getUsgsEarthQuakesApi() {
        return usgsEarthQuakesApi;
    }

    public void setUsgsEarthQuakesApi(UsgsEarthQuakesApi usgsEarthQuakesApi) {
        this.usgsEarthQuakesApi = usgsEarthQuakesApi;
    }
}
