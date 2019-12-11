package br.melo.bruno;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

	@LocalServerPort
	protected int _PORT;

	private String _SCHEMA = "http";
	private String _URL_ADDRESS = "localhost";

	protected URI getServerUri(String _path) {
		UriComponents _uri = UriComponentsBuilder.newInstance()
				.scheme(_SCHEMA)
				.host(_URL_ADDRESS)
				.port(_PORT)
				.path(_path)
				.build();
		return (_uri.toUri());
	}
}
