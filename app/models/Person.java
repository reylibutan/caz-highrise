package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import dto.PersonDto;
import play.db.jpa.GenericModel;

@Entity
public class Person extends GenericModel  {

	@Id
	public Long id;
	public Long authorId;
	public String background;
	public Long companyId;
	public Date createdAt;
	public String firstName;
	public String lastName;
	public Long groupId;
	public Long ownerId;
	public String title;
	public Date updatedAt;
	public String companyName;
	public String linkedinUrl;
	public String avatarUrl;

	@ManyToMany
	@JoinTable(name = "person_tag")
	public Set<Tag> tags = new HashSet<>();

	/*
	 * instructions said to not use any 3rd party libs, so I'm doing this manually
	 * without the instruction, I could have used an object mapping lib like mapstruct
	 */
	public static Person convert(PersonDto dto) {
		Person person = new Person();
		person.id = dto.getId().getValue();
		person.authorId = dto.getAuthorId().getValue();
		person.background = dto.getBackground();
		person.companyId = dto.getCompanyId().getValue();
		person.createdAt = dto.getCreatedAt();
		person.firstName = dto.getFirstName();
		person.lastName = dto.getLastName();
		person.groupId = dto.getGroupId().getValue();
		person.ownerId = dto.getOwnerId().getValue();
		person.title = dto.getTitle();
		person.updatedAt = dto.getUpdatedAt();
		person.companyName = dto.getCompanyName();
		person.linkedinUrl = dto.getLinkedinUrl();
		person.avatarUrl = dto.getAvatarUrl();

		return person;
	}

	@Override
	public String toString() {
		return "Person [personId=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}