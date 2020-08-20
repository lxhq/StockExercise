package com.stock.exerice.model;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.stock.exerice.model.manager.StockManager;
import com.stock.exerice.model.manager.StockManagerImpl;
import com.stock.exerice.model.stock.Stock;
import com.stock.exerice.model.stock.StockFactory;
import com.stock.exerice.model.stock.StockImpl;

public class StockModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StockManager.class).to(StockManagerImpl.class);
        install(new FactoryModuleBuilder()
                .implement(Stock.class, StockImpl.class)
                .build(StockFactory.class));
    }
}
