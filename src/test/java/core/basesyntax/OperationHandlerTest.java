package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.OperationStrategyServiceImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {

    private ShopService shopService;
    private final String testFruit = "apple";
    private final int testQuantity = 5;

    @BeforeEach
    void setUp() {
        OperationStrategyServiceImpl operationStrategyService = new OperationStrategyServiceImpl();
        shopService = new ShopServiceImpl(operationStrategyService);
    }

    @Test
    void balanceOperation_shouldUpdateStorage() {
        BalanceOperation balanceOperation = new BalanceOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, testFruit, testQuantity);
        balanceOperation.handle(shopService, transaction);
        assertEquals(testQuantity, shopService.getReportData().get(testFruit),
                "Balance operation should set the quantity in storage.");
    }

    @Test
    void supplyOperation_shouldIncreaseQuantity() {
        BalanceOperation balanceOperation = new BalanceOperation();
        FruitTransaction initialTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, testFruit, 10);
        balanceOperation.handle(shopService, initialTransaction);
        SupplyOperation supplyOperation = new SupplyOperation();
        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, testFruit, testQuantity);
        supplyOperation.handle(shopService, supplyTransaction);
        assertEquals(10 + testQuantity,
                shopService.getReportData().get(testFruit),
                "Supply operation should increase the quantity in storage.");
    }

    @Test
    void supplyOperation_shouldAddNewFruitIfNotFound() {
        SupplyOperation supplyOperation = new SupplyOperation();
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, testFruit, testQuantity);
        supplyOperation.handle(shopService, fruitTransaction);
        assertEquals(testQuantity, shopService.getReportData().get(testFruit),
                "Supply operation should add new fruit to storage.");
    }

    @Test
    void purchaseOperation_shouldDecreaseQuantityIfEnoughStock() {
        BalanceOperation balanceOperation = new BalanceOperation();
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, testFruit, 10);
        balanceOperation.handle(shopService, fruitTransaction);
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, testFruit, testQuantity);
        purchaseOperation.handle(shopService, purchaseTransaction);
        assertEquals(10 - testQuantity,
                shopService.getReportData().get(testFruit),
                "Purchase operation should decrease the quantity in storage.");
    }

    @Test
    void purchaseOperation_shouldThrowExceptionIfNotEnoughStock() {
        BalanceOperation balanceOperation = new BalanceOperation();
        FruitTransaction initialTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, testFruit, testQuantity);
        balanceOperation.handle(shopService, initialTransaction);
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, testFruit, 10);
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(
                shopService, purchaseTransaction),
                "Purchase operation should throw exception for insufficient stock.");
    }

    @Test
    void returnOperation_shouldIncreaseQuantity() {
        BalanceOperation balanceOperation = new BalanceOperation();
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, testFruit, 15);
        balanceOperation.handle(shopService, fruitTransaction);
        ReturnOperation returnOperation = new ReturnOperation();
        FruitTransaction returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, testFruit, testQuantity);
        returnOperation.handle(shopService, returnTransaction);
        assertEquals(15 + testQuantity,
                shopService.getReportData().get(testFruit),
                "Return operation should increase the quantity in storage.");
    }

    @Test
    void returnOperation_shouldAddNewFruitIfNotFound() {
        ReturnOperation returnOperation = new ReturnOperation();
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, testFruit, testQuantity);
        returnOperation.handle(shopService, fruitTransaction);
        assertEquals(testQuantity, shopService.getReportData().get(testFruit),
                "Return operation should add new fruit to storage.");
    }
}
