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
 * Ez az osztály felel a játék grafikus megjelenítéséért. Kirajzolja az adott játékos által irányított telepest
 * az aszteroidára, amin épp tartózkodik, továbbá a nála lévõ nyersanyagokat és teleportkapukat.
 * A telepes irányítására szolgáló gombok mellett kirajzolja még az aszteroida szomszédait,
 * amire továbbléphet az adott játékos
 * 
 * @author Peti
 *
 */

public class GameFrame extends JFrame{
	/**
	 * Változó, amely figyeli az adott telepes körének a végét
	 */
	boolean buttonPushed = false;
	
	
	
	/**
	 * A menusáv megjelenítésére szolgáló JMenuBar
	 */
	private JMenuBar menubar;
	/**
	 * A játékosok száma
	 */
	private int settlerNum;
	/**
	 * A soron lévõ játékos aszteroidájának szomszédszáma
	 */
	private int neighnum;
	/**
	 * A szomszédok kirajzolásánál ennyi oszlopban lesznek az aszteroidák/teleportkapuk
	 */
	private int width;
	
	/**
	 * Az épp ledobásra kiválasztott nyersanyag a telepes inventoryából 
	 */
	private int selectedMat=-1;
	/**
	 * Az épp lerakásra kiválasztott teleportgate a telepes inventoryából
	 */
	private int selectedTpGate=-1;
	/**
	 * Singleton use-hoz használt statikus instance
	 */
	private static GameFrame instance = null;
	
	/**
	 * A soron lévõ telepes
	 */
	private Settler currSettler;
	/**
	 * A mûveleti gombokat befoglaló panel
	 */
	private JPanel butPan;
	/**
	 * Az aktuális aszteroidát befoglaló panel
	 */
	private JPanel astPan;
	/**
	 * Az aktuális inventoryt befoglaló panel
	 */
	private JPanel inventoryPan;
	/**
	 * Az aktuális szomszédokat befoglaló panel
	 */
	private JPanel asterPan;
	
	private Main main;
	/**
	 * A menübáron található üzenetek számára fenntartott címke
	 */
	private JLabel message;
	/**
	 * A megnyomható gombok listája
	 */
	private ArrayList<JButton> buttons=new ArrayList<JButton>();
	/**
	 * Privát konstruktor
	 * @param s a játékosok száma
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
	 * A GameFrame singleton példány létrehozására és elérésére használt metódus
	 * @return GameFrame objektummal tér vissza
	 */
	public static GameFrame Instance(int s)
    {
        if (instance == null)
            instance = new GameFrame(s);
        return instance;
    }
	/**
	 * A GameFrame singleton példány elérésére használt metódus
	 * @return GameFrame objektummal tér vissza
	 */
	public static GameFrame Instance()
    {
        return instance;
    }
	/**
	 *  Kiválasztja a megfelelõ képet attól függõen,
	 *  hogy milyen osztályt adunnk át a függvénynek és visszatér vele
	 * @param obj a kirajzolandó osztály
	 * @return a megfelelõ kép a kirajzoláshoz
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
	 * Beállítja, hogy hány oszlop szükséges a szomszédok kirajzolásához
	 */
	private void SetWidth() {
		if(neighnum<=6) width=2;
		else{
			double c=Math.ceil(((double)neighnum)/6);
			width=(int)c;
		}
	}
	/**
	 * Ez a metódus inicializálja a megjelenítést.
	 * Létrehozza és beállítja a szükséges paneleket a kirajzoláshoz.
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
	 * Beállítja az adott gomb paramétereit
	 * @param b Az adott gomb aminek beállítja a paramétereit
	 */
	private void SetButton(JButton b) {
		b.setFont(new Font("courier new", Font.BOLD, 12));
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setForeground(Color.WHITE);
		b.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
	}
	
	/**
	 * Újrarajzolja az ablakot, ha módosítunk rajta
	 */
	private void Reset() {
		this.repaint();
	}
	
	/**
	 * Hozzáadjuk a megfelelõ panelhez a mûveleti gombokat és beállítja,
	 * hogy kattintás hatására mi történjen
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
	 * Kirajzolja az aktuális aszteroidát befoglaló panelre a szükséges képeket
	 * @param s A soron lévõ telepes
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
	 * Kirajzolja az aktuális telepes inventoryát és beállítja a gombokat a megfelelõ mûködésre
	 * @param s A soron lévõ telepes
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
		//kitöltés
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
		//kitöltés
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
	 * Hozzáad az aktuális szomszédokat befoglaló panelhez egy aszteroida képet és a hozzá tartozó képeket
	 * @param a Az adott aszteroida
	 * @param c2 A kirajzoláshoz szükséges GridBagConstraint 
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
                	//köcsáó
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
	 * Hozzáad az aktuális szomszédokat befoglaló panelhez egy teleportkapu képet és a hozzá tartozó képeket
	 * @param tp Az adott teleportkapu
	 * @param c2 A kirajzoláshoz szükséges GridBagConstraint 
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
	 * Az aktuális szomszédokat befoglaló panelre kirajzolja a szükséges képeket
	 * @param s A soron lévõ telepes
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
	 * Beállítja a menübár színét az állapothoz mérten
	 * @param state Az aktuális állapotüzenet
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
	 * A message címke szövegét változtatjuk 
	 * @param msg a kiírandó ERROR üzenet
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
	 * Új körnél újrarajzolja az adott telepes alapján az ablakot
	 * @param s A soron lévõ telepes
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
	 * Letiltja a játékban található gombokat a játékmenet végén
	 */
	void Disable() {
		for(JButton b: buttons) {
			b.removeActionListener(b.getActionListeners()[0]);
			
		}
	}
	
		
}