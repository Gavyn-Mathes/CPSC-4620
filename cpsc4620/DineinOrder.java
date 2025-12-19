package cpsc4620;

import java.io.IOException;
import java.sql.SQLException;

public class DineinOrder extends Order{
	
	private int TableNum;
	
	public DineinOrder(int orderID, int custID, String date, double custPrice, double busPrice, int isComplete, int tablenum) {
		super(orderID, custID, DBNinja.dine_in, date, custPrice, busPrice, isComplete);
		this.TableNum = tablenum;
	}

	public int getTableNum() {
		return TableNum;
	}

	public void setTableNum(int tableNum) {
		TableNum = tableNum;
	}
	
	@Override
	public String toString() {
		return super.toString() + " | Customer was sat at table number " + TableNum;
	}

	@Override
	public String toSimplePrint() {
		boolean iscomplete = super.getIsComplete() != 0;
        return "OrderID=" + super.getOrderID() + " | Date Placed= " + this.getDate() + " | Customer name= INSTORE customer" + ", OrderType= " + super.getOrderType() + ", IsComplete= " + iscomplete;
	}
}
