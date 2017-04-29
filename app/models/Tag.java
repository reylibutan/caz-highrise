package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import dto.TagDto;
import play.db.jpa.GenericModel;

@Entity
public class Tag extends GenericModel {

	@Id
	public Long id;
	public String name;

	@ManyToMany(mappedBy = "tags")
	public Set<Person> persons = new HashSet<>();

	public static Tag convert(TagDto dto) {
		Tag tag = new Tag();
		tag.id = dto.getId().getValue();
		tag.name = dto.getName();

		// ignore Set<Person> persons

		return tag;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}
}