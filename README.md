# Projeto de Controle de Estoque para Oficina Mecânica
  Sistema desktop de controle de estoque para oficina mecânica. Implementa cadastro completo de usuários e peças, controle de movimentações, persistência em banco de dados e interface gráfica em Java Swing.
  Este é um sistema desktop desenvolvido em **Java (Swing)** com conexão a um banco de dados **MySQL**, utilizando **XAMPP** como ambiente local de desenvolvimento.

## Tecnologias
 - NetBeans IDE
 - JDK 23
 - JavaSwing
 - MySQL
 - JDBC
 - XAMPP
 - Dotenv (Opcional)

## Funcionalidades
 - Autenticação de usuários com validação de credenciais armazenadas no MySQL.
 - Cadastro, edição e remoção de peças com persistência em banco de dados.
 - Validação de campos obrigatórios antes da persistência.
 - Controle básico de usuários (cadastro e autenticação).
 - Tratamento básico de exceções SQL durante operações de persistência.
 - Registro de entradas e saídas de estoque com atualização automática da quantidade disponível.
 - Implementação de regra que impede saída de estoque superior à quantidade cadastrada.

 ## Requisitos
 - [XAMPP](https://www.apachefriends.org/pt_br/index.html) instalado e rodando (Apache + MySQL).
 - Java JDK instalado.
 - NetBeans IDE.
 - [Driver JDBC](https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.4.0/) para MySQL. Baixe o arquivo `mysql-connector-j-8.4.0.jar`
 - (Opcional) Baixe o arquivo [dotenv-java-3.0.0.jar](https://repo1.maven.org/maven2/io/github/cdimascio/dotenv-java/3.0.0/) se for usar um `.env` para armazenar as credenciais de conexão. 

**IMPORTANTE**: Adicione ambos `mysql-connector-j-8.4.0.jar` e `dotenv-java-3.0.0.jar` na biblioteca.
 <div align="center">
  <img src="https://github.com/user-attachments/assets/09d8395e-ca37-4351-b8d3-9b7c7f387866" />
 </div>

 ## Configuração do Banco de Dados
 1. Faça o download do XAMPP.
 2. Inicie o XAMPP e ative **Apache** e **MySQL**.
 <div align="center">
  <img src="https://github.com/user-attachments/assets/ed48724c-e550-4d7c-8fd3-b086ca791376" />
 </div>
 
 3. Acesse o **phpMyAdmin** clicando em **Admin**.
 4. Clique em Novo -> MySQL.
 5. Copie e cole código encontrado no arquivo `.sql` do repositório.
 <div align="center">
  <img src="https://github.com/user-attachments/assets/49b6d720-b0b7-43c2-aea0-217140a2271f" />
 </div>

 ## Configurando a Conexão
 Se optou por usar `.env`:
 Crie um arquivo .env colocando-o na **pasta raiz** do projeto, e insira o as linhas:
```env
DB_URL=jdbc:mysql://localhost:3306/nome-do-banco-de-dados
DB_USER=root
DB_PASSWORD=
```

Se não usar `.env`, edite a classe `MySQLConnection`:
<pre lang="java">
package jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MySQLConnection {
        
    private static final String URL = "jdbc:mysql://localhost/estoque_mecanica";
    private static final String USER = "root";
    private static final String SENHA = ""; // por padrão a senha é vazia no XAMPP
    
    // faz a conexão com o banco de dados MySQL
    public  Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, SENHA);            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao se conectar com o Banco de Dados! " + e.getMessage());
            return null;
        }
    }
    
}
</pre>

## Executando o Projeto
Ao executar o projeto você entrará na tela de login, insira o email e senha do funcionário já criado:
<div align="center">
  <img src="https://github.com/user-attachments/assets/a6fb05b2-afe3-4091-866c-8a0a8369f346" />
</div>

## Status do Projeto  
-- Projeto em andamento - funcionalidades básicas implementadas.  
-- README ainda sendo atualizado conforme updates.
