import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentInfoSystem {
    // Main components
    private JFrame mainFrame;
    private JPanel currentPanel;
    private ArrayList<Student> students;

    // Current student being viewed/edited
    private int currentStudentIndex = -1;

    public static void main(String[] args) {
        StudentInfoSystem app = new StudentInfoSystem();
        app.initialize();
        app.showLoginScreen();
    }

    // Student class to hold student data
    class Student {
        String id;
        String name;
        String major;
        int year;
        double gpa;

        public Student(String id, String name, String major, int year, double gpa) {
            this.id = id;
            this.name = name;
            this.major = major;
            this.year = year;
            this.gpa = gpa;
        }
    }

    public StudentInfoSystem() {
        // Initialize sample data
        students = new ArrayList<>();
        students.add(new Student("S001", "Бат Болд", "Компьютерийн ухаан", 2, 3.6));
        students.add(new Student("S002", "Нарангэрэл Дулам", "Мэдээллийн технологи", 3, 3.8));
        students.add(new Student("S003", "Өлзийсайхан Баяр", "Программ хангамж", 1, 3.2));
    }

    private void initialize() {
        // Create main frame
        mainFrame = new JFrame("Оюутны мэдээллийн систем");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem exitItem = new JMenuItem("Гарах");
        exitItem.addActionListener(e -> {
            System.out.println("Програмаас гарлаа");
            System.exit(0);
        });
        fileMenu.add(exitItem);

        // Student menu
        JMenu studentMenu = new JMenu("Оюутан");
        JMenuItem listItem = new JMenuItem("Жагсаалт харах");
        listItem.addActionListener(e -> {
            System.out.println("Оюутны жагсаалт харах товчлуур дарагдлаа");
            showStudentListScreen();
        });

        JMenuItem addItem = new JMenuItem("Оюутан нэмэх");
        addItem.addActionListener(e -> {
            System.out.println("Оюутан нэмэх товчлуур дарагдлаа");
            currentStudentIndex = -1;
            showStudentFormScreen();
        });

        studentMenu.add(listItem);
        studentMenu.add(addItem);

        // Help menu
        JMenu helpMenu = new JMenu("Тусламж");
        JMenuItem aboutItem = new JMenuItem("Програмын тухай");
        aboutItem.addActionListener(e -> {
            System.out.println("Програмын тухай товчлуур дарагдлаа");
            JOptionPane.showMessageDialog(mainFrame,
                    "Оюутны мэдээллийн систем v1.0\n" +
                            "Объект хандлагат програмчлал хичээл\n" +
                            "© 2025",
                    "Програмын тухай",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(studentMenu);
        menuBar.add(helpMenu);

        // Set menu bar to frame
        mainFrame.setJMenuBar(menuBar);
    }

    private void switchPanel(JPanel newPanel) {
        if (currentPanel != null) {
            mainFrame.remove(currentPanel);
        }
        currentPanel = newPanel;
        mainFrame.add(currentPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // Login Screen
    public void showLoginScreen() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Оюутны мэдээллийн систем");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Хэрэглэгчийн нэр:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(userField, gbc);

        JLabel passLabel = new JLabel("Нууц үг:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Нэвтрэх");
        JButton cancelButton = new JButton("Цуцлах");
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);

        // Add login button event
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // Simple validation
                if (username.length() > 0 && password.length() > 0) {
                    System.out.println("Хэрэглэгч нэвтэрлээ: " + username);
                    showStudentListScreen();
                } else {
                    System.out.println("Нэвтрэх үйлдэл амжилтгүй: Хэрэглэгчийн нэр эсвэл нууц үг хоосон байна");
                    JOptionPane.showMessageDialog(mainFrame,
                            "Хэрэглэгчийн нэр болон нууц үгээ оруулна уу",
                            "Нэвтрэх алдаа",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add cancel button event
        cancelButton.addActionListener(e -> {
            System.out.println("Нэвтрэх цонхыг хаалаа");
            System.exit(0);
        });

        switchPanel(loginPanel);
        mainFrame.setVisible(true);
    }

    // Student List Screen
    private void showStudentListScreen() {
        JPanel listPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Оюутны жагсаалт");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton addButton = new JButton("Шинэ оюутан нэмэх");
        headerPanel.add(addButton, BorderLayout.EAST);
        listPanel.add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "ID", "Нэр", "Мэргэжил", "Курс", "Голч дүн" };
        Object[][] data = new Object[students.size()][5];

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            data[i][0] = s.id;
            data[i][1] = s.name;
            data[i][2] = s.major;
            data[i][3] = s.year;
            data[i][4] = s.gpa;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Засах");
        JButton deleteButton = new JButton("Устгах");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add button event
        addButton.addActionListener(e -> {
            System.out.println("Шинэ оюутан нэмэх товчлуур дарагдлаа");
            currentStudentIndex = -1;
            showStudentFormScreen();
        });

        // Edit button event
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                System.out.println("Оюутан засах: " + students.get(selectedRow).name);
                currentStudentIndex = selectedRow;
                showStudentFormScreen();
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Оюутныг сонгоно уу",
                        "Сонголт хийгдээгүй",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete button event
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String studentName = students.get(selectedRow).name;
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                        studentName + " нэртэй оюутныг устгах уу?",
                        "Устгах уу",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.out.println("Оюутан устгах: " + studentName);
                    students.remove(selectedRow);
                    showStudentListScreen(); // Refresh the list
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Оюутныг сонгоно уу",
                        "Сонголт хийгдээгүй",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Table selection event
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        System.out.println("Хос товшилт: " + students.get(row).name);
                        currentStudentIndex = row;
                        showStudentDetailsScreen();
                    }
                }
            }
        });

        switchPanel(listPanel);
    }

    // Student Form Screen (Add/Edit)
    private void showStudentFormScreen() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setLayout(new BorderLayout());

        String title = (currentStudentIndex == -1) ? "Шинэ оюутан нэмэх" : "Оюутны мэдээлэл засах";
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Буцах");
        headerPanel.add(backButton, BorderLayout.EAST);
        formPanel.add(headerPanel, BorderLayout.NORTH);

        // Form fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID field
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(new JLabel("ID:"), gbc);

        JTextField idField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        fieldsPanel.add(idField, gbc);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(new JLabel("Нэр:"), gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        fieldsPanel.add(nameField, gbc);

        // Major field
        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Мэргэжил:"), gbc);

        JComboBox<String> majorCombo = new JComboBox<>(new String[] {
                "Компьютерийн ухаан",
                "Мэдээллийн технологи",
                "Программ хангамж",
                "Сүлжээний инженерчлэл"
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        fieldsPanel.add(majorCombo, gbc);

        // Year field
        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldsPanel.add(new JLabel("Курс:"), gbc);

        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[] { 1, 2, 3, 4 });
        gbc.gridx = 1;
        gbc.gridy = 3;
        fieldsPanel.add(yearCombo, gbc);

        // GPA field
        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldsPanel.add(new JLabel("Голч дүн:"), gbc);

        JTextField gpaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        fieldsPanel.add(gpaField, gbc);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Хадгалах");
        JButton cancelButton = new JButton("Цуцлах");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        // If editing, populate fields
        if (currentStudentIndex != -1) {
            Student student = students.get(currentStudentIndex);
            idField.setText(student.id);
            nameField.setText(student.name);
            majorCombo.setSelectedItem(student.major);
            yearCombo.setSelectedItem(student.year);
            gpaField.setText(String.valueOf(student.gpa));
        }

        // Back button event
        backButton.addActionListener(e -> {
            System.out.println("Жагсаалт руу буцах товчлуур дарагдлаа");
            showStudentListScreen();
        });

        // Save button event
        saveButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                String major = (String) majorCombo.getSelectedItem();
                int year = (Integer) yearCombo.getSelectedItem();
                double gpa = Double.parseDouble(gpaField.getText());

                // Validation
                if (id.isEmpty() || name.isEmpty()) {
                    throw new Exception("ID болон нэр хоосон байж болохгүй");
                }

                if (gpa < 0 || gpa > 4.0) {
                    throw new Exception("Голч дүн 0-4.0 хооронд байх ёстой");
                }

                // Create or update student
                if (currentStudentIndex == -1) {
                    // New student
                    students.add(new Student(id, name, major, year, gpa));
                    System.out.println("Шинэ оюутан нэмэгдлээ: " + name);
                } else {
                    // Update existing student
                    Student student = students.get(currentStudentIndex);
                    student.id = id;
                    student.name = name;
                    student.major = major;
                    student.year = year;
                    student.gpa = gpa;
                    System.out.println("Оюутны мэдээлэл шинэчлэгдлээ: " + name);
                }

                showStudentListScreen();

            } catch (NumberFormatException ex) {
                System.out.println("Тоон утга буруу байна");
                JOptionPane.showMessageDialog(mainFrame,
                        "Голч дүн тоон утга байх ёстой",
                        "Утга оруулах алдаа",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println("Алдаа: " + ex.getMessage());
                JOptionPane.showMessageDialog(mainFrame,
                        ex.getMessage(),
                        "Утга оруулах алдаа",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel button event
        cancelButton.addActionListener(e -> {
            System.out.println("Хадгалах үйлдлийг цуцаллаа");
            showStudentListScreen();
        });

        switchPanel(formPanel);
    }

    // Student Details Screen
    private void showStudentDetailsScreen() {
        if (currentStudentIndex == -1 || currentStudentIndex >= students.size()) {
            showStudentListScreen();
            return;
        }

        Student student = students.get(currentStudentIndex);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Оюутны дэлгэрэнгүй мэдээлэл");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Буцах");
        headerPanel.add(backButton, BorderLayout.EAST);
        detailsPanel.add(headerPanel, BorderLayout.NORTH);

        // Details
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Add student info
        addDetailRow(infoPanel, gbc, 0, "ID:", student.id);
        addDetailRow(infoPanel, gbc, 1, "Нэр:", student.name);
        addDetailRow(infoPanel, gbc, 2, "Мэргэжил:", student.major);
        addDetailRow(infoPanel, gbc, 3, "Курс:", String.valueOf(student.year));
        addDetailRow(infoPanel, gbc, 4, "Голч дүн:", String.valueOf(student.gpa));

        // Status (this would typically come from a database)
        String status = (student.gpa >= 3.0) ? "Идэвхтэй" : "Анхааруулга авсан";
        addDetailRow(infoPanel, gbc, 5, "Төлөв:", status);

        JScrollPane scrollPane = new JScrollPane(infoPanel);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton editButton = new JButton("Засах");
        JButton deleteButton = new JButton("Устгах");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Back button event
        backButton.addActionListener(e -> {
            System.out.println("Жагсаалт руу буцах товчлуур дарагдлаа");
            showStudentListScreen();
        });

        // Edit button event
        editButton.addActionListener(e -> {
            System.out.println("Оюутан засах: " + student.name);
            showStudentFormScreen();
        });

        // Delete button event
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame,
                    student.name + " нэртэй оюутныг устгах уу?",
                    "Устгах уу",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.out.println("Оюутан устгах: " + student.name);
                students.remove(currentStudentIndex);
                showStudentListScreen();
            }
        });

        switchPanel(detailsPanel);
    }

    // Helper method to add rows to the details screen
    private void addDetailRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelComp, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        JLabel valueComp = new JLabel(value);
        panel.add(valueComp, gbc);
    }
}