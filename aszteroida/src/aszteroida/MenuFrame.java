package aszteroida;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Cs�ki
 *
 */

public class MenuFrame extends JFrame {
	/**
	 * A Framen tal�lhat� 2 gomb, �s a Settlerek sz�m�t be�ll�t� TextField
	 */
	private JButton newGameBt, quitBt;
	private JTextField SettlerNum;
	/**
	 * Az elind�tand� GameFrame
	 */
	private GameFrame gf;
	/**
	 * a j�t�kot vez�rl� main
	 */
	private Main main;
	
	/**
	 * Konstruktor
	 */
	
	public MenuFrame() {
		super("AsteroidMining Menu");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initMenuComponents();
		this.setSize(350,350);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
	/**
	 * A Menun tal�lhat� elemek l�trehoz�sa
	 */
	@SuppressWarnings({"serial"})
	public void initMenuComponents() {
		this.setLayout(new BorderLayout());
	/**
	 * Panel, h�tt�re egy k�p
	 */
		JPanel menuP = new JPanel() {
			public void paintComponent(Graphics g) {  
				try {
					Image bg = ImageIO.read(new File("images/bg7.png"));
					 g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		menuP.setBorder(new EmptyBorder(30,100,30,100));
		//menuP.setBorder(new EmptyBorder(5,5,30,200));
		menuP.setLayout(new GridLayout(3,1,50,60));
		setContentPane(menuP);
		/**
		 * Az �j j�t�kot elind�t� gomb
		 * A telepesek minimum sz�ma 1, maximuma 20
		 * Ha elindult a j�t�k az ablak bez�r�dik
		 */
		newGameBt = new JButton();
		newGameBt.setText("NEW GAME");
		newGameBt.setForeground(Color.WHITE);
		newGameBt.setBackground(new Color(14, 86, 141));
		newGameBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = SettlerNum.getText().replaceAll("[^0-9]", "");
				if(!temp.equals("")) {
					int num = Integer.parseInt(temp);
					if(num > 20) num = 20;
					if(num > 0) {
						gf=GameFrame.Instance(num);
						gf.setVisible(true);
						main.Init(num, gf);
						dispose();
					}
				}
			}
		});
		menuP.add(newGameBt);
		
		/**
		 * A telepesek sz�ma, minden karakter ami nem sz�m, nem is fog belesz�m�tani az �rt�k�be
		 */
		SettlerNum = new JTextField();
		SettlerNum.setBackground(new Color(14, 86, 141));
		SettlerNum.setForeground(Color.WHITE);
		SettlerNum.setText("SETTLERS : 1");
		SettlerNum.setFont(newGameBt.getFont());
		menuP.add(SettlerNum);
		/**
		 * Kil�p�st megval�s�t� gomb
		 */
		quitBt = new JButton();
		quitBt.setText("QUIT");
		quitBt.setForeground(Color.WHITE);
		quitBt.setBackground(new Color(14, 86, 141));
		quitBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuP.add(quitBt);
		menuP.setBackground(new Color(229, 194, 159));
		menuP.setVisible(true);		
	}
}
