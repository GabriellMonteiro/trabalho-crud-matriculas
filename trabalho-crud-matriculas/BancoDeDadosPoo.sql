CREATE TABLE Alunos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome_completo VARCHAR(100) NOT NULL,
  idade INT NOT NULL,
  email VARCHAR(100) NOT NULL,
  endereco VARCHAR(200) NOT NULL,
  cep VARCHAR(10),
  telefone VARCHAR(20),
  usuario VARCHAR(50) NOT NULL,
  senha VARCHAR(50) NOT NULL,
  curso VARCHAR(50) NOT NULL,
  observacoes TEXT,
  ativo ENUM('sim', 'não') NOT NULL
);