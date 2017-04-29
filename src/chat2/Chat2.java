/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Andres
 */
public class Chat2 extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 939034576004835678L;
    private  JPanel contentPane;
    private  JTextField txtMensaje;
    private JTextField txtIP;
    private JTextArea txtMensajes;
    private JTextField txtPuertoDestino;
    private JTextField txtPuertoLocal;
    private JLabel lblPuertoServidorLocal;
    private JButton btnIniciarServidorLocal;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Chat2 frame = new Chat2();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Chat2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        contentPane = new PanelDeFondo(new ImageIcon("download.jpg"));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblMensajeOrigen = new JLabel("Mensaje Origen");
        GridBagConstraints gbc_lblMensajeOrigen = new GridBagConstraints();
        gbc_lblMensajeOrigen.insets = new Insets(0, 0, 5, 5);
        gbc_lblMensajeOrigen.gridx = 0;
        gbc_lblMensajeOrigen.gridy = 0;
        contentPane.add(lblMensajeOrigen, gbc_lblMensajeOrigen);

        JLabel lblIpDestino = new JLabel("IP Destino");
        GridBagConstraints gbc_lblIpDestino = new GridBagConstraints();
        gbc_lblIpDestino.insets = new Insets(0, 0, 5, 5);
        gbc_lblIpDestino.gridx = 1;
        gbc_lblIpDestino.gridy = 0;
        contentPane.add(lblIpDestino, gbc_lblIpDestino);

        JLabel lblPuertoDelServidor = new JLabel("Puerto del servidor destino");
        lblPuertoDelServidor.setForeground(Color.BLUE);
        lblPuertoDelServidor.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbc_lblPuertoDelServidor = new GridBagConstraints();
        gbc_lblPuertoDelServidor.insets = new Insets(0, 0, 5, 5);
        gbc_lblPuertoDelServidor.gridx = 2;
        gbc_lblPuertoDelServidor.gridy = 0;
        contentPane.add(lblPuertoDelServidor, gbc_lblPuertoDelServidor);

        txtMensaje = new JTextField();
        GridBagConstraints gbc_txtMensaje = new GridBagConstraints();
        gbc_txtMensaje.insets = new Insets(0, 0, 5, 5);
        gbc_txtMensaje.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtMensaje.gridx = 0;
        gbc_txtMensaje.gridy = 1;
        contentPane.add(txtMensaje, gbc_txtMensaje);
        txtMensaje.setColumns(10);

        txtIP = new JTextField();
        GridBagConstraints gbc_txtIP = new GridBagConstraints();
        gbc_txtIP.insets = new Insets(0, 0, 5, 5);
        gbc_txtIP.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtIP.gridx = 1;
        gbc_txtIP.gridy = 1;
        contentPane.add(txtIP, gbc_txtIP);
        txtIP.setColumns(10);

        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                enviarMensaje();
            }
        });
        GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
        gbc_btnEnviar.insets = new Insets(0, 0, 5, 0);
        gbc_btnEnviar.fill = GridBagConstraints.BOTH;
        gbc_btnEnviar.gridheight = 2;
        gbc_btnEnviar.gridx = 3;
        gbc_btnEnviar.gridy = 0;
        contentPane.add(btnEnviar, gbc_btnEnviar);

        txtPuertoDestino = new JTextField();
        GridBagConstraints gbc_txtPuertoDestino = new GridBagConstraints();
        gbc_txtPuertoDestino.insets = new Insets(0, 0, 5, 5);
        gbc_txtPuertoDestino.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPuertoDestino.gridx = 2;
        gbc_txtPuertoDestino.gridy = 1;
        contentPane.add(txtPuertoDestino, gbc_txtPuertoDestino);
        txtPuertoDestino.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.gridwidth = 4;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 2;
        contentPane.add(scrollPane, gbc_scrollPane);

        txtMensajes = new JTextArea();
        txtMensajes.setEditable(false);
        scrollPane.setViewportView(txtMensajes);

        lblPuertoServidorLocal = new JLabel("Puerto servidor local");
        lblPuertoServidorLocal.setForeground(Color.BLUE);
        lblPuertoServidorLocal.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbc_lblPuertoServidorLocal = new GridBagConstraints();
        gbc_lblPuertoServidorLocal.insets = new Insets(0, 0, 0, 5);
        gbc_lblPuertoServidorLocal.anchor = GridBagConstraints.EAST;
        gbc_lblPuertoServidorLocal.gridx = 0;
        gbc_lblPuertoServidorLocal.gridy = 3;
        contentPane.add(lblPuertoServidorLocal, gbc_lblPuertoServidorLocal);

        txtPuertoLocal = new JTextField();
        GridBagConstraints gbc_txtPuertoLocal = new GridBagConstraints();
        gbc_txtPuertoLocal.gridwidth = 2;
        gbc_txtPuertoLocal.insets = new Insets(0, 0, 0, 5);
        gbc_txtPuertoLocal.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPuertoLocal.gridx = 1;
        gbc_txtPuertoLocal.gridy = 3;
        contentPane.add(txtPuertoLocal, gbc_txtPuertoLocal);
        txtPuertoLocal.setColumns(10);

        btnIniciarServidorLocal = new JButton("Iniciar servidor local");
        btnIniciarServidorLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                iniciarServidorLocal();
            }
        });
        GridBagConstraints gbc_btnIniciarServidorLocal = new GridBagConstraints();
        gbc_btnIniciarServidorLocal.gridx = 3;
        gbc_btnIniciarServidorLocal.gridy = 3;
        contentPane.add(btnIniciarServidorLocal, gbc_btnIniciarServidorLocal);
    }

    protected void iniciarServidorLocal() {
        try {
            int puerto = Integer.parseInt(this.txtPuertoLocal.getText());
            new ServidorLocal(this, puerto).start();
            this.txtPuertoLocal.setEnabled(false);
            this.btnIniciarServidorLocal.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void enviarMensaje() {
        Socket s = null;
        ObjectOutputStream oos = null;
        try {
            String ip = this.txtIP.getText().trim();
            int puerto = Integer.parseInt(this.txtPuertoDestino.getText());
            System.out.println("Destino: " + ip + ", puerto: " + puerto);
            s = new Socket(ip, puerto);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(this.txtMensaje.getText().trim());
            this.addText("Yo: " + this.txtMensaje.getText().trim());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (s != null) {
                    s.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    protected void addText(String txt) {
        synchronized (this.txtMensajes) {
            if (this.txtMensajes.getText().trim().isEmpty()) {
                this.txtMensajes
                        .setText(this.txtMensajes.getText().concat(txt));
            } else {
                this.txtMensajes.setText(this.txtMensajes.getText().concat(
                        "\n" + txt));
            }
        }
    }
}

class ServidorLocal extends Thread {

    private Chat2 chat;
    private int puerto;

    public ServidorLocal(Chat2 chat, int puerto) {
        this.chat = chat;
        this.puerto = puerto;
    }

    @Override
    public void run() {
        super.run();
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(this.puerto);
            while (true) {

                System.out.println("Esperando conexion...");
                s = ss.accept();
                System.out.println("Conexion establecida desde: "
                        + s.getInetAddress().getHostAddress());

                ObjectInputStream ois = null;

                try {
                    ois = new ObjectInputStream(s.getInputStream());
                    String str = (String) ois.readObject();
                    System.out.println(str);
                    this.chat.addText("Cliente "
                            + s.getInetAddress().getHostAddress() + ": " + str);
                } catch (Exception e) {
                    e.getMessage();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}