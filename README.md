# Projeto Spring Exemplo - POC Azure Table Storage

Bem-vindo ao Projeto Spring Exemplo - POC Azure Table Storage! Este é um projeto de prova de conceito que utiliza o Spring Framework para criar uma API REST que interage com o Azure Table Storage.

## Visão Geral

Este projeto tem como objetivo demonstrar o uso do Azure Table Storage para armazenar e recuperar dados por meio de uma API REST construída com o Spring Framework.

## Configuração

Antes de executar o projeto, você precisará configurar algumas variáveis de ambiente para que a interação com o Azure Table Storage funcione corretamente:

- `STORAGE_ACCOUNT_NAME`: O nome da sua conta de armazenamento no Azure.
- `STORAGE_ACCOUNT_KEY`: A chave de acesso à sua conta de armazenamento.
- `TABLE_STORAGE_NAME`: O nome da tabela que você deseja utilizar para este projeto.
- `TABLE_STORAGE_URL`: A URL de acesso à sua conta de table storage.

Certifique-se de ter uma conta na Azure com uma Storage Account configurada, bem como uma tabela criada para este projeto.

## Executando o Projeto

1. Clone este repositório para o seu ambiente local.
2. Configure as variáveis de ambiente listadas acima com as informações corretas da sua conta Azure.
3. Abra o terminal e navegue até o diretório do projeto.
4. Execute o comando `mvn spring-boot:run` para iniciar o aplicativo.
5. A API estará acessível em http://localhost:8080.

## Rotas da API

- `GET /clientes`: Retorna todos os itens da tabela de armazenamento.
- `POST /clientes`: Cria um novo item na tabela de armazenamento. O corpo da solicitação deve conter os detalhes do item.

Lembre-se de que esta é uma POC (Prova de Conceito) e foi desenvolvida com o intuito de testar o table storage.

Para mais detalhes sobre o uso do Azure Table Storage com o Spring, consulte a documentação oficial da Microsoft.

