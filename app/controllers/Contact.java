package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.simpleframework.xml.Serializer;

import dto.PersonDto;
import dto.PersonListDto;
import dto.TagDto;
import dto.TagListDto;
import models.Person;
import play.db.jpa.NoTransaction;
import play.mvc.Controller;
import utils.AppConstants;
import utils.HighRiseClient;

/*
 * ============================================================================
 * TODO
 * 		> Shouldn't I name controllers as FooController?
 * 		> Shouldn't I separate controllers base on functionality, treating them as resources? ex. CompanyController, PersonController vs. ApplicationController
 * 		> If I use XController naming, how to customize view folders so that I should not be required to name them as "views/ApplicationController" and just "views/Application"
 * 
 * 
 * 		1. Do security (login2 using username password / account URL)
 * 		2. Make Filter for setting request media type (XML)
 * 		3. Make a static method in HighRiseClient -> HighRiseClient.invoke(URL, params) returns String
 * ============================================================================
 */
public class Contact extends Controller {

	@NoTransaction
	public static void index() {
		retrieve();
	}

	@NoTransaction
	public static void retrieve() {
		render();
	}

	public static void doRetrieve(String tag) {
		validation.required(tag);

		if (!validation.hasErrors()) {
			Long tagId = getTagId(tag);
			if (tagId == null) {
				// ================================================================
				// TODO - how to get this from messages?
				// ================================================================
				validation.addError(AppConstants.FIELD_TAG, AppConstants.VALIDATION_DOES_NOT_EXIST, AppConstants.FIELD_TAG);
			} else {
				// retrieve all contacts using tag ID
				Response resp = null;
				WebTarget peopleTarget = HighRiseClient.getContextTarget().path(HighRiseClient.URI_PEOPLE).queryParam(HighRiseClient.QUERY_PARAM_TAG_ID, tagId);

				PersonListDto personList = null;
				Serializer serializer = HighRiseClient.getSerializer();

				try {
					resp = peopleTarget.request(MediaType.APPLICATION_XML_TYPE).get();
					String peopleResponse = resp.readEntity(String.class);

					personList = serializer.read(PersonListDto.class, peopleResponse);

					for(PersonDto personDto : personList.getPersons()) {
						Person person = Person.convert(personDto);
						person = person.merge();
						person.save();
					}

					System.out.println(Person.count());
				} catch (Exception e) {
					// ===================================================================
					// TODO - do logging
					// ===================================================================
					e.printStackTrace();
				} finally {
					resp.close();
				}
			}
		}

		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			retrieve();

			// ===================================================================
			// TODO - retain "tag" value so textbox is still populated after error
			// ===================================================================
		}
	}

	private static Long getTagId(String tagName) {
		// retrieve all tags from Highrise to determine tag ID
		Response resp = null;
		WebTarget tagsTarget = HighRiseClient.getContextTarget().path(HighRiseClient.URI_TAGS);

		Long tagId = null;
		TagListDto tagList = null;
		Serializer serializer = HighRiseClient.getSerializer();

		try {
			resp = tagsTarget.request(MediaType.APPLICATION_XML_TYPE).get();
			String tagResponse = resp.readEntity(String.class);

			tagList = serializer.read(TagListDto.class, tagResponse);
		} catch (Exception e) {
			// ===================================================================
			// TODO - do logging
			// ===================================================================
			e.printStackTrace();
		} finally {
			if (resp != null) {
				resp.close();
			}
		}

		if (!(tagName == null || tagName.isEmpty()) || tagList == null || tagList.getTags() == null) {
			for (TagDto tag : tagList.getTags()) {
				if (tagName.equals(tag.getName())) {
					tagId = tag.getId().getValue();
					break;
				}
			}
		}

		return tagId;
	}

	private static List<Long> getPersonIds(List<PersonDto> personList) {
		List<Long> personIdList = new ArrayList<>();

		for (PersonDto person : personList) {
			personIdList.add(person.getId().getValue());
		}

		return personIdList;
	}
	
}