import org.junit.Before;
import org.junit.Test;

import models.Person;
import play.test.Fixtures;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

	// ========================================================================
	// TODO - Study and write more tests here
	// ========================================================================

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void testPersonCreateAndFind() {
		Person person = new Person();
		person.id = 1L;
		person.firstName = "Foo";
		person.lastName = "Bar";

		person.save();

		Person retrievedPerson = Person.find("byPersonId", 1L).first();

		assertNotNull(retrievedPerson);
		assertEquals("Foo", retrievedPerson.firstName);
		assertEquals("Bar", retrievedPerson.lastName);
	}
}