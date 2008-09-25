package com.rjcass.xbml.m2analyzer.model;

import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public class XBMLEntityFactory
{
	private static Log sLog = LogFactory.getLog(XBMLEntityFactory.class);

	public XBMLEntityFactory()
	{}

	public XBMLEntity createEntity(Map<String, XBMLEntity> entitiesById, Snippet snippet)
	{
		XBMLEntity entity;
		if (XBMLConst.TAG_ATTRIBUTE_STYLE == snippet.getType())
		{
			entity = createAttributeStyle(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_ATTRIBUTE_TYPE == snippet.getType())
		{
			entity = createAttributeType(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_LINK == snippet.getType())
		{
			entity = createLink(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_LINK_BEHAVIOR == snippet.getType())
		{
			entity = createLinkBehavior(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_LINK_STYLE == snippet.getType())
		{
			entity = createLinkStyle(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_NODE == snippet.getType())
		{
			entity = createNode(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_NODE_BEHAVIOR == snippet.getType())
		{
			entity = createNodeBehavior(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_NODE_STYLE == snippet.getType())
		{
			entity = createNodeStyle(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_OBJECT == snippet.getType())
		{
			entity = createObject(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_PROFILE_TYPE == snippet.getType())
		{
			entity = createProfileType(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_PROJECT == snippet.getType())
		{
			entity = createProject(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_RELATIONSHIP == snippet.getType())
		{
			entity = createRelationship(entitiesById, snippet);
		}
		else if (XBMLConst.TAG_VIEW == snippet.getType())
		{
			entity = createView(entitiesById, snippet);
		}
		else
		{
			throw new M2AnalyzerException("Unknown snippet type: " + snippet.getType());
		}

		entity.setPath(snippet.getPath());

		return entity;
	}

	private XBMLAttributeStyle createAttributeStyle(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLAttributeStyle style = getEntity(XBMLAttributeStyle.class, entities, snippet.getId());
		style.setType(getEntity(XBMLAttributeType.class, entities, snippet.getChildId(XBMLConst.TAG_ATTRIBUTE_TYPE)));
		return style;
	}

	private XBMLAttributeType createAttributeType(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLAttributeType type = getEntity(XBMLAttributeType.class, entities, snippet.getId());
		type.setName(snippet.getChildValue(XBMLConst.TAG_NAME));
		return type;
	}

	private XBMLLink createLink(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLLink link = getEntity(XBMLLink.class, entities, snippet.getId());
		link.setBehavior(getEntity(XBMLLinkBehavior.class, entities, snippet.getChildId(XBMLConst.TAG_BEHAVIOR)));
		link.setDestination(getEntity(XBMLNode.class, entities, snippet.getChildId(XBMLConst.TAG_DESTINATION)));
		link.setOrigin(getEntity(XBMLNode.class, entities, snippet.getChildId(XBMLConst.TAG_ORIGIN)));
		link.setRelationship(getEntity(XBMLRelationship.class, entities, snippet.getChildId(XBMLConst.TAG_RELATIONSHIP)));
		link.setStyle(getEntity(XBMLLinkStyle.class, entities, snippet.getChildId(XBMLConst.TAG_STYLE)));
		String targetId = snippet.getChildId(XBMLConst.TAG_TARGET);
		if (targetId != null)
			link.setTarget(getEntity(XBMLLink.class, entities, targetId));
		return link;
	}

	private XBMLLinkBehavior createLinkBehavior(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLLinkBehavior behavior = getEntity(XBMLLinkBehavior.class, entities, snippet.getId());
		return behavior;
	}

	private XBMLLinkStyle createLinkStyle(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLLinkStyle style = getEntity(XBMLLinkStyle.class, entities, snippet.getId());
		return style;
	}

	private XBMLNode createNode(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLNode node = getEntity(XBMLNode.class, entities, snippet.getId());
		String anchorId = snippet.getChildId(XBMLConst.TAG_ANCHOR);
		if (anchorId != null)
			node.setAnchor(getEntity(XBMLNode.class, entities, anchorId));
		Set<String> styleIds = snippet.getGrandchildIds(XBMLConst.TAG_ATTRIBUTE_STYLES, XBMLConst.TAG_ATTRIBUTE_STYLE);
		for (String styleId : styleIds)
		{
			node.addAttributeStyle(getEntity(XBMLAttributeStyle.class, entities, styleId));
		}
		node.setBehavior(getEntity(XBMLNodeBehavior.class, entities, snippet.getChildId(XBMLConst.TAG_BEHAVIOR)));
		node.setNodeStyle(getEntity(XBMLNodeStyle.class, entities, snippet.getChildId(XBMLConst.TAG_STYLE)));
		node.setObject(getEntity(XBMLObject.class, entities, snippet.getChildId(XBMLConst.TAG_OBJECT)));

		addProfiles(entities, snippet, node);

		String targetId = snippet.getChildId(XBMLConst.TAG_TARGET);
		if (targetId != null)
			node.setTarget(getEntity(XBMLNode.class, entities, targetId));
		return node;
	}

	private XBMLNodeBehavior createNodeBehavior(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLNodeBehavior behavior = getEntity(XBMLNodeBehavior.class, entities, snippet.getId());
		return behavior;
	}

	private XBMLNodeStyle createNodeStyle(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLNodeStyle style = getEntity(XBMLNodeStyle.class, entities, snippet.getId());
		return style;
	}

	private XBMLObject createObject(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLObject object = getEntity(XBMLObject.class, entities, snippet.getId());
		object.setName(snippet.getChildValue(XBMLConst.TAG_NAME));
		String profileTypeId = snippet.getChildId(XBMLConst.TAG_PROFILE_TYPE);
		if (profileTypeId != null)
			object.setProfileType(getEntity(XBMLProfileType.class, entities, profileTypeId));

		addProfiles(entities, snippet, object);

		return object;
	}

	private XBMLProfileType createProfileType(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLProfileType profileType = getEntity(XBMLProfileType.class, entities, snippet.getId());
		profileType.setName(snippet.getChildValue(XBMLConst.TAG_NAME));
		Set<String> typeIds = snippet.getGrandchildIds(XBMLConst.TAG_VALID_ATTRIBUTE_TYPES, XBMLConst.TAG_ATTRIBUTE_TYPE);
		for (String typeId : typeIds)
		{
			profileType.addAttributeType(getEntity(XBMLAttributeType.class, entities, typeId));
		}
		return profileType;
	}

	private XBMLProject createProject(Map<String, XBMLEntity> entitiesById, Snippet snippet)
	{
		XBMLProject project = getEntity(XBMLProject.class, entitiesById, snippet.getId());
		project.setName(snippet.getChildValue(XBMLConst.TAG_NAME));
		return project;
	}

	private XBMLRelationship createRelationship(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLRelationship rel = getEntity(XBMLRelationship.class, entities, snippet.getId());
		rel.setName(snippet.getChildValue(XBMLConst.TAG_NAME));
		rel.setDestination(getEntity(XBMLObject.class, entities, snippet.getChildId(XBMLConst.TAG_DESTINATION)));
		rel.setOrigin(getEntity(XBMLObject.class, entities, snippet.getChildId(XBMLConst.TAG_ORIGIN)));

		addProfiles(entities, snippet, rel);

		return rel;
	}

	private XBMLView createView(Map<String, XBMLEntity> entities, Snippet snippet)
	{
		XBMLView view = getEntity(XBMLView.class, entities, snippet.getId());
		view.setName(snippet.getChildValue(XBMLConst.TAG_NAME));
		String rootItemId = snippet.getChildId(XBMLConst.TAG_ROOT_ITEM);
		if (rootItemId != null)
			view.setRootItem(getEntity(XBMLNode.class, entities, rootItemId));
		return view;
	}

	private <T extends XBMLEntity> T getEntity(Class<T> cls, Map<String, XBMLEntity> entities, String id)
	{
		XBMLEntity entity = entities.get(id);
		T typedEntity = null;
		try
		{
			typedEntity = cls.cast(entity);
		}
		catch (ClassCastException e)
		{
			sLog.warn("Found entity " + entity + " when a " + cls.getSimpleName() + " was expected");
		}
		if (entity == null)
		{
			try
			{
				typedEntity = cls.newInstance();
				typedEntity.setId(id);
				entities.put(id, typedEntity);
			}
			catch (InstantiationException e)
			{
				throw new M2AnalyzerException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new M2AnalyzerException(e);
			}
		}
		return typedEntity;
	}

	private void addProfiles(Map<String, XBMLEntity> entities, Snippet snippet, XBMLProfiledEntity entity)
	{
		Set<Snippet.Profile> profiles = snippet.getProfiles();
		for (Snippet.Profile profileInfo : profiles)
		{
			XBMLProfile profile = getEntity(XBMLProfile.class, entities, profileInfo.getId());
			entity.addProfile(profile);
			profile.setType(getEntity(XBMLProfileType.class, entities, profileInfo.getTypeId()));
			for (String attrId : profileInfo.getAttributeTypes())
			{
				profile.addAttributeType(getEntity(XBMLAttributeType.class, entities, attrId));
			}
		}
	}
}
