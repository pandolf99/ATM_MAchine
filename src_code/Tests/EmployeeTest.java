package Tests;

import TopClasses.BillHandler;
import UserClasses.Users.BankManager;
import UserClasses.Users.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {
    private Employee e1;

    @Before
    public void setUp() {
        e1 = new Employee("employee1", "pass1");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addBills(){
        int[] bills = new BillHandler().getBills();
        e1.addBills(100000, 0, 17, 0);
        assertArrayEquals(new int[]{bills[0] + 100000, bills[1], bills[2] + 17, bills[3]}, new BillHandler().getBills());

    }


    @Test
    public void requestAccount(){
        e1.requestAccount("chequing", "C");
        assertFalse(new BankManager().getRequests().isEmpty());
    }
}