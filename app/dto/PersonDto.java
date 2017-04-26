package dto;

import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "person", strict = false)
public class PersonDto {

	@Element
	private IdDto id;

	@Element(name = "author-id")
	private IdDto authorId;

	@Element(name = "group-id")
	private IdDto groupId;

	@Element(name = "company-id")
	private IdDto companyId;

	@Element(name = "owner-id")
	private IdDto ownerId;

	@Element(required = false)
	private String background;

	@Element(name = "first-name", required = false)
	private String firstName;

	@Element(name = "last-name", required = false)
	private String lastName;

	@Element(required = false)
	private String title;

	@Element(name = "created-at")
	private Date createdAt;

	@Element(name = "updated-at")
	private Date updatedAt;

	@Element(name = "visible-to")
	private String visibleTo;

	@Element(name = "company-name", required = false)
	private String companyName;

	@Element(name = "linkedin-url", required = false)
	private String linkedinUrl;

	@Element(name = "avatar_url")
	private String avatarUrl;

	@Element(name = "tags")
	private TagListDto tagList;

	/*
	 <contact-data>
        <instant-messengers type="array" />
        <phone-numbers type="array" />
        <twitter-accounts type="array" />
        <web-addresses type="array" />
        <addresses type="array" />
        <email-addresses type="array" />
     </contact-data>
	 */

	public IdDto getId() {
		return id;
	}

	public void setId(IdDto id) {
		this.id = id;
	}

	public IdDto getAuthorId() {
		return authorId;
	}

	public void setAuthorId(IdDto authorId) {
		this.authorId = authorId;
	}

	public IdDto getGroupId() {
		return groupId;
	}

	public void setGroupId(IdDto groupId) {
		this.groupId = groupId;
	}

	public IdDto getCompanyId() {
		return companyId;
	}

	public void setCompanyId(IdDto companyId) {
		this.companyId = companyId;
	}

	public IdDto getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(IdDto ownerId) {
		this.ownerId = ownerId;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getVisibleTo() {
		return visibleTo;
	}

	public void setVisibleTo(String visibleTo) {
		this.visibleTo = visibleTo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public TagListDto getTagList() {
		return tagList;
	}

	public void setTagList(TagListDto tagList) {
		this.tagList = tagList;
	}
}