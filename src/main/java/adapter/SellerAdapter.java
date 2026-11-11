package adapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Registered;
import domain.Sale;

public class SellerAdapter extends AbstractTableModel{
	protected Registered seller;
	protected String[] columnNames = new String[] {"Title", "Description", "Price", "Status"};
	protected List<Sale> sales;
	
	
	public SellerAdapter (Registered seller) {
		this.seller = seller;
		this.sales = seller.getSales();
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return sales.size();
	}
	
	 public String getColumnName(int i) {
		  if (i < 0 || i > 3) return "";
		  return columnNames[i];
	  }

	@Override
	public Object getValueAt(int row, int col) {
		if (col < 0 || col > 3) return "";
		else if (col == 0) return sales.get(row).getTitle();
		else if (col == 1) return sales.get(row).getDescription();
		else if (col == 2) return sales.get(row).getPrice();
		else {
			if(sales.get(row).getStatus() == 0) return "Txarra";
			if(sales.get(row).getStatus() == 1) return "Erabilia";
			if(sales.get(row).getStatus() == 2) return "Ona";
			return "Oso ona";
		}
	}

}
