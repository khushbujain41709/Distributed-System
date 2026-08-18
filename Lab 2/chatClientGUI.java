import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class chatClientGUI {
    
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField msgField;
    private JButton sendBtn, connectBtn;
    private JTextField nameField;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    
    public chatClientGUI() {
        createGUI();
    }
    
    private void createGUI() {
        frame = new JFrame("Chat Application");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Top panel - connection
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Name:"));
        nameField = new JTextField("User" + (int)(Math.random()*100), 10);
        topPanel.add(nameField);
        connectBtn = new JButton("Connect");
        topPanel.add(connectBtn);
        frame.add(topPanel, BorderLayout.NORTH);
        
        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(chatArea);
        frame.add(scroll, BorderLayout.CENTER);
        
        // Bottom panel - send message
        JPanel bottomPanel = new JPanel(new BorderLayout());
        msgField = new JTextField();
        msgField.setEnabled(false);
        sendBtn = new JButton("Send");
        sendBtn.setEnabled(false);
        bottomPanel.add(msgField, BorderLayout.CENTER);
        bottomPanel.add(sendBtn, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        // Action listeners
        connectBtn.addActionListener(e -> connect());
        sendBtn.addActionListener(e -> sendMessage());
        msgField.addActionListener(e -> sendMessage());
        
        frame.setVisible(true);
    }
    
    private void connect() {
        try {
            socket = new Socket("localhost", 8088);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            writer.println("/name " + nameField.getText());
            
            connectBtn.setText("Disconnect");
            msgField.setEnabled(true);
            sendBtn.setEnabled(true);
            
            chatArea.append("Connected to server!\n");
            
            new Thread(this::receiveMessages).start();
            
        } catch (IOException e) {
            chatArea.append("Connection failed!\n");
        }
    }
    
    private void receiveMessages() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                String finalMsg = msg;
                SwingUtilities.invokeLater(() -> chatArea.append(finalMsg + "\n"));
            }
        } catch (IOException e) {
            chatArea.append("Disconnected\n");
        }
    }
    
    private void sendMessage() {
        String msg = msgField.getText().trim();
        if (msg.isEmpty()) return;
        writer.println(msg);
        msgField.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(chatClientGUI::new);
    }
}