package com.stock.exercise.model;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.stock.exercise.model.manager.StockManager;
import com.stock.exercise.model.manager.StockManagerImpl;
import com.stock.exercise.model.stock.Stock;
import com.stock.exercise.model.stock.StockFactory;
import com.stock.exercise.model.stock.StockImpl;

public class StockModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StockManager.class).to(StockManagerImpl.class);
        install(new FactoryModuleBuilder()
                .implement(Stock.class, StockImpl.class)
                .build(StockFactory.class));
    }
}
