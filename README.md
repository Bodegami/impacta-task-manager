# PROJETO DE SOFTWARE: TASK MANAGER
</br>

### Descricao:

<p>
  O task manager e um gerenciador de tarefas, que permite um usuario cadastar, gerenciar e compartilhar suas tarefas com outros colegas.
  E um projeto de software desenvolvido para disciplia Software Product: Analysis, Specification, Project & Implementation da faculdade Impacta e foi baseado no Trello e Jira.
</p>
</br>

### Execucao:

</br>

#### Camada de Dados:

Primeiro precisamos ter o docker instalado na maquina e em execucao.
Depois e preciso executar o docker-compose dentro do diretorio (./) com o seguinte comando:
```

docker-compose up --build -d

```

#### Camada de Backend:

Agora com o docker executando a imagem do PostgreSQL e com as tabelas criadas, podemos executar o backend.
Voce pode carregar o projeto do backend a partir do diretorio (./backend/task-manager/) e executar na sua IDE favorita, ou dentro do diretorio (./backend/task-manager/) executar o seguinte comando:
```

./gradlew bootRun

```

Agora com backend em execucao e conectado ao banco de dados, caso queira, voce pode testar essa integracao via postman/insomnia usando a collection que se encontra na raiz do projeto.

#### Camada de FrontEnd:

Vamos integrar o frontend e para isso precisamos do Node e NPM instalados na maquina.
Acesse o diretorio (./frontend/) e execute o comando abaixo para instalar todas dependencias necessarias:
```

npm install

```
Depois da instalacao, execute o comando para rodar o projeto:
```

npm run dev

```

Apos os passos acima, basta acessar o endereco <b>http://localhost:5173/</b> e testar a vontade.




