package dto;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

public class TagListDto {

	@Attribute(required = false)
	private String type;

	@Attribute(required = false)
	@ElementList(inline = true)
	private List<TagDto> tags;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TagDto> getTags() {
		return tags;
	}

	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}
}