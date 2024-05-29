# MilExpress

MilExpress é uma plataforma inovadora para consulta e realização de pedidos via delivery, composta por um website e um sistema web. Desenvolvido para uma rede de restaurantes em expansão, o objetivo principal é aumentar a visibilidade e a eficiência operacional do negócio.

## Descrição do Projeto

MilExpress foi concebido como um projeto backend utilizando o Java Spring Framework. As principais tecnologias empregadas incluem Hibernate para o mapeamento objeto-relacional, JWT (JSON Web Token) para autenticação e segurança, e MySQL como banco de dados relacional.

Este projeto foi desenvolvido como parte da disciplina de Gestão de Projetos no curso de Sistemas de Informação.

## Funcionalidades

- **Cadastro de Usuários:** Permite aos novos usuários se cadastrarem na plataforma.
- **Autenticação e Autorização:** Utiliza JWT para garantir a segurança das transações e acessos.
- **Consulta de Restaurantes e Cardápios:** Oferece uma interface para os usuários explorarem restaurantes e seus respectivos menus.
- **Realização de Pedidos:** Funcionalidade para efetuar pedidos de forma simples e rápida.
- **Histórico de Pedidos:** Permite aos usuários visualizarem seus pedidos anteriores.

## Tecnologias Utilizadas

- **Java Spring Framework:** Base do desenvolvimento backend.
- **Hibernate:** Framework de mapeamento objeto-relacional para persistência de dados.
- **JWT (JSON Web Token):** Utilizado para autenticação segura.
- **MySQL:** Banco de dados relacional utilizado para armazenar informações do sistema.

## Estrutura do Projeto

- **Controller:** Gerencia as requisições HTTP e envia as respostas adequadas.
- **Service:** Contém a lógica de negócios da aplicação.
- **Repository:** Interage com o banco de dados utilizando o Hibernate.
- **Model:** Representa as entidades do banco de dados.

## Requisitos

- **Java 8+**
- **Spring Boot**
- **MySQL**
- **Maven**

## Instalação e Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/caionovaiss/milexpress-server.git
2. Navegue até o diretório do projeto:
   ```bash
   cd milexpress
3. Configure o banco de dados MySQL no arquivo application.properties:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/milexpress
   spring.datasource.username=seu-usuario
   spring.datasource.password=sua-senha
4. Compile e execute o projeto utilizando Maven:
   ```bash
   mvn spring-boot:run
