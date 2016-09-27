package imgMatching;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Main extends JFrame {
    int money = 0;
    int force = 10;
    int forceUp = 0;
    int forceUpmoney = 50;

    int weaponDamage = 1;
    int weapon = 0;
    int weaponUpmoney = 200;

    int damage = force + weaponDamage;
    int upgradeCount = 1;
    int upMoney = 10;

    int hitEnemy = 0;
    int defaultEnemyHit = 50;

    Container pane = getContentPane();
    JPanel northJP = new JPanel();
    JButton saveJB = new JButton("저장하기");
    JButton loadJB = new JButton("불러오기");
    JButton exitJB = new JButton("나가기");
    JLabel moneyJL = new JLabel("돈: 0");

    JPanel centerJP = new JPanel();
    JLabel playerJL = new JLabel();
    JLabel enemyJL = new JLabel();
    ImageIcon player = new ImageIcon("image/zerg.png");
    ImageIcon[] enemy = new ImageIcon[]{
            new ImageIcon("image/enemy1.png"),
            new ImageIcon("image/enemy2.png"),
            new ImageIcon("image/enemy3.png")
    };
    ImageIcon[] stateUpicon = new ImageIcon[]{
            new ImageIcon("image/state1.png"),
            new ImageIcon("image/state2.png"),
            new ImageIcon("image/state3.png"),
            new ImageIcon("image/state3.png"),
    };
    JLabel hpGageJL = new JLabel("현재 체력: " + (defaultEnemyHit - hitEnemy));

    JPanel southJP = new JPanel();
    JButton stateJB = new JButton("능력강화");
    JButton weaponJB = new JButton("무기구매");
    JButton effectJB = new JButton("효과강화");
    JLabel damageJL = new JLabel("현재 데미지: " + damage);

    JDialog stateJD = new JDialog(new JFrame(), true);
    JLabel stateJL = new JLabel("현재 공격력: " + force);
    JLabel stateNeedJL = new JLabel("강화시 필요한 돈 : " + forceUpmoney);
    JLabel stateImageJL = new JLabel();
    JButton stateUpJB = new JButton("공격력강화");

    JDialog weaponJD = new JDialog(new JFrame(), true);
    JLabel weaponDamageJL = new JLabel("현재 무기 공격력: " + weaponDamage);
    JLabel weaponNeedJL = new JLabel("무기 구매시 필요한 돈: " + weaponUpmoney);
    JButton weaponUpJB = new JButton("무기변경");

    JDialog effectJD = new JDialog(new JFrame(), true);
    boolean buyEffect[] = new boolean[2];
    JLabel money2JL = new JLabel("돈2배(300)");
    JButton money2JB = new JButton("구매");
    JLabel money5JL = new JLabel("돈5배(600)");
    JButton money5JB = new JButton("구매");
    JLabel sorryJL = new JLabel("이 후 구현중");

    JDialog failLoadJD = new JDialog(new Frame(), true);
    JLabel failLoadJL = new JLabel("불러올 데이터가 없습니다");
    JButton failLoadJB = new JButton("확인");

    //치트
    JButton intoCheat = new JButton("힌트");
    JDialog cheatJD = new JDialog(new Frame(), true);
    JLabel cheatJL = new JLabel("키 코드 입력");
    JTextField cheatJT = new JTextField();
    JButton cheatJB = new JButton("입력");

    String moneyCode = "jaranaramoneymoney";
    String inputCheatCode = "";

    Main() {
        setting();
        dialogAdd();
    }

    void setting() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("저글링키우기");
        setSize(400, 400);
        setVisible(true);

        northJP.setBackground(Color.BLACK);
        saveJB.addActionListener(new MyActionListener());
        northJP.add(saveJB);
        loadJB.addActionListener(new MyActionListener());
        northJP.add(loadJB);
        exitJB.addActionListener(new MyActionListener());
        northJP.add(exitJB);
        moneyJL.setForeground(Color.WHITE);
        northJP.add(moneyJL);
        pane.add(northJP, BorderLayout.NORTH);

        playerJL.setIcon(player);
        enemyJL.addMouseListener(new MyMouseListener());
        enemyJL.setIcon(enemy[0]);
        centerJP.add(playerJL, BorderLayout.WEST);
        centerJP.add(enemyJL, BorderLayout.EAST);
        centerJP.add(hpGageJL, BorderLayout.NORTH);
        pane.add(centerJP, BorderLayout.CENTER);

        southJP.setLayout(new GridLayout(0, 3));
        southJP.setBackground(Color.BLACK);
        stateJB.addActionListener(new MyActionListener());
        southJP.add(stateJB);
        weaponJB.addActionListener(new MyActionListener());
        southJP.add(weaponJB);
        effectJB.addActionListener(new MyActionListener());
        southJP.add(effectJB);
        damageJL.setForeground(Color.WHITE);
        southJP.add(damageJL);
        //치트
        southJP.add(intoCheat, BorderLayout.EAST);
        intoCheat.addActionListener(new MyActionListener());
        //
        pane.add(southJP, BorderLayout.SOUTH);

        for (int i = 0; i < buyEffect.length; i++) buyEffect[i] = false;
    }

    void dialogAdd() {
        stateJD.setTitle("상태업그레이드");
        stateJD.setSize(250, 350);
        stateJD.setVisible(false);
        stateJD.setLayout(null);
        stateJL.setBounds(75, 20, 200, 20);
        stateJD.add(stateJL);
        stateNeedJL.setBounds(50, 40, 200, 20);
        stateJD.add(stateNeedJL);
        stateImageJL.setBounds(75, 20, 200, 200);
        stateImageJL.setIcon(stateUpicon[forceUp]);
        stateJD.add(stateImageJL);
        stateUpJB.setBounds(75, stateJD.getHeight() / 2, 100, 30);
        stateUpJB.addActionListener(new MyActionListener());
        stateJD.add(stateUpJB);

        weaponJD.setTitle("무기업그레이드");
        weaponJD.setSize(250, 350);
        weaponJD.setVisible(false);
        weaponJD.setLayout(null);
        weaponDamageJL.setBounds(40, 20, 200, 20);
        weaponJD.add(weaponDamageJL);
        weaponNeedJL.setBounds(40, 40, 200, 20);
        weaponJD.add(weaponNeedJL);
        weaponUpJB.setBounds(75, weaponJD.getHeight() / 2, 100, 30);
        weaponUpJB.addActionListener(new MyActionListener());
        weaponJD.add(weaponUpJB);

        effectJD.setTitle("효과업그레이드");
        effectJD.setSize(250, 350);
        effectJD.setVisible(false);
        effectJD.setLayout(new GridLayout(0, 2));
        effectJD.add(money2JL);
        money2JB.addActionListener(new MyActionListener());
        effectJD.add(money2JB);
        effectJD.add(money5JL);
        money5JB.addActionListener(new MyActionListener());
        effectJD.add(money5JB);
        effectJD.add(sorryJL);

        failLoadJD.setTitle("불러오기실패");
        failLoadJD.setSize(200, 100);
        failLoadJD.setVisible(false);
        failLoadJD.setLayout(new FlowLayout());
        failLoadJD.add(failLoadJL);
        failLoadJB.addActionListener(new MyActionListener());
        failLoadJD.add(failLoadJB);

        //치트
        cheatJD.setTitle("힌트");
        cheatJD.setSize(250, 200);
        cheatJD.setVisible(false);
        cheatJD.setLayout(new FlowLayout());
        cheatJD.add(cheatJL);
        cheatJT.setColumns(20);
        cheatJD.add(cheatJT);
        cheatJB.addActionListener(new MyActionListener());
        cheatJD.add(cheatJB);
    }

    class MyMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            hitEnemy += damage;
            if (hitEnemy >= defaultEnemyHit) {
                enemySelect();
                hpGageJL.setText("현재 체력: " + (defaultEnemyHit - hitEnemy));
            }
            hpGageJL.setText("현재 체력: " + (defaultEnemyHit - hitEnemy));
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveJB) save();
            if (e.getSource() == loadJB) load();
            if (e.getSource() == exitJB) System.exit(0);
            if (e.getSource() == stateJB) stateJD.setVisible(true);
            if (e.getSource() == weaponJB) weaponJD.setVisible(true);
            if (e.getSource() == effectJB) effectJD.setVisible(true);
            if (e.getSource() == stateUpJB) stateUp();
            if (e.getSource() == weaponUpJB) weaponUp();
            if (e.getSource() == failLoadJB) failLoadJD.setVisible(false);
            if (e.getSource() == money2JB) {
                if (buyEffect[0] == false && money >= 300) {
                    upMoney *= 2;
                    buyEffect[0] = true;
                    money -= 300;
                    moneyJL.setText("돈: " + money);
                }
            }
            if (e.getSource() == money5JB) {
                if (buyEffect[1] == false && money >= 600) {
                    upMoney *= 5;
                    buyEffect[1] = true;
                    money -= 600;
                    moneyJL.setText("돈: " + money);
                }
            }
            // 치트
            if (e.getSource() == intoCheat) {
                cheatJD.setVisible(true);
            }
            if (e.getSource() == cheatJB) {
                //String moneyCode = "jaranaramoneymoney";
                inputCheatCode = cheatJT.getText();
                System.out.println(inputCheatCode);
                if (inputCheatCode.equals(moneyCode)) {
                    money = 100000;
                    moneyJL.setText("돈: " + money);
                }
            }
        }
    }

    public void enemySelect() {
        money += upMoney;
        moneyJL.setText("돈: " + money);
        int enemySelect = (int) (Math.random() * 3);

        hitEnemy = 0;

        defaultEnemyHit *= 1.03;
        enemyJL.setIcon(enemy[enemySelect]);
    }

    void stateUp() {
        if (money >= forceUpmoney) {
            if (forceUp < 3) {
                force += force / 2;
                forceUp++;
                stateJL.setText("현재 공격력: " + force);
                money -= forceUpmoney;
                moneyJL.setText("돈: " + money);
                forceUpmoney += 50 * upgradeCount;
                damage = force + weaponDamage;
                damageJL.setText("현재 데미지: " + damage);
                stateNeedJL.setText("강화시 필요한 돈: " + forceUpmoney);
                stateImageJL.setIcon(stateUpicon[forceUp]);
                upgradeCount++;
            }
        }
    }

    void weaponUp() {
        if (forceUp == 3) {
            if (money >= weaponUpmoney) {
                weaponDamage = 200 * (weapon + 1);
                weapon++;
                money -= weaponUpmoney;
                moneyJL.setText("돈: " + money);
                weaponUpmoney += 300;
                forceUp = 0;
                stateJL.setText("현재 공격력: " + force);
                damage = force + weaponDamage;
                damageJL.setText("현재 데미지: " + damage);
                weaponDamageJL.setText("현재 무기 공격력: " + weaponDamage);
                weaponNeedJL.setText("무기 구매시 필요한 돈: " + weaponUpmoney);
                stateImageJL.setIcon(stateUpicon[forceUp]);
            }
        }
    }

    void save() {
        try {
            BufferedWriter save = new BufferedWriter(new FileWriter("zerg.txt"));
            save.write(money + "@" + force + "@" + forceUp + "@" + forceUpmoney + "@"
                    + weaponDamage + "@" + weapon + "@" + weaponUpmoney + "@" + damage
                    + "@" + upgradeCount + "@" + upMoney + "@" + hitEnemy + "@"
                    + defaultEnemyHit + "@" + buyEffect[0] + "@" + buyEffect[1]);
            save.close();
        } catch (Exception e) {
        }
    }

    void load() {
        try {
            File load = new File("zerg.txt");
            FileReader loadFR = new FileReader(load);
            BufferedReader loadBR = new BufferedReader(loadFR);
            String loading = loadBR.readLine();
            String str[] = loading.split("@");

            money = Integer.valueOf(str[0]);
            force = Integer.valueOf(str[1]);
            forceUp = Integer.valueOf(str[2]);
            forceUpmoney = Integer.valueOf(str[3]);
            weaponDamage = Integer.valueOf(str[4]);
            weapon = Integer.valueOf(str[5]);
            weaponUpmoney = Integer.valueOf(str[6]);
            damage = Integer.valueOf(str[7]);
            upgradeCount = Integer.valueOf(str[8]);
            upMoney = Integer.valueOf(str[9]);
            hitEnemy = Integer.valueOf(str[10]);
            defaultEnemyHit = Integer.valueOf(str[11]);
            buyEffect[0] = Boolean.valueOf(str[12]);
            buyEffect[1] = Boolean.valueOf(str[13]);

            moneyJL.setText("돈: " + money);
            stateJL.setText("현재 공격력: " + force);
            stateNeedJL.setText("강화시 필요한 돈: " + forceUpmoney);
            weaponDamageJL.setText("현재 무기 공격력: " + weaponDamage);
            weaponNeedJL.setText("무기 구매시 필요한 돈: " + weaponUpmoney);
            damageJL.setText("현재 데미지: " + damage);
            hpGageJL.setText("현재 체력: " + (defaultEnemyHit - hitEnemy));

            loadBR.close();
            loadFR.close();
        } catch (Exception e) {
            failLoadJD.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
