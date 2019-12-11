package br.melo.bruno;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
class TimerControllerTest extends BaseTest {

	@Autowired
	SimpleDateFormat sdf;

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void postTimer() {
		String uri = getServerUri("/timer/create/{startDateTime}").toString();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		Date startDateTime = calendar.getTime();

		ResponseEntity<String> _response =
				restTemplate
						.exchange(
								getServerUri("/timer/create") + "/{_email}",
								HttpMethod.GET,
								null,
								String.class,
								sdf.format(startDateTime)
						);
		Assert.assertEquals(HttpStatus.OK.value(), _response.getStatusCodeValue());
		Assert.assertNotNull(_response.getBody());
	}

}
