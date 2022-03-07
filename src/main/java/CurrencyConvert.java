
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConvert extends Frame {
    JComboBox<String> convertFrom;
    JComboBox<String> convertTo;
    JTextField txtFrom, txtTo;
    JButton compute, refresh;
    JLabel from, to;
    JLabel lblFrom, lblTo;


    public CurrencyConvert(List<String> stringList) {

        super("Currency Exchange");
        setSize(420, 300);
        setLayout(null);
        setBackground(Color.lightGray);

        lblFrom = new JLabel("From:");
        lblFrom.setBounds(30, 45, 250, 30);
        lblFrom.setForeground(Color.black);
        add(lblFrom);

        convertFrom = new JComboBox<>();
        convertFrom.setBounds(30, 70, 150, 30);
        convertFrom.setEditable(true);

        convertTo = new JComboBox<>();
        convertTo.setBounds(230, 70, 150, 30);

        convertFrom.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                System.out.println("itemStateChanged");
                /*if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    item.toUpperCase();
                    convertFrom.removeAll();
                    for (String temp : findElement(item,stringList)) {
                        convertFrom.addItem(temp);
                    }
                    add(convertFrom);
                }*/
            }

        });


        convertFrom.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                e.setKeyChar(Character.toUpperCase(character));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String s = (String) convertFrom.getEditor().getItem();
                List<String> findElementsList = findElement(s, stringList);
                convertFrom.removeAllItems();
                //convertFrom.add
                for (String temp : findElementsList) {
                    System.out.println(temp);

                }
                convertFrom.showPopup();
                System.out.println("fromTextValue = " + s);
            }
        });

        for (String temp : stringList) {
            convertFrom.addItem(temp);
            convertTo.addItem(temp);
        }

        add(convertFrom);
        add(convertTo);

        lblTo = new JLabel("To:");
        lblTo.setBounds(230, 45, 250, 30);
        lblTo.setForeground(Color.RED);
        add(lblTo);

        from = new JLabel("Enter Amount to Convert:");
        from.setBounds(50, 110, 300, 30);
        add(from);

        txtFrom = new JTextField(15);
        txtFrom.setBounds(50, 140, 300, 30);
        txtFrom.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        add(txtFrom);

        compute = new JButton("Compute");
        compute.setBounds(100, 250, 100, 30);
        add(compute);

        to = new JLabel("Total Amount Converted:");
        to.setBounds(50, 170, 300, 30);
        add(to);

        txtTo = new JTextField(15);
        txtTo.setBounds(50, 200, 300, 30);
        txtTo.setEditable(false);
        txtTo.setForeground(Color.RED);
        add(txtTo);

        refresh = new JButton("Refresh");
        refresh.setBounds(210, 250, 100, 30);
        add(refresh);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFrom.setText("");
                txtTo.setText("");

            }
        });


        compute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromAmount = (String) convertFrom.getSelectedItem();
                String to = (String) convertTo.getSelectedItem();
                String amount = txtFrom.getText();

                try {
                    txtTo.setText(ExchangeClient.currencyExchange(fromAmount, to, amount) + "");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }

    public List<String> findElement(String search, List<String> list) {
        List<String> matches = new ArrayList<>();
        if (search.isEmpty()) {
            return matches;
        }
        for (String str : list) {
            if (str.startsWith(search)) {
                matches.add(str);
            }
        }

        return matches;
    }

    public void exit() {
        setVisible(false);
        dispose();
        System.exit(0);
    }
}
