package ru.lanwen.heisenbug.wiremock;

import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import ru.lanwen.heisenbug.WiremockResolver;

/**
 * You can create custom config to init wiremock server in test.
 *
 * @author lanwen (Merkushev Kirill)
 * @see WiremockResolver.Server
 */
public interface WiremockConfigFactory {

    /**
     * Create config to be used by injected to test method wiremock
     *
     * @return config for wiremock
     */
    WireMockConfiguration create();

    /**
     * By default creates config with dynamic port only and notifier.
     */
    class DefaultWiremockConfigFactory implements WiremockConfigFactory {

        @Override
        public WireMockConfiguration create() {
            return WireMockConfiguration.options().dynamicPort().notifier(new Slf4jNotifier(true));
        }
    }
}
