package dasniko.keycloak.events;

import lombok.RequiredArgsConstructor;
import org.keycloak.common.util.Time;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

/**
 * @author Niko Köbler, https://www.n-k.de, @dasniko
 */
@RequiredArgsConstructor
public class LastLoginTimeListener implements EventListenerProvider {

	private final KeycloakSession session;

	@Override
	public void onEvent(Event event) {
		if (event.getType().equals(EventType.LOGIN)) {
			UserModel user = session.users().getUserById(session.getContext().getRealm(), event.getUserId());
			if (user != null) {
				user.setSingleAttribute(LastLoginTimeListenerFactory.attributeName, Integer.toString(Time.currentTime()));
			}
		}
	}

	@Override
	public void onEvent(AdminEvent event, boolean includeRepresentation) {
	}

	@Override
	public void close() {
	}

}
