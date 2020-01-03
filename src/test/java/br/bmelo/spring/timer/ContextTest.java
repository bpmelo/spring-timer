package br.bmelo.spring.timer;

import br.bmelo.spring.timer.service.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class ContextTest implements ITimerScheduler {

	@Autowired
	TimerSchedulerService timerSchedulerService;

	private Boolean started = Boolean.FALSE;

	@Test
	void contextTest() {
		Calendar calendar = Calendar.getInstance();

		Map<String, String> properties = new HashMap<>();
		properties.put("_CUSTOM_CONTEXT_KEY", "_CUSTOM_CONTEXT_VALUE");

		UUID uuid = timerSchedulerService.schedule(
				new TimerSchedulerSettings(
						calendar.getTime(),
						2L,
						1000L
				),
				this,
				properties
		);

		await().
				atMost(2, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				until(() -> {return started;});

		Assert.assertEquals(
				"_CUSTOM_CONTEXT_VALUE",
				((Map<String, String>)timerSchedulerService.properties(uuid)).get("_CUSTOM_CONTEXT_KEY")
		);
	}

	@Override
	public void ended(Map _context) {
	}

	@Override
	public void stopped(Map _context) {
	}

	@Override
	public void started(Map _context) {
		started = Boolean.TRUE;
	}

	@Override
	public void beat(Map _context) {
	}
}
