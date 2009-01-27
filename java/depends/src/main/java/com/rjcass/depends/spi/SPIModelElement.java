package com.rjcass.depends.spi;

import com.rjcass.depends.ModelElement;

public interface SPIModelElement extends ModelElement
{
    void setModel(SPIModel model);
}
