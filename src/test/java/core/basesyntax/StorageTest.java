package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void addQuantity_newFruit_shouldAddCorrectQuantity() {
        storage.addQuantity("apple", 10);
        assertEquals(10, storage.getQuantity("apple"),
                "Should add new fruit with correct quantity.");
        assertTrue(storage.containsFruit("apple"),
                "Storage should contain the added fruit.");
    }

    @Test
    void addQuantity_existingFruit_shouldIncreaseQuantity() {
        storage.addQuantity("orange", 5);
        storage.addQuantity("orange", 7);
        assertEquals(12, storage.getQuantity("orange"),
                "Should increase quantity of existing fruit.");
    }

    @Test
    void getQuantity_nonExistingFruit_shouldReturnZero() {
        assertEquals(0, storage.getQuantity("banana"),
                "Should return 0 for non-existing fruit.");
    }

    @Test
    void removeQuantity_existingFruit_shouldDecreaseQuantity() {
        storage.addQuantity("apple", 10);
        storage.removeQuantity("apple", 3);
        assertEquals(7, storage.getQuantity("apple"),
                "Should decrease quantity of existing fruit.");
    }

    @Test
    void removeQuantity_nonExistingFruit_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> storage.removeQuantity(
                "strawberry", 3),
                "Should throw IllegalArgumentException for removing non-existing fruit.");
    }

    @Test
    void containsFruit_existingFruit_shouldReturnTrue() {
        storage.addQuantity("orange", 20);
        assertTrue(storage.containsFruit("orange"),
                "Should return true for existing fruit.");
    }

    @Test
    void containsFruit_nonExistingFruit_shouldReturnFalse() {
        assertFalse(storage.containsFruit("grape"),
                "Should return false for non-existing fruit.");
    }

    @Test
    void getFruitQuantities_shouldReturnAllFruits() {
        storage.addQuantity("apple", 10);
        storage.addQuantity("banana", 5);
        assertEquals(2, storage.getFruitQuantities().size(),
                "Should return all added fruits.");
    }
}
