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
import models.Tag;
import play.Logger;
import play.db.jpa.NoTransaction;
import play.i18n.Messages;
import play.mvc.Controller;
import utils.AppConstants;
import utils.HighRiseClient;

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
		validation.clear();
		validation.required(tag);

		if (!validation.hasErrors()) {
			Long tagId = getTagId(tag);
			if (tagId == null) {
				validation.addError(AppConstants.FIELD_TAG, AppConstants.ERROR_MSG_DOES_NOT_EXIST, AppConstants.FIELD_TAG);
			} else {
				// retrieve all contacts using tag ID
				Response resp = null;
				WebTarget peopleTarget = HighRiseClient.getContextTarget().path(HighRiseClient.URI_PEOPLE).queryParam(HighRiseClient.QUERY_PARAM_TAG_ID, tagId);

				PersonListDto personList = null;
				Serializer serializer = HighRiseClient.getSerializer();

				List<String> retrievedPersonNames = new ArrayList<>();

				try {
					resp = peopleTarget.request(MediaType.APPLICATION_XML_TYPE).get();
					String peopleResponse = resp.readEntity(String.class);
					personList = serializer.read(PersonListDto.class, peopleResponse);
					for (PersonDto personDto : personList.getPersons()) {
						Person person = Person.convert(personDto);

						for (Tag tagModel : person.tags) {
							tagModel.merge();
						}

						person = person.merge();
						person.save();

						retrievedPersonNames.add(((person.firstName == null ? "" : person.firstName) + " " + (person.lastName == null ? "" : person.lastName)).trim());
					}

					renderArgs.put(AppConstants.PARAM_SUCCESS_MSG, Messages.get(AppConstants.SUCCESS_CONTACT_RETRIEVAL, tag));
					renderArgs.put(AppConstants.PARAM_RETRIEVED_PERSON_NAMES, retrievedPersonNames);
				} catch (Exception e) {
					validation.addError(null, AppConstants.ERROR_MSG_GENERAL);
					Logger.error(e, Messages.get(AppConstants.ERROR_MSG_GENERAL));
				} finally {
					if (resp != null) {
						resp.close();
					}
				}
			}
		}

		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
		}

		// retrieve();
		render("@retrieve");
	}

	// ========================================================================
	// TODO - support multiselect tag
	// ========================================================================
	public static void list(Long searchTagId) {
		List<Person> personList = new ArrayList<>();

		if (AppConstants.DEFAULT_SEARCH_TAG_ID.equals(searchTagId)) {
			// ================================================================
			// TODO - pagination
			// ================================================================
			personList = Person.findAll();
		} else {
			personList = Person.findByTagId(searchTagId);
		}

		renderArgs.put(AppConstants.PARAM_TAG_LIST, Tag.findAll());
		renderArgs.put(AppConstants.PARAM_PERSON_LIST, personList);

		params.flash();
		render();
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
			Logger.error(e, Messages.get(AppConstants.ERROR_MSG_GENERAL));
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
}