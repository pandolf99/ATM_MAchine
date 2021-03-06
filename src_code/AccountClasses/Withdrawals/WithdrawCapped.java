package AccountClasses.Withdrawals;

import TopClasses.BillHandler;

import java.io.Serializable;

public class WithdrawCapped implements IWithdrawable, Serializable {
    double minBalance;
    double maxBalance;
    public WithdrawCapped(int minBalance, int maxBalance){
        this.minBalance = minBalance;
        this.maxBalance = maxBalance;
    }

    public double withdraw(int amount, double balance){
        if (balance-amount >= minBalance & balance-amount <= maxBalance){
            new BillHandler().withdrawBills(amount/5);
            return balance - amount;
        }
        else {
            System.out.println("You don't have enough money remaining to withdraw that much! Balance: " + balance);
            return balance;
        }
    }
}
