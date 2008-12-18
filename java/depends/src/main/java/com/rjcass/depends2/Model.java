package com.rjcass.depends2;

import java.io.OutputStream;

public interface Model
{
	String getName();
	Entity getRoot();
    void dump(OutputStream os);
}
