package ru.lanwen.heisenbug;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.heisenbug.WiremockAddressResolver.Uri;
import ru.lanwen.heisenbug.WiremockResolver.Server;
import ru.lanwen.heisenbug.app.TicketEndpoint;

/**
 * @author lanwen (Merkushev Kirill)
 */
@ExtendWith({
        WiremockResolver.class,
        WiremockAddressResolver.class
})
public class EticketResourceTest {

    @Test
    void shouldCreateTicket(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {

    }
}
