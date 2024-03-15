import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospitalInformationSystem extends JFrame implements ActionListener {
    private JTextField nameField, ageField, genderField;
    private JButton addButton;

    public HospitalInformationSystem() {
        setTitle("Hospital Information System");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField();

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(ageLabel);
        mainPanel.add(ageField);
        mainPanel.add(genderLabel);
        mainPanel.add(genderField);

        addButton = new JButton("Add Patient");
        addButton.addActionListener(this);
        mainPanel.add(addButton);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();

            // Save patient to database
            savePatient(name, age, gender);

            // Clear input fields after adding patient
            nameField.setText("");
            ageField.setText("");
            genderField.setText("");

            JOptionPane.showMessageDialog(this, "Patient added successfully.");
        }
    }

    private void savePatient(String name, int age, String gender) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "username", "password");

            String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to add patient.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalInformationSystem());
    }
}
