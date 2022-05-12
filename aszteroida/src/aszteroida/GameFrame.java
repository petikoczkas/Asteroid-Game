package aszteroida;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

/**
 * Ez az oszt�ly felel a j�t�k grafikus megjelen�t�s��rt. Kirajzolja az adott j�t�kos �ltal ir�ny�tott telepest
 * az aszteroid�ra, amin �pp tart�zkodik, tov�bb� a n�la l�v� nyersanyagokat �s teleportkapukat.
 * A telepes ir�ny�t�s�ra szolg�l� gombok mellett kirajzolja m�g az aszteroida szomsz�dait,
 * amire tov�bbl�phet az adott j�t�kos
 * 
 * @author Peti
 *
 */

public class GameFrame extends JFrame{
	/**
	 * V�ltoz�, amely figyeli az adott telepes k�r�nek a v�g�t
	 */
	boolean buttonPushed = false;
	
	
	
	/**
	 * A menus�v megjelen�t�s�re szolg�l� JMenuBar
	 */
	private JMenuBar menubar;
	/**
	 * A j�t�kosok sz�ma
	 */
	private int settlerNum;
	/**
	 * A soron l�v� j�t�kos aszteroid�j�nak szomsz�dsz�ma
	 */
	private int neighnum;
	/**
	 * A szomsz�dok kirajzol�s�n�l ennyi oszlopban lesznek az aszteroid�k/teleportkapuk
	 */
	private int width;
	
	/**
	 * Az �pp ledob�sra kiv�lasztott nyersanyag a telepes inventory�b�l 
	 */
	private int selectedMat=-1;
	/**
	 * Az �pp lerak�sra kiv�lasztott teleportgate a telepes inventory�b�l
	 */
	private int selectedTpGate=-1;
	/**
	 * Singleton use-hoz haszn�lt statikus instance
	 */
	private static GameFrame instance = null;
	
	/**
	 * A soron l�v� telepes
	 */
	private Settler currSettler;
	/**
	 * A m�veleti gombokat befoglal� panel
	 */
	private JPanel butPan;
	/**
	 * Az aktu�lis aszteroid�t befoglal� panel
	 */
	private JPanel astPan;
	/**
	 * Az aktu�lis inventoryt befoglal� panel
	 */
	private JPanel inventoryPan;
	/**
	 * Az aktu�lis szomsz�dokat befoglal� panel
	 */
	private JPanel asterPan;
	
	private Main main;
	/**
	 * A men�b�ron tal�lhat� �zenetek sz�m�ra fenntartott c�mke
	 */
	private JLabel message;
	/**
	 * A megnyomhat� gombok list�ja
	 */
	private ArrayList<JButton> buttons=new ArrayList<JButton>();
	/**
	 * Priv�t konstruktor
	 * @param s a j�t�kosok sz�ma
	 */
	private GameFrame(int s){
		super("Asteroid 1.0");
		settlerNum = s;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		InitComponents();
		setVisible(true);
	}
	/**
	 * A GameFrame singleton p�ld�ny l�trehoz�s�ra �s el�r�s�re haszn�lt met�dus
	 * @return GameFrame objektummal t�r vissza
	 */
	public static GameFrame Instance(int s)
    {
        if (instance == null)
            instance = new GameFrame(s);
        return instance;
    }
	/**
	 * A GameFrame singleton p�ld�ny el�r�s�re haszn�lt met�dus
	 * @return GameFrame objektummal t�r vissza
	 */
	public static GameFrame Instance()
    {
        return instance;
    }
	/**
	 *  Kiv�lasztja a megfelel� k�pet att�l f�gg�en,
	 *  hogy milyen oszt�lyt adunnk �t a f�ggv�nynek �s visszat�r vele
	 * @param obj a kirajzoland� oszt�ly
	 * @return a megfelel� k�p a kirajzol�shoz
	 */
	private String GetFileName(Object obj) {
		
		switch(obj.getClass().getSimpleName()) {
			case "Settler":
				return "images/astronaut.png";
			case "Robot":
				return "images/robot.png";
			case "Ufo":
				return "images/ufo.png";
			case "Aluminium":
				return "images/alu.png";
			case "Carbon":
				return "images/coal.png";
			case "Copper":
				return "images/copper.png";
			case "Gold":
				return "images/gold.png";
			case "Ice":
				return "images/ice.png";
			case "Iron":
				return "images/iron.png";
			case "Uranium":
				return "images/uran.png";
			case "Asteroid":
				if(((Asteroid) obj).GetLayerNum() == 0) {
					
					return "images/drillast.png";
				}
				else {
					return "images/ast.png";
				}
			case "Base":
				return "images/base.png";
			case "TeleportGate":
				return "images/tpgate.png";
			default:
				return "";
		}
	}
	/**
	 * Be�ll�tja, hogy h�ny oszlop sz�ks�ges a szomsz�dok kirajzol�s�hoz
	 */
	private void SetWidth() {
		if(neighnum<=6) width=2;
		else{
			double c=Math.ceil(((double)neighnum)/6);
			width=(int)c;
		}
	}
	/**
	 * Ez a met�dus inicializ�lja a megjelen�t�st.
	 * L�trehozza �s be�ll�tja a sz�ks�ges paneleket a kirajzol�shoz.
	 */
	private void InitComponents(){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		JPanel pane = new JPanel() {public void paintComponent(Graphics g) {  
            try {
                Image bg = ImageIO.read(new File("images/bg6.png"));
                 g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);  
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
        };
		pane.setLayout(new GridBagLayout());
		pane.setBackground(Color.black);
		add(pane);
		
		menubar = new JMenuBar();
		menubar.setBackground(Color.WHITE);
		JMenu newgame = new JMenu("New Game");
		JMenuItem same = new JMenuItem("Settlers : " + String.valueOf(settlerNum));
		same.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.gf.setVisible(false);	
				main.ReInit(settlerNum, main.gf);
				buttonPushed=true;
			}
		});
		JMenuItem five = new JMenuItem("Settlers : 5");
		five.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.gf.setVisible(false);	
				main.ReInit(5, main.gf);
				buttonPushed=true;
			}
		});
		JMenuItem ten = new JMenuItem("Settlers : 10");
		ten.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.gf.setVisible(false);	
				main.ReInit(10, main.gf);
				buttonPushed=true;
			}
		});
		
		JMenuItem current = new JMenuItem("Current Settler");
		JMenuItem all = new JMenuItem("Overview");
		current.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					
			}
		});
		all.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		newgame.add(same);
		newgame.add(five);
		newgame.add(ten);
		menubar.add(newgame);
		message = new JLabel("  ");
		menubar.add(message);

		this.setJMenuBar(menubar);
		
		astPan = new JPanel(new BorderLayout(10,10));
		JPanel invPan = new JPanel(new BorderLayout(10,10));
		butPan = new JPanel();
		butPan.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		butPan.setLayout(new GridLayout(2,3,10,10));
		JPanel neiPan = new JPanel(new BorderLayout(10,10));
		
		astPan.setOpaque(false);
		astPan.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
		invPan.setOpaque(false);
		invPan.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
		butPan.setOpaque(false);
		neiPan.setOpaque(false);
		neiPan.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));

		JLabel caLabel = new JLabel("CURRENT SETTLER:");
		caLabel.setFont(new Font("Courier new", Font.BOLD, 16));
		caLabel.setForeground(Color.white);
		astPan.add(caLabel, BorderLayout.PAGE_START);


		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.ipady = 170;
		c.ipadx = 170;
		pane.add(astPan, c);
		
		JLabel iLabel = new JLabel("INVENTORY:");
		iLabel.setFont(new Font("Courier new", Font.BOLD, 16));
		iLabel.setForeground(Color.white);
		invPan.add(iLabel, BorderLayout.PAGE_START);
		
		
		inventoryPan= new JPanel(new GridLayout(3,5));
		inventoryPan.setOpaque(false);
		invPan.add(inventoryPan, BorderLayout.CENTER);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 160;
		pane.add(invPan, c);
		
		
		
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 20;
		pane.add(butPan, c);
		
		JLabel neiLabel = new JLabel("NEIGHBOURS:");
		neiLabel.setFont(new Font("Courier new", Font.BOLD, 16));
		neiLabel.setForeground(Color.white);
		neiPan.add(neiLabel, BorderLayout.PAGE_START);
		

		
		asterPan = new JPanel(new GridBagLayout());
		asterPan.setPreferredSize(new Dimension(width*300,400));
		asterPan.setOpaque(false);
		asterPan.revalidate();

		JScrollPane neighbours= new JScrollPane(asterPan);
		neighbours.setOpaque(false);
		neighbours.getViewport().setOpaque(false);
		neighbours.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		neighbours.revalidate();
		neiPan.add(neighbours, BorderLayout.CENTER);
		
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		c.ipadx = 530;
		c.ipady = 530;

		pane.add(neiPan, c);

	}
	
	/**
	 * Be�ll�tja az adott gomb param�tereit
	 * @param b Az adott gomb aminek be�ll�tja a param�tereit
	 */
	private void SetButton(JButton b) {
		b.setFont(new Font("courier new", Font.BOLD, 12));
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setForeground(Color.WHITE);
		b.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
	}
	
	/**
	 * �jrarajzolja az ablakot, ha m�dos�tunk rajta
	 */
	private void Reset() {
		this.repaint();
	}
	
	/**
	 * Hozz�adjuk a megfelel� panelhez a m�veleti gombokat �s be�ll�tja,
	 * hogy kattint�s hat�s�ra mi t�rt�njen
	 */
	private void AddButtons() {
		JButton drillButton=new JButton("drill");
		SetButton(drillButton);
		butPan.add(drillButton);
		buttons.add(drillButton);
		drillButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currSettler.Drill()) {
					Reset();
					buttonPushed = true;
				}
				
			}
		});
		
		JButton mineButton=new JButton("mine");
		SetButton(mineButton);
		butPan.add(mineButton);
		buttons.add(mineButton);
		mineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currSettler.Mine()) {
					AddInventory(currSettler);
					Reset();
					buttonPushed = true;
				}
			}
		});
		
		JButton dropButton=new JButton("drop");
		SetButton(dropButton);
		butPan.add(dropButton);
		buttons.add(dropButton);
		dropButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedMat!=-1) {
					if(currSettler.Remove(currSettler.GetInventory().get(selectedMat))) {
						AddInventory(currSettler);
						Reset();
						buttonPushed = true;
					}
					selectedMat=-1;
				}
				else Message("There is no selected material");
			}
		});

		JButton crobotButton=new JButton("<html>craft<br>robot</html>");
		SetButton(crobotButton);
		butPan.add(crobotButton);
		buttons.add(crobotButton);
		crobotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currSettler.CreateRobot()) {
					AddInventory(currSettler);
					Reset();
					buttonPushed = true;
				}
				
			}
		});
		
		JButton ctpgateButton=new JButton("<html>craft<br>tpgate</html>");
		SetButton(ctpgateButton);
		butPan.add(ctpgateButton);
		buttons.add(ctpgateButton);
		ctpgateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currSettler.CreateTpGates()) {
					AddInventory(currSettler);
					Reset();
					buttonPushed = true;
				}
			}
		});
		
		JButton ptpgateButton=new JButton("<html>place<br>tpgate</html>");
		SetButton(ptpgateButton);
		butPan.add(ptpgateButton);
		buttons.add(ptpgateButton);
		ptpgateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedTpGate!=-1) {
					currSettler.PlaceTpGate(currSettler.GetTpGates().get(selectedTpGate));
					AddInventory(currSettler);
					AddNeighbour(currSettler);
					SetWidth();
					asterPan.setPreferredSize(new Dimension(width*300,400));
					Reset();
					buttonPushed = true;
					selectedTpGate=-1;
				}
				else Message("There is no selected Teleportgate");
			}
		});
		
	}
	
	/**
	 * Kirajzolja az aktu�lis aszteroid�t befoglal� panelre a sz�ks�ges k�peket
	 * @param s A soron l�v� telepes
	 */
	private void AddCurrent(Settler s) {
		if(s.GetPlace().getClass().getSimpleName().equals("Asteroid") || s.GetPlace().getClass().getSimpleName().equals("Base")) {
			neighnum = s.GetPlace().GetNeighbours().size();
			JPanel caPanel=new JPanel(){public void paintComponent(Graphics g) {  
				try {
					Image ast = ImageIO.read(new File(GetFileName(s.GetPlace())));
			        g.drawImage(ast, 6, 30, this.getWidth(), this.getHeight(), this); 
	                if(s.GetPlace().GetMaterial() != null && s.GetPlace().GetLayerNum()==0) {
	                	Image mat = ImageIO.read(new File(GetFileName(s.GetPlace().GetMaterial())));
				        g.drawImage(mat, 105, 95, this.getWidth()/8, this.getHeight()/8, this);
	                }
	                if(s.GetPlace().inStorm) {
	                	Image lgt = ImageIO.read(new File("images/lightning.png"));
		                g.drawImage(lgt, 210, 0, this.getWidth()/9, this.getHeight()/4, this);
	                }
	                if(((Asteroid)s.GetPlace()).GetInPh()) {
	                	Image sun = ImageIO.read(new File("images/sun.png"));
		                g.drawImage(sun, -10, -15, this.getWidth()/3, this.getHeight()/3, this);
	                }
			        for(Character c:s.GetPlace().characters) {
			        	if(c.getClass().getSimpleName().equals("Robot")) {
			        		Image robot = ImageIO.read(new File(GetFileName(c)));
					        g.drawImage(robot, -15, -10, 150, 150, this);
			        	}
			            if(c.getClass().getSimpleName().equals("Ufo")) {
			            	Image ufo = ImageIO.read(new File(GetFileName(c)));
			            	g.drawImage(ufo, 105, -20, 150, 150, this);
			                }
			            if(c.getClass().getSimpleName().equals("Settler")) {
			                Image set = ImageIO.read(new File(GetFileName(c)));
					        g.drawImage(set, 45, -30, 150, 150, this);
			            }
			        }
				} catch (IOException e) {
					e.printStackTrace();
			    }
			}
		};
		astPan.add(caPanel);
		}
		else{
			if(((TeleportGate)s.GetPlace()).GetPair().GetAsteroid()==null) neighnum=1;
			else neighnum=2;
			JPanel caPanel=new JPanel(){public void paintComponent(Graphics g) {  
				try {
					Image tp = ImageIO.read(new File(GetFileName(s.GetPlace())));
			        g.drawImage(tp, 46, 30, this.getWidth()*2/3, this.getHeight()*2/3, this); 

	                if(s.GetPlace().inStorm) {
	                	Image lgt = ImageIO.read(new File("images/lightning.png"));
		                g.drawImage(lgt, 210, 0, this.getWidth()/9, this.getHeight()/4, this);
	                }
			        for(Character c:s.GetPlace().GetCharacters()) {
			        	if(c.getClass().getSimpleName().equals("Robot")) {
			        		Image robot = ImageIO.read(new File(GetFileName(c)));
					        g.drawImage(robot, -15, -10, 150, 150, this);
			        	}
			            if(c.getClass().getSimpleName().equals("Ufo")) {
			            	Image ufo = ImageIO.read(new File(GetFileName(c)));
			            	g.drawImage(ufo, 105, -20, 150, 150, this);
			                }
			            if(c.getClass().getSimpleName().equals("Settler")) {
			                Image set = ImageIO.read(new File(GetFileName(c)));
					        g.drawImage(set, 45, -30, 150, 150, this);
			            }
			        }
				} catch (IOException e) {
					e.printStackTrace();
			    }
			}
		};
		astPan.add(caPanel);
		}
		
	}
	/**
	 * Kirajzolja az aktu�lis telepes inventory�t �s be�ll�tja a gombokat a megfelel� m�k�d�sre
	 * @param s A soron l�v� telepes
	 */
	private void AddInventory(Settler s) {
		inventoryPan.removeAll();
		//adott inventory
		for(int j=0; j<s.GetInventory().size(); j++) {
			ImageIcon icon=new ImageIcon(new ImageIcon(GetFileName(s.GetInventory().get(j))).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JButton i1=new JButton(icon);
            i1.setName(Integer.toString(j));
			i1.setBackground(new Color(142,163,182));
			i1.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
			inventoryPan.add(i1);
			i1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedMat=Integer.parseInt(i1.getName());
				}
			});
		}
		//kit�lt�s
		for(int j=0; j<10-s.GetInventory().size(); j++) {
			JButton i1=new JButton();
			i1.setEnabled(false);
			i1.setBackground(new Color(142,163,182));
			i1.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
			inventoryPan.add(i1);
		}
		//adott tpgate
		for(int j=0; j<s.GetTpGates().size(); j++) {
			ImageIcon icon=new ImageIcon(new ImageIcon(GetFileName(s.GetTpGates().get(j))).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JButton i1=new JButton(icon);
            i1.setName(Integer.toString(j));
			i1.setBackground(new Color(142,163,182));
			i1.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
			inventoryPan.add(i1);
			i1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedTpGate=Integer.parseInt(i1.getName());
				}
			});
		}
		//kit�lt�s
		for(int j=0; j<3-s.GetTpGates().size(); j++) {
			JButton i1=new JButton();
			i1.setEnabled(false);
			i1.setBackground(new Color(142,163,182));
			i1.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
			inventoryPan.add(i1);
		}
		inventoryPan.revalidate();
		inventoryPan.repaint();
		
	}
	/**
	 * Hozz�ad az aktu�lis szomsz�dokat befoglal� panelhez egy aszteroida k�pet �s a hozz� tartoz� k�peket
	 * @param a Az adott aszteroida
	 * @param c2 A kirajzol�shoz sz�ks�ges GridBagConstraint 
	 */
	private void AddAsteroid(Asteroid a, GridBagConstraints c2) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		String astfn = GetFileName(a);
		String matfn;
		if(a.GetMaterial()!=null) {
			matfn = GetFileName(a.GetMaterial());
		}
		else {
			matfn = "null";
		}
		boolean inPh = a.GetInPh();
		boolean inStorm = a.GetInStorm();
		JPanel asteroid =new JPanel(){public void paintComponent(Graphics g) {  
            try {
            	setOpaque(false);
                Image ast = ImageIO.read(new File(astfn));
                g.drawImage(ast, 0, -10, this.getWidth()/3*2, this.getHeight()/2*3, this);
                if(a.GetLayerNum()==0) {
                	if(!matfn.equals("null")) {
	                	Image mat = ImageIO.read(new File(matfn));
		                g.drawImage(mat, 70, 40, this.getWidth()/3*2/7, this.getHeight()/2*3/7, this);
	                }
                }
                
                
                for(Character c: a.GetCharacters()) {
                	
                	if(c.getClass().getSimpleName().equals("Robot")) {
                		
                		Image robot = ImageIO.read(new File(GetFileName(c)));
		                g.drawImage(robot, 22, -8, this.getWidth()/3/2, this.getHeight()/3*2, this);
	                }
                	if(c.getClass().getSimpleName().equals("Ufo")) {
                		Image ufo = ImageIO.read(new File(GetFileName(c)));
		                g.drawImage(ufo, 102, -8, this.getWidth()/3/2, this.getHeight()/3*2, this);
	                }
                	if(c.getClass().getSimpleName().equals("Settler")) {
                		Image set = ImageIO.read(new File(GetFileName(c)));
		                g.drawImage(set, 62, -8, this.getWidth()/3/2, this.getHeight()/3*2, this);
	                }
                }

                if(inPh) {
                	Image sun = ImageIO.read(new File("images/sun.png"));
	                g.drawImage(sun, 145, 25, this.getWidth()/3, this.getHeight()/3*2, this);
                }
                if(inStorm) {
                	//k�cs��
                	Image lgt = ImageIO.read(new File("images/lightning.png"));
	                g.drawImage(lgt, 225, 30, this.getWidth()/9, this.getHeight()/2, this);
                }
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
        };
        
		//Move gombok
		JButton b=new JButton();
		b.setBackground(new Color(142,163,182));
		b.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
		b.setPreferredSize(new Dimension(30,30));
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currSettler.Move(a);
				AddNeighbour(currSettler);
				neighnum=currSettler.GetPlace().GetNeighbours().size()+((Asteroid)currSettler.GetPlace()).GetTpgate().size();
				SetWidth();
				asterPan.setPreferredSize(new Dimension(width*300,400));
				buttonPushed = true;
			}
		});
		buttons.add(b);
		
		panel.add(b, BorderLayout.LINE_START);
		panel.add(asteroid, BorderLayout.CENTER);
		
		asterPan.add(panel, c2);
	}
	/**
	 * Hozz�ad az aktu�lis szomsz�dokat befoglal� panelhez egy teleportkapu k�pet �s a hozz� tartoz� k�peket
	 * @param tp Az adott teleportkapu
	 * @param c2 A kirajzol�shoz sz�ks�ges GridBagConstraint 
	 */
	private void AddTpGate(TeleportGate tp, GridBagConstraints c2) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		String astfn = GetFileName(tp);
		String charfn;
		if(tp.GetCharacters().size()!=0) {
			charfn = GetFileName(tp.GetCharacters().get(0));
		}
		else {
			charfn = "null";
		}
		boolean inStorm = tp.GetInStorm();
		JPanel asteroid =new JPanel(){public void paintComponent(Graphics g) {  
            try {
            	setOpaque(false);
                Image ast = ImageIO.read(new File(astfn));
                g.drawImage(ast, 20, 10, this.getWidth()/2, this.getHeight(), this);
                if(!charfn.equals("null")) {
                	Image set = ImageIO.read(new File(charfn));
	                g.drawImage(set, 22, -8, this.getWidth()/3/2, this.getHeight()/3*2, this);
                }
                if(inStorm) {
                	Image lgt = ImageIO.read(new File("images/lightning.png"));
	                g.drawImage(lgt, 225, 40, this.getWidth()/9, this.getHeight()/3, this);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
        };
        
		
		JButton b=new JButton();
		b.setBackground(new Color(142,163,182));
		b.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
		b.setPreferredSize(new Dimension(30,30));
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currSettler.Move(tp);
				AddNeighbour(currSettler);
				if(tp.GetPair().GetAsteroid()==null) neighnum=1;
				else neighnum=2;
				SetWidth();
				asterPan.setPreferredSize(new Dimension(width*300,400));
				buttonPushed = true;
			}
		});
		buttons.add(b);
		panel.add(b, BorderLayout.LINE_START);
		panel.add(asteroid, BorderLayout.CENTER);
		
		asterPan.add(panel, c2);
	}
	/**
	 * Az aktu�lis szomsz�dokat befoglal� panelre kirajzolja a sz�ks�ges k�peket
	 * @param s A soron l�v� telepes
	 */
	private void AddNeighbour(Settler s) {
		asterPan.removeAll();
		GridBagConstraints c2 = new GridBagConstraints();
		int x=0, y=0;
		c2.ipadx=300;
		c2.ipady=300;
		c2.weightx = 1;
		c2.weighty = 1;
		if(neighnum<7) {
			JPanel p=new JPanel();
			p.setOpaque(false);
			c2.gridx=1;
			c2.gridy=0;
			asterPan.add(p,c2);
		}
		if(neighnum<6) {
			int n=neighnum;
			for(int j=0; j<6-neighnum; j++) {
				JPanel p=new JPanel();
				p.setOpaque(false);
				c2.gridx=0;
				c2.gridy=n++;
				asterPan.add(p,c2);
			}
		}
		if(s.GetPlace().getClass().getSimpleName().equals("TeleportGate")) {
			TeleportGate tp = ((TeleportGate)s.GetPlace()).GetPair();
			c2.gridx=0;
			c2.gridy=0;
			Asteroid a=((TeleportGate)s.GetPlace()).GetAsteroid();
			
			AddAsteroid(a,c2);
			
			
			if(((TeleportGate)s.GetPlace()).GetPair().GetAsteroid()!=null) {
				c2.gridy=1;
				AddTpGate(tp, c2);
			}
			astPan.revalidate();
			astPan.repaint();
			asterPan.revalidate();
			asterPan.repaint();
			
		}
		else {
			for(Asteroid a : s.GetPlace().GetNeighbours()) {
				
				c2.gridx=x;
				c2.gridy=y;
				
				if(y==5) {
					y=0;
					x++;
				}
				else y++;
				
				AddAsteroid(a, c2);
				
			}
			for(TeleportGate tp : ((Asteroid)(s.GetPlace())).GetTpgate()) {
				c2.gridx=x;
				c2.gridy=y;
				if(y==5) {
					y=0;
					x++;
				}
				else y++;
				
				AddTpGate(tp, c2);
			}
		}
		
		
		astPan.revalidate();
		astPan.repaint();
		asterPan.revalidate();
		asterPan.repaint();
	
	}
	/**
	 * Be�ll�tja a men�b�r sz�n�t az �llapothoz m�rten
	 * @param state Az aktu�lis �llapot�zenet
	 */
	public void MenuBarColor(String state) {
		switch(state) {
		case "Storm" :
			this.getJMenuBar().setBackground(Color.YELLOW);
			break;
		case "Simple" : 
			this.getJMenuBar().setBackground(Color.WHITE);
			break;
		case "Lose" : 
			this.getJMenuBar().setBackground(Color.RED);
			break;
		case "Win" : 
			this.getJMenuBar().setBackground(Color.GREEN);
			break;	
		default:
			this.getJMenuBar().setBackground(Color.WHITE);
			break;
		}
		
	}
	/**
	 * A message c�mke sz�veg�t v�ltoztatjuk 
	 * @param msg a ki�rand� ERROR �zenet
	 */
	public void Message(String msg) {
		message.setText(" " + msg);
		if(msg.equals(""))
			message.setForeground(Color.WHITE);
		else
			message.setForeground(Color.RED);
		if(msg.equals("The game has ended! The settlers have lost!") || msg.equals("The game has ended! The settlers have won! The base has been built!"))
			message.setForeground(Color.BLACK);
	}
	
	
	/**
	 * �j k�rn�l �jrarajzolja az adott telepes alapj�n az ablakot
	 * @param s A soron l�v� telepes
	 */
	void DrawStatus(Settler s) {
		buttonPushed = false;
		astPan.removeAll();
		JLabel caLabel = new JLabel("CURRENT SETTLER:"+(AsteroidField.Instance().GetSettlers().indexOf(s)+1));
		caLabel.setFont(new Font("Courier new", Font.BOLD, 16));
		caLabel.setForeground(Color.white);
		astPan.add(caLabel, BorderLayout.PAGE_START);
        inventoryPan.removeAll();
        asterPan.removeAll();
        butPan.removeAll();
		currSettler=s;
		
		AddCurrent(s);
		AddInventory(s);
		if(s.GetPlace().getClass().getSimpleName().equals("TeleportGate")) {
			if(((TeleportGate)s.GetPlace()).GetPair().GetAsteroid()==null) neighnum=1;
			else neighnum=2;
		}
		else {
			neighnum=s.GetPlace().GetNeighbours().size()+((Asteroid)s.GetPlace()).GetTpgate().size();
		}
		SetWidth();
		asterPan.setPreferredSize(new Dimension(width*300,400));
		AddNeighbour(s);
		asterPan.revalidate();
		buttons.clear();
		AddButtons();
		while(!buttonPushed) {
			System.out.print("");
		}
		
	}
	/**
	 * Letiltja a j�t�kban tal�lhat� gombokat a j�t�kmenet v�g�n
	 */
	void Disable() {
		for(JButton b: buttons) {
			b.removeActionListener(b.getActionListeners()[0]);
			
		}
	}
	
		
}