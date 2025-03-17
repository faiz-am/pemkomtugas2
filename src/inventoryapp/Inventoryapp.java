package inventoryapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Quantity: " + quantity;
    }
}

class Inventory {
    private List<Item<?>> items = new ArrayList<>();

    public void addItem(Item<?> item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public List<Item<?>> getItems() {
        return items;
    }
}

public class Inventoryapp extends JFrame {
    private Inventory inventory = new Inventory();
    private DefaultTableModel tableModel;
    private JTable table;

    public Inventoryapp() {
        setTitle("Inventory Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Add Item");
        JButton editButton = new JButton("Edit Item");
        JButton deleteButton = new JButton("Delete Item");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(addButton);
        panel.add(editButton);

        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String idText = idField.getText();
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            Item<?> item;
            if (idText.matches("\\d+")) {
                item = new Item<>(Integer.parseInt(idText), name, quantity);
            } else {
                item = new Item<>(idText, name, quantity);
            }
            inventory.addItem(item);
            tableModel.addRow(new Object[]{item.getId(), item.getName(), item.getQuantity()});

            idField.setText("");
            nameField.setText("");
            quantityField.setText("");
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String newName = JOptionPane.showInputDialog("Enter new name:", tableModel.getValueAt(selectedRow, 1));
                int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity:", tableModel.getValueAt(selectedRow, 2)));
                
                inventory.getItems().get(selectedRow).setName(newName);
                inventory.getItems().get(selectedRow).setQuantity(newQuantity);

                tableModel.setValueAt(newName, selectedRow, 1);
                tableModel.setValueAt(newQuantity, selectedRow, 2);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                inventory.removeItem(selectedRow);
                tableModel.removeRow(selectedRow);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Inventoryapp().setVisible(true));
    }
}
