package ru.lanwen.heisenbug.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import ru.lanwen.heisenbug.WiremockResolver;


/**
 * Helps to create reusable customizer for injected wiremock server
 *
 * @author lanwen (Merkushev Kirill)
 * @see WiremockResolver.Server
 */
public interface WiremockCustomizer {

    void customize(final WireMockServer server);

    class NoopWiremockCustomizer implements WiremockCustomizer {
        @Override
        public void customize(final WireMockServer server) {
            // noop
        }
    }
}
