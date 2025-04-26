package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(ShopService shopService, FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int quantity = fruitTransaction.getQuantity();
        int currentQuantity = shopService.getReportData().getOrDefault(fruit, 0);
        shopService.updateStorage(fruit, quantity - currentQuantity);
    }
}
