# API REST - Funcionários

## Sobre
Aplicação desenvolvida em Spring Boot com Java 21 no padrão API REST. Conta com 21 endpoints para gerenciar operações em 5 tabelas com vínculos (cargos, setores, cidades, endereços e funcionários).
Boas práticas de desenvolvente foram implementadas, por exemplo, divisão de responsabilidades usando arquitetura de software, cobertura de testes para validar funcionalidades, logs para registrar
ocorrências durante execução, paginação de resultados para não sobrecarregar a memória levando a erros de stack overflow. Além disso, versionamento de rotas URL para evitar quebra de compatibilidade 
no caso de aplicações que já utilizavam alguma rota anterior a mudança realizada.

## Tecnologias

<table>
<tr>
  <th>Nome</th>
  <th>Função</th>
</tr>

<tr>
  <td>Intellij</td>
  <td>IDE</td>
</tr>

<tr>
  <td>Spring Boot</td>
  <td>Framework - Java 21</td>
</tr>

<tr>
  <td>Gradle</td>
  <td>Gerenciador de dependências</td>
</tr>

<tr>
  <td>Mockito</td>
  <td>Simulador de Objetos Para Testes</td>
</tr>

<tr>
  <td>Junit</td>
  <td>Executar os Testes Java</td>
</tr>

<tr>
  <td>Logback</td>
  <td>Ferramenta de Logging</td>
</tr>
  
</table>


## Implementações
- 21 Rotas URL
- Arquiteta de Software
- Logs Diariamente
- Testes Unitários
- Paginação
- Versionamento das rotas URL
- DTOs para input e output
- Status Codes
- Validation

## Próximas Implementações
- Limite de requisição por usuário (Evitar sobrecarga e garantir uso justo dos recursos)
- Maior cobertura de testes
- Versionamento banco de dados
- Reimplementar ID para evitar ataque de enumeração de ID
- Docker

## Exemplo de Rotas URL

<table>
  <tr>
    <th>Verbo</th>
    <th>Rota</th>
    <th>Body na request</th>
  </tr>
  <tr>
    <td>GET</td>
    <td>/v1/funcionarios/listar/pagina/1</td>
    <td>false</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/v1/funcionarios/listar/id/1</td>
    <td>false</td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/v1/funcionarios/registrar</td>
    <td>true</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/v1/funcionarios/alterar/1</td>
    <td>true</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/v1/funcionarios/alterar/1/status/true</td>
    <td>false</td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>/v1/funcionarios/excluir/1</td>
    <td>false</td>
  </tr>
  
</table>

## Tabelas
- cargos
- setores
- cidades
- endereços
- funcionarios

## Arquitetura Principal
- Aspect
- Config
- Controller
- Dto
- Exception
- Model
- Repository
- Service

  
## Requisitos para Startar Aplicação
- Gradle 9.3.1
- Spring Boot 3.5.10
- Intellij
- PgAdmin4 (PostgreSQL)
- Postman (Para testar requisições)

## Como Startar Aplicação
- Clone este repositório na sua máquina
- Abra o projeto com o intellij
- Crie um banco de dados com nome (db_funcionarios)
- Acesse o arquivo src > main > resources > application.properties
- Em application.properties verifique o username e password do banco de dados postgreSQL
- Verifique também server.port
- No intellij, pressione o botão verde de start
- Agora localize o arquivo postman.json em resources > static
- Copie e cole esse arquivo em um local no seu computador de fácil acesso
- Abra o postman, procure pela opção importar, pressione, procure a opção para localizar arquivos
- Basta procurar o arquivo postman.json copiado
- Se tudo ocorreu bem, você terá acesso a todas as rotas configuradas para teste de requisição
- Por último, procure por environment do lado direito do postman
- Crie uma variable de nome startUrl, com value http://localhost:8181 (essa é a porta configurada lá no arquivo application.properties do Apring Boot)
- Agora é só testar cada rota fazendo uma requisição
  
O projeto foi configurado para deletar dados antigos e salvar novos dados iniciais a cada start na aplicação (Recomendado para ambiente de desenvolvimento).






