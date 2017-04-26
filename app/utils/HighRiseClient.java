package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.hibernate.criterion.Example;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

public class HighRiseClient {
	
	public static String URI_TAGS = "tags";
	public static String URI_PEOPLE = "people";
	
	public static String QUERY_PARAM_TAG_ID = "tag_id";

	private static String BASE_URL = "https://reylibutan.highrisehq.com/";
	private static String AUTH_TOKEN = "3327349935ff87b38cad76dfcdaea1ee";
	private static String DEFAULT_PASSWORD = "x"; // can be really just anything

	private static String HIGHRISE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	private static Client instance;
	private static WebTarget contextTarget;
	private static Serializer serializer;

	private HighRiseClient() {

	}

	public static Client getClient() {
		initialize();
		return instance;
	}

	public static WebTarget getContextTarget() {
		initialize();
		return contextTarget;
	}
	
	public static Serializer getSerializer() {
		initialize();
		return serializer;
	}

	private static void initialize() {
		if (instance == null) {
			HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(AUTH_TOKEN, DEFAULT_PASSWORD);
			instance = ClientBuilder.newClient().register(authFeature);
			contextTarget = instance.target(BASE_URL);
			initSerializer();
		}
	}
	
	private static void initSerializer() {
		DateFormat format = new SimpleDateFormat(HIGHRISE_DATE_FORMAT);

		RegistryMatcher regMatcher = new RegistryMatcher();
		regMatcher.bind(Date.class, new DateFormatTransformer(format));

		serializer = new Persister(regMatcher);
	}
}