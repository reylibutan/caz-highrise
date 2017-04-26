package dto;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

public class PersonListDto {

	@Attribute(required = false)
	private String type;

	@Attribute(required = false)
	@ElementList(inline = true)
	private List<PersonDto> persons;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PersonDto> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonDto> persons) {
		this.persons = persons;
	}	
}