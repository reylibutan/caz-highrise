package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.jpa.GenericModel;

@Entity
public class Tag extends GenericModel {

	@Id
	public Long id;
	public String name;

	@ManyToMany(mappedBy = "tags")
	public Set<Person> persons = new HashSet<>();
}