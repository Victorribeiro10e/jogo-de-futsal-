// Jogo de Futsal Educativo em Java (versão simplificada usando Java Swing)
// OBS: Esta versão é uma base funcional simples para expansão.
// Não usa bibliotecas externas, apenas Swing/AWT.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JogoFutsal extends JPanel implements ActionListener, KeyListener {

    // Dimensões
    int largura = 800;
    int altura = 600;

    // Jogadores e bola
    Rectangle jogador = new Rectangle(100, 250, 30, 30);
    Rectangle adversario = new Rectangle(650, 250, 30, 30);
    Rectangle goleiro = new Rectangle(740, 250, 20, 80);
    Rectangle bola = new Rectangle(390, 290, 20, 20);

    // Velocidades
    int velJogador = 6;
    int velAdversario = 3;
    int velBolaX = 0;
    int velBolaY = 0;

    // Placar
    int placarJogador = 0;
    int placarAdversario = 0;

    Timer timer;

    public JogoFutsal() {
        this.setPreferredSize(new Dimension(largura, altura));
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fundo (quadra)
        g.setColor(new Color(20, 120, 20));
        g.fillRect(0, 0, largura, altura);

        g.setColor(Color.white);
        g.drawRect(20, 20, 760, 560);
        g.drawLine(400, 20, 400, 580);
        g.drawOval(330, 230, 140, 140);
        g.fillOval(395, 295, 10, 10);

        // Jogadores
        g.setColor(Color.blue);
        g.fillRect(jogador.x, jogador.y, jogador.width, jogador.height);

        g.setColor(Color.red);
        g.fillRect(adversario.x, adversario.y, adversario.width, adversario.height);

        g.setColor(Color.white);
        g.fillRect(goleiro.x, goleiro.y, goleiro.width, goleiro.height);

        // Bola
        g.fillOval(bola.x, bola.y, bola.width, bola.height);

        // Placar
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(placarJogador + " x " + placarAdversario, 360, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moverAdversario();
        moverGoleiro();
        moverBola();
        checarGols();
        repaint();
    }

    public void moverAdversario() {
        if (adversario.y < bola.y) adversario.y += velAdversario;
        else if (adversario.y > bola.y) adversario.y -= velAdversario;
    }

    public void moverGoleiro() {
        if (goleiro.y < bola.y) goleiro.y += 3;
        else if (goleiro.y > bola.y) goleiro.y -= 3;
    }

    public void moverBola() {
        bola.x += velBolaX;
        bola.y += velBolaY;

        if (bola.y <= 20 || bola.y >= 580) velBolaY *= -1;

        if (bola.intersects(jogador)) velBolaX = 5;
        if (bola.intersects(adversario)) velBolaX = -5;
        if (bola.intersects(goleiro)) velBolaX *= -1;
    }

    public void chutar(int forca) {
        velBolaX = forca;
        velBolaY = (int)(Math.random() * 6 - 3);
    }

    public void checarGols() {
        if (bola.x >= 780) {
            placarJogador++;
            resetarPosicoes();
        }

        if (bola.x <= 20) {
            placarAdversario++;
            resetarPosicoes();
        }
    }

    public void resetarPosicoes() {
        jogador.setLocation(100, 250);
        adversario.setLocation(650, 250);
        goleiro.setLocation(740, 250);
        bola.setLocation(390, 290);
        velBolaX = 0;
        velBolaY = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) jogador.y -= velJogador;
        if (key == KeyEvent.VK_S) jogador.y += velJogador;
        if (key == KeyEvent.VK_A) jogador.x -= velJogador;
        if (key == KeyEvent.VK_D) jogador.x += velJogador;

        if (key == KeyEvent.VK_SPACE) chutar(6);
        if (key == KeyEvent.VK_SHIFT) chutar(10);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}


    // ==========================
    // MÉTODO PRINCIPAL
    // ==========================
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo de Futsal - Java");
        JogoFutsal jogo = new JogoFutsal();

        frame.add(jogo);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
