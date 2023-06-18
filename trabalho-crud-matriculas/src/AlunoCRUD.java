import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoCRUD extends JFrame implements ActionListener {
    private JTextField nomeField, idadeField, emailField, enderecoField, cepField, telefoneField, usuarioField;
    private JPasswordField senhaField;
    private JComboBox<String> cursoComboBox;
    private JTextArea observacoesField;
    private JComboBox<String> ativoCheckBox;
    private JButton cadastrarButton, atualizarButton, excluirButton;

    private JTable tabela;
    private DefaultTableModel tableModel;
    private List<Aluno> alunos;
    private int proximoId;

    private static Aluno aluno = new Aluno();

    public AlunoCRUD() {
        setTitle("Aluno CRUD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome completo*"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Idade na matrícula*"));
        idadeField = new JTextField();
        panel.add(idadeField);

        panel.add(new JLabel("E-mail*"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Endereço*"));
        enderecoField = new JTextField();
        panel.add(enderecoField);

        panel.add(new JLabel("CEP"));
        cepField = new JTextField();
        panel.add(cepField);

        panel.add(new JLabel("Telefone"));
        telefoneField = new JTextField();
        panel.add(telefoneField);

        panel.add(new JLabel("Usuário*"));
        usuarioField = new JTextField();
        panel.add(usuarioField);

        panel.add(new JLabel("Senha*"));
        senhaField = new JPasswordField();
        panel.add(senhaField);

        panel.add(new JLabel("Curso*"));
        cursoComboBox = new JComboBox<>();
        cursoComboBox.addItem("Engenharia");
        cursoComboBox.addItem("Medicina");
        cursoComboBox.addItem("Administração");
        cursoComboBox.addItem("Ads");
        panel.add(cursoComboBox);

        panel.add(new JLabel("Observações"));
        observacoesField = new JTextArea();
        panel.add(new JScrollPane(observacoesField));

        panel.add(new JLabel("Ativo*"));
        ativoCheckBox = new JComboBox<>();
        ativoCheckBox.addItem("Sim");
        ativoCheckBox.addItem("Não");
        panel.add(ativoCheckBox);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(this);
        atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(this);
        excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(excluirButton);

        tabela = new JTable();
        tableModel = new DefaultTableModel(new String[]{"ID", "Nome","Idade","E-mail",
                "Endereço","CEP","Telefone","Curso","Observação", "Ativo"}, 0);
        tabela.setModel(tableModel);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        alunos = new ArrayList<>();
        proximoId = 1;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarButton) {
            cadastrarAluno();
        } else if (e.getSource() == atualizarButton) {
//            atualizarAluno();
        } else if (e.getSource() == excluirButton) {
            excluirAluno();
        }
    }

    private void cadastrarAluno() {
        String nome = nomeField.getText();
        String idadeString = idadeField.getText();
        String email = emailField.getText();
        String endereco = enderecoField.getText();
        String cep = cepField.getText();
        String telefone = telefoneField.getText();
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());
        String curso = (String) cursoComboBox.getSelectedItem();
        String observacoes = observacoesField.getText();
        String ativo = (String)ativoCheckBox.getSelectedItem();

        if (!nome.isEmpty() && !idadeString.isEmpty() && !email.isEmpty() && !endereco.isEmpty() && !usuario.isEmpty() && !senha.isEmpty()) {
            int idade = Integer.parseInt(idadeString);
            Aluno aluno = new Aluno(proximoId, nome, idade, email, endereco, cep, telefone, usuario, senha, curso, observacoes, ativo);
            alunos.add(aluno);
            tableModel.addRow(aluno.toArray());
            proximoId++;

            limparCampos();

            final String query = "INSERT INTO alunos (NomeCompleto, Idade, Email, Endereco, CEP," +
                    " Telefone, Usuario, Senha, Curso, Observacoes, Ativo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = null;
            PreparedStatement prepStmt = null;
            ResultSet rs = null;

            try {
                conn = ConectarBD.getConexao();
                prepStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                prepStmt.setString(1, nome);
                prepStmt.setInt(2, idade);
                prepStmt.setString(3, email);
                prepStmt.setString(4, endereco);
                prepStmt.setString(5, cep);
                prepStmt.setString(6, telefone);
                prepStmt.setString(7, usuario);
                prepStmt.setString(8, senha);
                prepStmt.setString(9, curso);
                prepStmt.setString(10, observacoes);
                prepStmt.setString(11, ativo);

                prepStmt.executeUpdate();

                rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
        }
    }

//    private void atualizarAluno() {
//
//        int selectedRow = tabela.getSelectedRow();
//        if (selectedRow >= 0) {
//            String nome = nomeField.getText();
//            String idadeString = idadeField.getText();
//            String email = emailField.getText();
//            String endereco = enderecoField.getText();
//            String cep = cepField.getText();
//            String telefone = telefoneField.getText();
//            String usuario = usuarioField.getText();
//            String senha = new String(senhaField.getPassword());
//            String curso = (String) cursoComboBox.getSelectedItem();
//            String observacoes = observacoesField.getText();
//            boolean ativo = ativoCheckBox.isSelected();
//
//            if (!nome.isEmpty() && !idadeString.isEmpty() && !email.isEmpty() && !endereco.isEmpty() && !usuario.isEmpty() && !senha.isEmpty()) {
//                int idade = Integer.parseInt(idadeString);
//
//                Aluno aluno = alunos.get(selectedRow);
//                aluno.setNome(nome);
//                aluno.setIdade(idade);
//                aluno.setEmail(email);
//                aluno.setEndereco(endereco);
//                aluno.setCep(cep);
//                aluno.setTelefone(telefone);
//                aluno.setUsuario(usuario);
//                aluno.setSenha(senha);
//                aluno.setCurso(curso);
//                aluno.setObservacoes(observacoes);
//                aluno.setAtivo(ativo);
//
//                // Atualizar linha na tabela
//                tableModel.removeRow(selectedRow);
//                tableModel.insertRow(selectedRow, aluno.toArray());
//
//                limparCampos();
//
//                // Atualizar no banco de dados
//                final String query = "UPDATE alunos SET NomeCompleto=?, Idade=?, Email=?, Endereco=?, CEP=?, " +
//                        "Telefone=?, Usuario=?, Senha=?, Curso=?, Observacoes=?, Ativo=? WHERE ID=?";
//                Connection conn = null;
//                PreparedStatement prepStmt = null;
//
//                try {
//                    conn = ConectarBD.getConexao();
//                    prepStmt = conn.prepareStatement(query);
//                    prepStmt.setString(1, nome);
//                    prepStmt.setInt(2, idade);
//                    prepStmt.setString(3, email);
//                    prepStmt.setString(4, endereco);
//                    prepStmt.setString(5, cep);
//                    prepStmt.setString(6, telefone);
//                    prepStmt.setString(7, usuario);
//                    prepStmt.setString(8, senha);
//                    prepStmt.setString(9, curso);
//                    prepStmt.setString(10, observacoes);
//                    prepStmt.setBoolean(11, ativo);
//                    prepStmt.setInt(12, aluno.getId());
//
//                    prepStmt.executeUpdate();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } finally {
//                    // Certifique-se de fechar as conexões e a instrução preparada
//                    if (prepStmt != null) {
//                        try {
//                            prepStmt.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    if (conn != null) {
//                        try {
//                            conn.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Selecione um aluno para atualizar.");
//        }
//
//    }

    private void excluirAluno() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "Deseja remover o aluno selecionado?", "Remover Aluno", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Aluno aluno = alunos.get(selectedRow);
                tableModel.removeRow(selectedRow);

                final String query = "DELETE FROM alunos WHERE id = ?";

                Connection conn = null;
                PreparedStatement prepStmt = null;

                try {
                    conn = ConectarBD.getConexao();

                    prepStmt = conn.prepareStatement(query);
                    prepStmt.setInt(1, aluno.getId());
                    prepStmt.execute();

                    limparCampos();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para remover.");
        }
    }


//    private void listar() {
//        final String query = "SELECT * FROM crudalunos ORDER BY id";
//
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            conn = ConectarBD.getConexao();
//
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery(query);
//
//            // Limpar o modelo de dados atual da tabela
//            tableModel.setRowCount(0);
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String nome = rs.getString("nome");
//                int idade = rs.getInt("idade");
//                String email = rs.getString("email");
//                String endereco = rs.getString("endereco");
//                String cep = rs.getString("cep");
//                String telefone = rs.getString("telefone");
//                String curso = rs.getString("curso");
//                String observacao = rs.getString("observacao");
//                boolean ativo = rs.getBoolean("ativo");
//
//                // Adicionar uma nova linha ao modelo de dados com os valores obtidos
//                tableModel.addRow(new Object[]{id, nome, idade, email, endereco, cep, telefone, curso, observacao, ativo});
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Certifique-se de fechar as conexões e o resultado
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }



    private void limparCampos() {
        nomeField.setText("");
        idadeField.setText("");
        emailField.setText("");
        enderecoField.setText("");
        cepField.setText("");
        telefoneField.setText("");
        usuarioField.setText("");
        senhaField.setText("");
        cursoComboBox.setSelectedIndex(0);
        observacoesField.setText("");
        ativoCheckBox.setSelectedIndex(0);
    }
}
