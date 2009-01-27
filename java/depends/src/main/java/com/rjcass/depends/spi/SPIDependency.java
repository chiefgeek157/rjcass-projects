package com.rjcass.depends.spi;

import com.rjcass.depends.Dependency;

public interface SPIDependency extends Dependency
{
    void addDetail(SPIDependency dependency);

    void removeDetail(SPIDependency dependency);
}
