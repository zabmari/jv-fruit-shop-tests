package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationStrategyServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.Test;

public class OperationStrategyServiceImplTest {

    private final OperationStrategyServiceImpl operationStrategyService =
            new OperationStrategyServiceImpl();

    @Test
    void validOperation_returnCorrectHandler_ok() {
        assertInstanceOf(BalanceOperation.class,
                operationStrategyService.getOperationHandler(FruitTransaction.Operation.BALANCE),
                "Should return BalanceOperation for BALANCE.");
        assertInstanceOf(PurchaseOperation.class,
                operationStrategyService.getOperationHandler(FruitTransaction.Operation.PURCHASE),
                "Should return PurchaseOperation for PURCHASE.");
        assertInstanceOf(SupplyOperation.class,
                operationStrategyService.getOperationHandler(FruitTransaction.Operation.SUPPLY),
                "Should return SupplyOperation for SUPPLY.");
        assertInstanceOf(ReturnOperation.class,
                operationStrategyService.getOperationHandler(FruitTransaction.Operation.RETURN),
                "Should return ReturnOperation for RETURN.");
    }

    @Test
    void invalidOperation_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategyService.getOperationHandler(
                        FruitTransaction.Operation.valueOf("UNKNOWN")),
                "Should throw IllegalArgumentException for an unknown operation.");
    }
}
