package com.rjcass.depends2.spi;

import com.rjcass.depends2.Dependency;

public interface SPIDependency extends Dependency
{
    void addDetail(SPIDependency dependency);

    void removeDetail(SPIDependency dependency);
}
