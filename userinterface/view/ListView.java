package userinterface.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;

public abstract class ListView extends View implements ListSelectionListener{

	private static final long serialVersionUID = 771887742917121575L;
	
	protected JTable table;
	
	protected ListView(Controller controller, String title) {
		super(controller, title);
	}

	protected ListView(Controller controller, String title, String[] buttonNames) {
		super(controller, title, buttonNames);
	}
	
	@Override
	protected void build(){
		buildFilterForm();
		
		table = createTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(new Dimension(900, 325));
		table.setAutoCreateRowSorter(true);
		table.getSelectionModel().addListSelectionListener(this);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == table && e.getClickCount() == 2) {
					select();
				}
			}
		});
		add(new JScrollPane(table));
	}
	
	@Override
	public void afterShown() {
		processListSelection();
	}
	
	protected abstract void buildFilterForm();
	
	protected abstract JTable createTable();
	
	protected abstract void select();

	protected abstract void processListSelection();
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		processListSelection();
	}

}
