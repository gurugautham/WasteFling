import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class LegendPanel extends JPanel implements ActionListener{

	private JButton back;
	private Scanner inFile;

	public LegendPanel(){

		setLayout(null);

		back = new JButton("Home");
		//back.setBounds((int)(HomeScreen.WIDTH*0.1), (int)(HomeScreen.HEIGHT*0.8), 50, 50);
		back.setBackground(new Color(0,250,100, 255));
		back.setBorderPainted(false);
		add(back);
		back.addActionListener(this);
		back.setBounds((int)(HomeScreen.WIDTH*0.5)-34, 5, 69, 26);

		repaint();

		//Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
		//		{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };


		// return 2, 3
		//Object rowData[][]= new Object[][3];

		Object columnNames[] = { "Name", "Image", "Sort" };
		Object rowData[][] = generateAll();
		System.out.println(rowData.length+ ", " + rowData[0].length);

		DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
		JTable table = new JTable( model )
		{
			//  Returning the Class of each column will allow different
			//  renderers to be used based on Class
			public Class getColumnClass(int column)
			{
				return getValueAt(0, column).getClass();
			}
		};
		updateRowHeights(table);

		LegendRenderer centerRenderer = new LegendRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );

		table.setBackground(new Color(156, 137, 196));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50,50,HomeScreen.WIDTH-100,HomeScreen.HEIGHT-100);
		add(scrollPane);
		scrollPane.setBackground(new Color(156, 137, 196));
	}

	public Object[][] generateAll(){
		try {
			inFile = new Scanner(new File("catalog.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int lineNumber = 0;

		Object rowData[][] = new Object[WasteGenerator.numOfCompostables+WasteGenerator.numOfGarbage+WasteGenerator.numOfHazardous+WasteGenerator.numOfRecyclables][3];

		//NAME & TYPE
		int typeCount=0;
		JLabel name;
		JLabel recyclable, garbage, compostable, hazardous;

		recyclable = new JLabel("Recyclable", SwingConstants.CENTER);
		recyclable.setForeground(new Color(169, 216, 247));
		recyclable.setFont(new Font(recyclable.getFont().getName(), Font.PLAIN, 24));

		garbage = new JLabel("Garbage", SwingConstants.CENTER);
		garbage.setForeground(new Color(207,207,207));
		garbage.setFont(new Font(garbage.getFont().getName(), Font.PLAIN, 24));

		compostable = new JLabel("Compostable", SwingConstants.CENTER);
		compostable.setForeground(new Color(155,255,113));
		compostable.setFont(new Font(compostable.getFont().getName(), Font.PLAIN, 24));

		hazardous = new JLabel("Hazardous Waste", SwingConstants.CENTER);
		hazardous.setForeground(new Color(255, 70, 70));
		hazardous.setFont(new Font(hazardous.getFont().getName(), Font.PLAIN, 24));



		for(int i = 0; i<rowData.length; i++){

			String currentLine = inFile.nextLine();
			if (currentLine.indexOf(':')>0){
				typeCount++;
				currentLine = inFile.nextLine();
			}

			name = new JLabel(currentLine, SwingConstants.CENTER);
			name.setFont(new Font(name.getFont().getName(), Font.BOLD, 24));
			rowData[i][0] = name;
			System.out.println(currentLine);
			rowData[i][1] = scaleImage(WasteGenerator.imageCatalog.get(currentLine));
			if(typeCount ==1){
				rowData[i][2] = recyclable;
			}
			else if(typeCount == 2) rowData[i][2] = garbage;
			else if(typeCount==3) rowData[i][2] = compostable;
			else if(typeCount==4) rowData[i][2] = hazardous;

		}

		return rowData;



	}

	public void paintComponent(Graphics g){
		g.drawImage(HomeScreen.BgImage, 0, 0, HomeScreen.WIDTH, HomeScreen.HEIGHT, this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)){
			HomeScreen.cards.show(HomeScreen.cardPanel, "Home");
		}
	}

	private void updateRowHeights(JTable table)
	{
		for (int row = 0; row < table.getRowCount(); row++)
		{
			int rowHeight = table.getRowHeight();

			for (int column = 0; column < table.getColumnCount(); column++)
			{
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height)+10;

			}

			table.setRowHeight(row, rowHeight);
		}
	}

	public ImageIcon scaleImage(Image toLoad){
		//Scale Logic
		int fitSizeW = 100;
		int fitSizeH = 100;
		int imageWidth = toLoad.getWidth(null);
		int imageHeight = toLoad.getHeight(null);
		//Hamburger
		if(imageWidth>imageHeight){
			int imageRatio = imageWidth/imageHeight;
			int projectedHeight = (int)(fitSizeW/imageRatio);
			return new ImageIcon(toLoad.getScaledInstance(fitSizeW, (int) projectedHeight, Image.SCALE_DEFAULT));

		}

		//Portrait
		else{
			int imageRatio = imageHeight/imageWidth;
			int projectedWidth = (int)(fitSizeH/imageRatio);
			return new ImageIcon(toLoad.getScaledInstance((int) projectedWidth, fitSizeH, Image.SCALE_DEFAULT));
		}

	}

}

class LegendRenderer extends DefaultTableCellRenderer {

	JLabel lbl = new JLabel();
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		if(value instanceof JLabel) return(JLabel)value;
		else return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}



}

