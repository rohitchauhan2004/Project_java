import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeManagementSystem extends JFrame {
    private JTextField adminNameField, adminIdField, adminPostField, adminDepartmentField;
    private JButton adminAddButton, adminRemoveButton, adminUpdateButton;
    private JTextField employeeSearchIdField, employeeSearchNameField;
    private JButton employeeSearchButton;
    private JTextArea employeeResultArea;
    private JTextField leaveIdField, leaveStartDateField, leaveEndDateField;
    private JButton leaveApplyButton;
    private JButton employeeLeaveManagementButton;

    private static final String EMPLOYEE_FILE = "EmployeeData.csv";
    private static final String LEAVE_FILE = "LeaveData.csv";
    private Map<String, String[]> employeeData; // Employee data stored in memory
    private Map<String, String[]> leaveData; // Leave data stored in memory

    public EmployeeManagementSystem() {
        setTitle("Employee Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Initialize employee and leave data maps
        employeeData = new HashMap<>();
        leaveData = new HashMap<>();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Admin Interface", createAdminInterface());
        tabbedPane.addTab("Employee Interface", createEmployeeInterface());
        tabbedPane.addTab("Leave Management Interface", createLeaveManagementInterface());

        add(tabbedPane);
    }

    private JPanel createAdminInterface() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5)); // Compact layout with gaps
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
        panel.setBackground(new Color(240, 240, 240));

        addLabelAndField(panel, "Name:", adminNameField = createTextField());
        addLabelAndField(panel, "ID:", adminIdField = createTextField());
        addLabelAndField(panel, "Post:", adminPostField = createTextField());
        addLabelAndField(panel, "Department:", adminDepartmentField = createTextField());

        adminAddButton = createButton("Add");
        adminAddButton.addActionListener(e -> addEmployee());
        adminRemoveButton = createButton("Remove");
        adminRemoveButton.addActionListener(e -> removeEmployee());
        adminUpdateButton = createButton("Update");
        adminUpdateButton.addActionListener(e -> updateEmployee());

        panel.add(adminAddButton);
        panel.add(adminRemoveButton);
        panel.add(adminUpdateButton);

        return panel;
    }

    private JPanel createEmployeeInterface() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
        panel.setBackground(new Color(240, 240, 240));

        JPanel searchPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.add(new JLabel("Search ID:"));
        searchPanel.add(employeeSearchIdField = createTextField());
        employeeSearchButton = createButton("Search");
        employeeSearchButton.addActionListener(e -> searchEmployee());
        searchPanel.add(employeeSearchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        employeeResultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(employeeResultArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        employeeLeaveManagementButton = createButton("Leave Management");
        panel.add(employeeLeaveManagementButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLeaveManagementInterface() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
        panel.setBackground(new Color(240, 240, 240));

        addLabelAndField(panel, "Employee ID:", leaveIdField = createTextField());
        addLabelAndField(panel, "Start Date:", leaveStartDateField = createTextField());
        addLabelAndField(panel, "End Date:", leaveEndDateField = createTextField());

        leaveApplyButton = createButton("Apply Leave");
        leaveApplyButton.addActionListener(e -> applyLeave());
        panel.add(new JPanel()); // Empty space
        panel.add(leaveApplyButton);

        return panel;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        return button;
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        panel.add(field);
    }

    private void addEmployee() {
        String name = adminNameField.getText();
        String id = adminIdField.getText();
        String post = adminPostField.getText();
        String department = adminDepartmentField.getText();

        if (!name.isEmpty() && !id.isEmpty() && !post.isEmpty() && !department.isEmpty()) {
            if (!employeeData.containsKey(id)) {
                String[] employeeInfo = {name, post, department};
                employeeData.put(id, employeeInfo);
                saveEmployeeDataToFile();
                JOptionPane.showMessageDialog(null, "Employee added successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Employee with ID '" + id + "' already exists.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
        }
    }

    private void removeEmployee() {
        String id = adminIdField.getText();
        if (!id.isEmpty()) {
            if (employeeData.containsKey(id)) {
                employeeData.remove(id);
                saveEmployeeDataToFile();
                JOptionPane.showMessageDialog(null, "Employee removed successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Employee with ID '" + id + "' not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter employee ID");
        }
    }

    private void updateEmployee() {
        String id = adminIdField.getText();
        String name = adminNameField.getText();
        String post = adminPostField.getText();
        String department = adminDepartmentField.getText();
        if (!id.isEmpty() && !name.isEmpty() && !post.isEmpty() && !department.isEmpty()) {
            if (employeeData.containsKey(id)) {
                String[] employeeInfo = {name, post, department};
                employeeData.put(id, employeeInfo);
                saveEmployeeDataToFile();
                JOptionPane.showMessageDialog(null, "Employee information updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Employee with ID '" + id + "' not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
        }
    }

    private void searchEmployee() {
        String id = employeeSearchIdField.getText();
        if (!id.isEmpty()) {
            String[] employeeInfo = employeeData.get(id);
            if (employeeInfo != null) {
                String result = "Name: " + employeeInfo[0] + "\nPost: " + employeeInfo[1] + "\nDepartment: " + employeeInfo[2];
                employeeResultArea.setText(result);
            } else {
                employeeResultArea.setText("Employee not found");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter employee ID");
        }
    }

    private void applyLeave() {
        String id = leaveIdField.getText();
        String startDate = leaveStartDateField.getText();
        String endDate = leaveEndDateField.getText();
        if (!id.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
            String[] leaveInfo = {startDate, endDate};
            leaveData.put(id, leaveInfo);
            saveLeaveDataToFile();
            JOptionPane.showMessageDialog(null, "Leave applied successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
        }
    }

    private void saveEmployeeDataToFile() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(EMPLOYEE_FILE)))) {
            for (Map.Entry<String, String[]> entry : employeeData.entrySet()) {
                String id = entry.getKey();
                String[] info = entry.getValue();
                writer.println(String.join(",", info[0], id, info[1], info[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving employee data: " + e.getMessage());
        }
    }

    private void saveLeaveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LEAVE_FILE)))) {
            for (Map.Entry<String, String[]> entry : leaveData.entrySet()) {
                String id = entry.getKey();
                String[] info = entry.getValue();
                writer.println(String.join(",", id, info[0], info[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving leave data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementSystem empSystem = new EmployeeManagementSystem();
            empSystem.setVisible(true);
        });
    }
}