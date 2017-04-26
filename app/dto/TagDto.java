package dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tag")
public class TagDto {

	@Element
	private IdDto id;

	@Element
	private String name;

	public IdDto getId() {
		return id;
	}

	public void setId(IdDto id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}