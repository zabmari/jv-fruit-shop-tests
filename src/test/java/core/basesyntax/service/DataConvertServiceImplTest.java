package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConvertServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConvertServiceImplTest {

    private final DataConvertServiceImpl dataConvertService = new DataConvertServiceImpl();

    @Test
    void convertTransactions_validLines_shouldReturnCorrectTransactions() {
        List<String> lines = List.of("type,fruit,quantity",
                "b,apple,10",
                "p,banana,5",
                "r,orange,2",
                "s,kiwi,15");

        List<FruitTransaction> transactions = dataConvertService.convertTransactions(lines);
        assertNotNull(transactions, "Transactions list should not be null");
        assertEquals(4, transactions.size(), "Should convert 4 transactions");

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(2).getOperation());
        assertEquals("orange", transactions.get(2).getFruit());
        assertEquals(2, transactions.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(3).getOperation());
        assertEquals("kiwi", transactions.get(3).getFruit());
        assertEquals(15, transactions.get(3).getQuantity());
    }

    @Test
    void convertTransactions_emptyLines_shouldReturnEmptyList() {
        List<String> lines = List.of("type,fruit,quantity");
        List<FruitTransaction> transactions = dataConvertService.convertTransactions(lines);
        assertNotNull(transactions, "Transactions list should not be null");
        assertTrue(transactions.isEmpty(), "Should return an empty list");
    }

    @Test
    void convertTransactions_invalidNumberOfElements_shouldThrowRuntimeException() {
        List<String> lines = List.of("type,fruit,quantity", "BALANCE,apple");
        assertThrows(RuntimeException.class,
                () -> dataConvertService.convertTransactions(lines),
                "Should throw RuntimeException for invalid number of elements");
    }

    @Test
    void convertTransactions_invalidOperation_shouldThrowIllegalArgumentException() {
        List<String> lines = List.of("type,fruit,quantity", "ADD,orange,3");
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertService.convertTransactions(lines),
                "Should throw IllegalArgumentException for invalid operation");
    }

    @Test
    void convertTransactions_invalidQuantityFormat_shouldThrowRuntimeException() {
        List<String> lines = List.of("type,fruit,quantity", "SUPPLY,kiwi,many");
        assertThrows(RuntimeException.class,
                () -> dataConvertService.convertTransactions(lines),
                "Should throw RuntimeException for invalid quantity format");
    }
}
