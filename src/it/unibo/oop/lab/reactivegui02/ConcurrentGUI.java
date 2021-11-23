package it.unibo.oop.lab.reactivegui02;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * 
 * 
 *
 */
public class ConcurrentGUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final double WIDTH = 0.2;
    private static final double HEIGHT = 0.1;
    private final JLabel display = new JLabel();
    private final JButton stop = new JButton("stop");
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    /**
     * 
     */
    public ConcurrentGUI() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH), (int) (screenSize.getHeight() * HEIGHT));
        final JPanel panel = new JPanel();
        panel.add(display);
        panel.add(down);
        panel.add(up);
        panel.add(stop);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*
         * Start the Thread
         */
        final Agent agent = new Agent();
        new Thread(agent).start();
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                agent.stop();
            }
        });
        up.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                agent.up();
            }
        });
        down.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
                agent.down();
            }
        });
        this.setVisible(true);
    }
    private class Agent implements Runnable{

        private volatile boolean stop;
        private volatile boolean up = true;
        private volatile int counter;
        @Override
        public void run() {
            while (!this.stop) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            // This will happen in the EDT: since i'm reading counter it needs to be volatile.
                            ConcurrentGUI.this.display.setText(Integer.toString(Agent.this.counter));
                        }
                    });
                    if (up) {
                        this.counter++;
                    } else {
                        this.counter--;
                    }
                    Thread.sleep(100);
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        public void stop() {
            this.stop = true;
            ConcurrentGUI.this.stop.setEnabled(false);
            ConcurrentGUI.this.up.setEnabled(false);
            ConcurrentGUI.this.down.setEnabled(false);
        }
        public void up() {
            this.up = true;
        }
        public void down() {
            this.up = false;
        }
    }
}
