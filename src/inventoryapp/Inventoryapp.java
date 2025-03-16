/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inventoryapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Class Generic untuk Item
class Item<T> {
    private T id;
    private String name;
    private int quantity;

    public Item(T id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public T getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Quantity: " + quantity;
    }
}

// Kelas Inventaris dengan Generic Method dan Wildcard
class Inventory {
    private List<Item<?>> items = new ArrayList<>();

    public void addItem(Item<?> item) {
        items.add(item);
    }

    public List<Item<?>> getItems() {
        return items;
    }
}

// Kelas GUI Aplikasi
public class Inventoryapp extends JFrame {
    private Inventory inventory = new Inventory();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> itemList = new JList<>(listModel);

    public Inventoryapp() {
        setTitle("Inventory Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Add Item");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(new JLabel());
        panel.add(addButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(itemList), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idField.getText();
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                
                // Menentukan tipe ID berdasarkan input
                Item<?> item;
                if (idText.matches("\\d+")) { // Jika angka
                    item = new Item<>(Integer.parseInt(idText), name, quantity);
                } else { // Jika teks
                    item = new Item<>(idText, name, quantity);
                }
                
                inventory.addItem(item);
                listModel.addElement(item.toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Inventoryapp().setVisible(true);
        });
    }
}
