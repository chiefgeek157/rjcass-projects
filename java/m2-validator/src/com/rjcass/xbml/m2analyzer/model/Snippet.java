package com.rjcass.xbml.m2analyzer.model;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rjcass.xbml.m2analyzer.M2AnalyzerException;

public class Snippet
{
	private Element mElement;
	private String mPath;

	public Snippet(String path)
	{
		mPath = path;
	}

	public String getPath()
	{
		return mPath;
	}

	public String getType()
	{
		return mElement.getTagName();
	}

	public String toString()
	{
		return "Snippet[path=" + mPath + "]";
	}

	public String getId()
	{
		return mElement.getAttribute(XBMLConst.ATTR_ID);
	}

	public void setElement(Element elem)
	{
		mElement = elem;
	}

	public String getChildId(String tag)
	{
		return getChildId(mElement, tag);
	}

	public String getChildValue(String tag)
	{
		return getChildValue(mElement, tag);
	}

	public Set<String> getGrandchildIds(String childTag, String grandchildTag)
	{
		Set<String> grandchildren = new HashSet<String>();
		Element childElem = getChild(mElement, childTag);
		if (childElem != null)
		{
			NodeList grandchildElemList = childElem.getElementsByTagName(grandchildTag);
			for (int i = 0; i < grandchildElemList.getLength(); i++)
			{
				grandchildren.add(getId(grandchildElemList.item(i)));
			}
		}
		return grandchildren;
	}

	public Set<Profile> getProfiles()
	{
		Set<Profile> profiles = new HashSet<Profile>();
		Element profilesElem = getChild(mElement, XBMLConst.TAG_PROFILES);
		if (profilesElem != null)
		{
			NodeList profileList = getChildren(profilesElem, XBMLConst.TAG_PROFILE);
			for (int i = 0; i < profileList.getLength(); i++)
			{
				Profile profile = new Profile();
				profiles.add(profile);
				profile.setId(getId(profileList.item(i)));
				profile.setTypeId(getId(getChild(profileList.item(i), XBMLConst.TAG_TYPE)));

				Element attributes = getChild(profileList.item(i), XBMLConst.TAG_ATTRIBUTES);
				if (attributes != null)
				{
					NodeList attributeList = getChildren(attributes, XBMLConst.TAG_ATTRIBUTE);
					for (int j = 0; j < attributeList.getLength(); j++)
					{
						Element type = getChild(attributeList.item(j), XBMLConst.TAG_TYPE);
						if (type != null)
							profile.addAttributeType(getId(type));
					}
				}
			}
		}
		return profiles;
	}

	private String getChildId(Element elem, String tag)
	{
		return getId(getChild(elem, tag));
	}

	private String getChildValue(Element elem, String tag)
	{
		String value = null;
		Element childElem = getChild(elem, tag);
		if (childElem != null)
			value = childElem.getNodeValue();
		return value;
	}

	private Element getChild(Node node, String tag)
	{
		return getChild((Element)node, tag);
	}

	private Element getChild(Element elem, String tag)
	{
		return (Element)getChildren(elem, tag).item(0);
	}

	private NodeList getChildren(Element elem, String tag)
	{
		return elem.getElementsByTagName(tag);
	}

	private String getId(Node node)
	{
		return getId((Element)node);
	}

	private String getId(Element elem)
	{
		String id = null;
		if (elem != null)
		{
			id = elem.getAttribute(XBMLConst.ATTR_ID);
			if (id == null)
				throw new M2AnalyzerException("Missing id attribute for " + elem);
		}
		return id;
	}

	public class Profile
	{
		private String mId;
		private String mTypeId;
		private Set<String> mAttributeTypes;

		public Profile()
		{
			mAttributeTypes = new HashSet<String>();
		}

		public String getId()
		{
			return mId;
		}

		public void setId(String id)
		{
			mTypeId = id;
		}

		public String getTypeId()
		{
			return mTypeId;
		}

		public void setTypeId(String id)
		{
			mTypeId = id;
		}

		public Set<String> getAttributeTypes()
		{
			return mAttributeTypes;
		}

		public void addAttributeType(String type)
		{
			mAttributeTypes.add(type);
		}
	}
}
