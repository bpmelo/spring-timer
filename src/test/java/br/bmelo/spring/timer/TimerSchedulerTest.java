package br.bmelo.spring.timer;

import br.bmelo.spring.timer.service.ITimerScheduler;
import br.bmelo.spring.timer.service.TimerSchedulerContext;
import br.bmelo.spring.timer.service.TimerSchedulerService;
import br.bmelo.spring.timer.service.TimerSchedulerSettings;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class TimerSchedulerTest implements ITimerScheduler {

	@Autowired
	TimerSchedulerService timerSchedulerService;

	private Boolean ended = Boolean.FALSE;
	private Boolean started = Boolean.FALSE;
	private Boolean stopped = Boolean.FALSE;
	private Long beat = 0L;

	@BeforeEach
	void setup() {
		ended = Boolean.FALSE;
		started = Boolean.FALSE;
		stopped = Boolean.FALSE;
		beat = 0L;
	}

	@Test
	void scheduleTest() {
		Calendar calendar = Calendar.getInstance();
		UUID uuid = timerSchedulerService.schedule(
				new TimerSchedulerSettings(
						calendar.getTime(),
						2L,
						1000L
				),
				this
		);
		await().
				atMost(2, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				until(() -> {return started;});
		await().
				atMost(5, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				until(() -> {return ended;});

		Assert.assertEquals(2L, beat.longValue());
	}

	@Test
	void stopTest() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		UUID uuid = timerSchedulerService.schedule(
				new TimerSchedulerSettings(
						calendar.getTime(),
						30L,
						1000L
				),
				this
		);
		timerSchedulerService.stop(uuid);
		await().
				atMost(2, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				until(() -> {return stopped;});
	}


	@Override
	public void ended(TimerSchedulerContext _context) {
		ended = Boolean.TRUE;
	}

	@Override
	public void stopped(TimerSchedulerContext _context) {
		stopped = Boolean.TRUE;
	}

	@Override
	public void started(TimerSchedulerContext _context) {
		started = Boolean.TRUE;
	}

	@Override
	public void beat(TimerSchedulerContext _context) {
		beat++;
	}
}
