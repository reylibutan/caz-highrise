package dto;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root
public class IdDto {

	@Text(required = false)
	private Long value;
	
	@Attribute(required = false)
	private String type;

	@Attribute(required = false)
	private boolean nil;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNil() {
		return nil;
	}

	public void setNil(boolean nil) {
		this.nil = nil;
	}
}