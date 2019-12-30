package br.melo.bruno;

import br.melo.bruno.service.ITimerScheduler;
import br.melo.bruno.service.ITimerSchedulerService;
import br.melo.bruno.service.TimerSchedulerContext;
import br.melo.bruno.service.TimerSchedulerSettings;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class TimerSchedulerTest {

	@Autowired
	ITimerSchedulerService timerSchedulerService;

	private AtomicBoolean ended = new AtomicBoolean(Boolean.FALSE);
	private AtomicBoolean started = new AtomicBoolean(Boolean.FALSE);
	private AtomicBoolean stopped = new AtomicBoolean(Boolean.FALSE);
	private Long beat = 0L;

	@BeforeEach
	void setup() {
		ended = new AtomicBoolean(Boolean.FALSE);
		started = new AtomicBoolean(Boolean.FALSE);
		stopped = new AtomicBoolean(Boolean.FALSE);
		beat = 0L;
	}

	@Test
	void scheduleTest() {
		ITimerScheduler<Map<String, String>> callback = new ITimerScheduler<Map<String, String>>() {
			@Override
			public void ended(TimerSchedulerContext<Map<String, String>> _context) {
				ended.set(Boolean.TRUE);
			}

			@Override
			public void stopped(TimerSchedulerContext<Map<String, String>> _context) {
			}

			@Override
			public void started(TimerSchedulerContext<Map<String, String>> _context) {
				started.set(Boolean.TRUE);
			}

			@Override
			public void beat(TimerSchedulerContext<Map<String, String>> _context) {
				beat++;
			}
		};

		Calendar calendar = Calendar.getInstance();
		UUID uuid = timerSchedulerService.schedule(
				new TimerSchedulerSettings(
						calendar.getTime(),
						2L,
						1000L
				),
				callback
		);
		await().
				atMost(2, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				untilTrue(started);
		await().
				atMost(5, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				untilTrue(ended);

		Assert.assertEquals(2L, beat.longValue());
	}

	@Test
	void stopTest() {
		ITimerScheduler<Map<String, String>> callback = new ITimerScheduler<Map<String, String>>() {
			@Override
			public void ended(TimerSchedulerContext<Map<String, String>> _context) {
			}

			@Override
			public void stopped(TimerSchedulerContext<Map<String, String>> _context) {
				stopped.set(Boolean.TRUE);
			}

			@Override
			public void started(TimerSchedulerContext<Map<String, String>> _context) {
				started.set(Boolean.TRUE);
			}

			@Override
			public void beat(TimerSchedulerContext<Map<String, String>> _context) {
			}
		};
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		UUID uuid = timerSchedulerService.schedule(
				new TimerSchedulerSettings(
						calendar.getTime(),
						30L,
						1000L
				),
				callback
		);
		timerSchedulerService.stop(uuid);
		await().
				atMost(2, TimeUnit.SECONDS).
				pollInterval(1, TimeUnit.SECONDS).
				untilTrue(stopped);
	}


}
