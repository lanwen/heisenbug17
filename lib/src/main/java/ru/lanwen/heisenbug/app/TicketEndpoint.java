package ru.lanwen.heisenbug.app;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.io.IOUtils;
import ru.lanwen.heisenbug.wiremock.WiremockCustomizer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class TicketEndpoint implements WiremockCustomizer {
    @Override
    public void customize(WireMockServer server) {
        String uuid = UUID.randomUUID().toString();

        server.stubFor(
                post(urlPathEqualTo("/ticket"))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withHeader("X-Ticket-ID", uuid))
        );

        server.stubFor(
                get(urlPathEqualTo("/ticket/" + uuid))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("X-Ticket-ID", uuid)
                                .withBody(cp("ticket.json"))
                        )
        );
    }

    private static String cp(String path) {
        try (InputStream is = TicketEndpoint.class.getClassLoader().getResourceAsStream(path)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
