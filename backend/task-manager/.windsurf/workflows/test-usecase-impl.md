---
description: Criar os usecases e ajustar implementacoes
---

Considere que voce e um especialista em desenvolvimento de software, com conhecimentos profundos em Orientacao a Objetos, SOLID e Clean Arch. 

Aqui tem algumas regras que voce deve seguir 
1. Para cada arquivo criado ou alterado,ao terminar de alterar GERE um commit com o nome: [BACKEND] <intencao da acao>, mas antes confirme com o usuario se pode commitar.
2. Quando o usuario pedir que execute o workflow em todo projeto, voce pode executar os STEPS para cada fluxo por vez. 
    Por exemplo: Primeiro execute os STEPS para users, depois execute os STEPS para TASKS e etc..


Aqui abaixo eu listarei a INTENCAO e o.
INTENCAO representa o meu objetivo.
STEPS sequencia que deve ser seguida.
-------------------------------------------------

INTENCAO:
Eu finalizei meu projeto, mas gostaria de ajusta-lo aos conceitos do clean arch. Eu quero que:


STEPS:
1. Analise todas classes Controllers e todos endpoints do projeto.
2. Crie testes unitarios para todos endpoints e todos os controllers.
3. Crie uma classe usecase para cada endpoint que voce encontrou. 
    Essa classe usecase deve ter como prefixo o nome da intencao do fluxo do endpoint e o suffixo sera UseCase.
    Exemplo:  endpoint getUser()
                    usecase -> GetUserUseCase
4. Agora eu quero que cada usecase se integre ao service, replicando a mesma chamada de metodo do service que existe no endpoint do controller. O usecase deve ser o responsavel pela comunicacao com o servicos.
5. Anote cada usecase com a anotacao @Component a nivel de classe.
6. Crie os testes unitarios dos usecases.
7. Agora remova a integracao dos services nos controllers, injete os seus respectivos usecases e faca as chamadas de metodos correspondentes.
8. Faca a correcao nos testes unitarios dos controllers.
9. Testes todos os testes e corrija se for necessario