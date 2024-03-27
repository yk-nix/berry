package com.vroad.app.berry.data.repository;

import com.vroad.app.berry.data.datasource.AbstractDataSource;
import com.vroad.app.berry.net.RetrofitService;

public abstract class AbstractRepository<T extends AbstractDataSource<? extends RetrofitService<?>>> {
  protected T dataSource;

  protected AbstractRepository(T dataSource) {
    this.dataSource = dataSource;
  }
}
