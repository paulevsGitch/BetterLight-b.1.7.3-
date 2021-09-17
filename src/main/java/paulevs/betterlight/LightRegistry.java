package paulevs.betterlight;

import net.modificationstation.stationloader.api.common.event.Event;
import net.modificationstation.stationloader.api.common.factory.EventFactory;

public interface LightRegistry {
	Event<LightRegistry> EVENT = EventFactory.INSTANCE.newEvent(LightRegistry.class, (listeners) -> {
		return () -> {
			LightRegistry[] var1 = listeners;
			int var2 = listeners.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				LightRegistry event = var1[var3];
				event.registerLightSources();
			}

		};
	});

	void registerLightSources();
}
