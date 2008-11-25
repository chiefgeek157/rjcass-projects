package com.rjcass.depends2;

import java.util.Set;

public interface Relationship
{
	RelationshipType getType();
	int getLevelOfAbstraction();
	Relationship getAbstraction();
	Set<Relationship> getDetails();
	Entity getSource();
	Entity getTarget();
}
