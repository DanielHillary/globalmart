import com.verraki.services.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.verraki.models.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockMonitorTest {

    private StockManager stockMonitor;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Product product1;
    private Product product2;
    private Supplier supplier1;
    private Supplier supplier2;

    @BeforeEach
    void setUp() {
        warehouse1 = new Warehouse(1, "North America");
        warehouse2 = new Warehouse(2, "Europe");

        product1 = new Product(1, "Product 1", 10, 20);
        product2 = new Product(2, "Product 2", 15, 30);

        supplier1 = new Supplier(1, "Supplier A", 5, true);
        supplier2 = new Supplier(2, "Supplier B", 7, true);

        warehouse1.addProduct(1, 100);
        warehouse1.addProduct(2, 200);

        warehouse2.addProduct(1, 50);
        warehouse2.addProduct(2, 150);

        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2);
        List<Product> products = Arrays.asList(product1, product2);
        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);

        stockMonitor = new StockManager(warehouses, products, suppliers);
    }

    @Test
    void testReorderTriggered() {
        // Set up stock levels to be below reorder point
        warehouse1.setStockLevel(1, 5);
        warehouse2.setStockLevel(2, 10);

        StockManager mockMonitor = spy(stockMonitor);
        doNothing().when(mockMonitor).placeOrder(any(), any(), anyInt());

        mockMonitor.monitorAndReorder();

        // Verify that placeOrder is called
//        verify(mockMonitor, times(1)).placeOrder(any(Supplier.class), eq(product1), anyInt());
        verify(mockMonitor, times(1)).placeOrder(any(Supplier.class), eq(product2), anyInt());
    }

    @Test
    void testNoReorderNeeded() {
        // Set up stock levels to be above reorder point
        warehouse1.setStockLevel(2, 200);
        warehouse2.setStockLevel(1, 200);

        StockManager mockMonitor = spy(stockMonitor);
        doNothing().when(mockMonitor).placeOrder(any(), any(), anyInt());

        mockMonitor.monitorAndReorder();

        // Verify that placeOrder is not called
        verify(mockMonitor, never()).placeOrder(any(), any(), anyInt());
    }

    @Test
    void testSupplierSelection() {
        Supplier mockSupplier = mock(Supplier.class);
        when(mockSupplier.getLeadTime()).thenReturn(3);
        when(mockSupplier.isAvailable()).thenReturn(true);

        List<Supplier> suppliers = Arrays.asList(mockSupplier, supplier1, supplier2);
        stockMonitor = new StockManager(Arrays.asList(warehouse1, warehouse2), Arrays.asList(product1, product2), suppliers);

        Supplier selectedSupplier = stockMonitor.selectSupplier();

        assertEquals(mockSupplier, selectedSupplier);
    }

    @Test
    void testNoAvailableSupplier() {
        Supplier unavailableSupplier = new Supplier(3, "Supplier C", 10, false);
        List<Supplier> suppliers = Arrays.asList(unavailableSupplier);

        stockMonitor = new StockManager(Arrays.asList(warehouse1, warehouse2), Arrays.asList(product1, product2), suppliers);

        // When there are no available suppliers, selectSupplier should return null
        Supplier selectedSupplier = stockMonitor.selectSupplier();
        assertNull(selectedSupplier);
    }

    @Test
    void testErrorHandling() {
        StockManager spyMonitor = spy(stockMonitor);
        doThrow(new RuntimeException("Order placement failed")).when(spyMonitor).placeOrder(any(), any(), anyInt());

        warehouse1.setStockLevel(1, 5);

        try {
            spyMonitor.monitorAndReorder();
        } catch (RuntimeException e) {
            // Expected exception, do nothing
        }
        // Verify that the error was handled
        verify(spyMonitor, times(1)).placeOrder(any(), any(), anyInt());
    }

    @Test
    void testProductNotFound() {
        // Setting up to check exception handling when product is not found
        assertThrows(IllegalArgumentException.class, () -> {
            stockMonitor.getProductById(999);
        });
    }
}
