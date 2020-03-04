## Título
Academic content aggregation application

## Descrição
A aplicação BDA deverá permitir o acesso à informação académica disponibilizada através de vários canais, nomeadamente, Email (v.g. emails enviados pelo diretor de departamento/curso/ano, emails enviados pela plataforma de eLearning, etc.), Facebook (v.g. posts da conta Facebook do ISCTE/departamento/curso) e Twitter (v.g. tweets da conta Twitter do ISCTE/departamento/curso).
A aplicação deverá permitir que o utilizador não só aceda/consulte, mas também possa enviar informação através desses canais (v.g. resposta a emails, posts Facebook, retweets).
Os dados das configurações associadas aos serviços referidos (identificação de contas/utilizadores, informação de autenticação/tokens de acesso, etc.) deverão ser armazenados num ficheiro XML (com nome config.xml) e deverão poder ser consultados e editados (criados, alterados, removidos) através da interface gráfica da aplicação BDA.
Deverá ser possível ativar/desativar serviços, bem como permitir a criação de filtros para os conteúdos fornecidos pelos serviços, tendo em consideração as características de cada serviço e de cada API (Application Programming Interface) de acesso ao serviço. Entre os critérios para filtragem da informação que devem ser contemplados estão a origem da mensagem (v.g. conta/utilizador), palavras chave no conteúdo (v.g. exame), período a que deve respeitar a informação recolhida nesses serviços (v.g. últimas 24h), entre outros critérios que o grupo de trabalho entenda relevantes para a melhoria das funcionalidades da aplicação. Os critérios a usar nos filtros associados aos diferentes serviços deverão ser guardados no documento XML de configuração dos serviços (config.xml).
A informação obtida a partir dos vários serviços (fontes de informação académica) deve ser apresentada na interface gráfica da aplicação, seguindo uma estrutura do tipo timeline (informação organizada cronologicamente), onde sejam facilmente identificados os dados principais da mensagem (v.g. fonte, data/hora, título) e caso o utilizador pretenda, seja possível expandir e visualizar todos os detalhes da mensagem.
A aplicação deverá permitir o modo de funcionamento offline, ou seja, o utilizador poderá continuar a consultar os últimos conteúdos que a aplicação conseguiu obter dos vários serviços, mesmo que a aplicação tenha perdido a ligação de rede e o utilizador tenha fechado e voltado a abrir a aplicação.
A aplicação BDA deve ser uma aplicação Java Desktop.

## Estrutura do repostório GIT
* Pasta src/BDA, contendo o código fonte desenvolvido pelo grupo para o projeto;
* Pasta src/jUnitTests, contendo os ficheiros relacionados com os testes unitários (jUnit) desenvolvidos para o projeto;
* Pasta JavaDoc, localizada na raiz do repositório, contendo os documentos JavaDoc gerados;
* Pasta TestCoverage, localizada na raiz do repositório, contendo os relatórios gerados com o plugin Eclipse EclEmma, indicando sucesso para todos os testes realizados e uma cobertura de testes superior a 75%, em pelo menos duas métricas de cobertura de testes;
* Pasta CodeInspection, localizada na raiz do repositório, contendo um relatório de code inspection relativo ao software desenvolvido para o projeto;
* Pasta ReverseEngineeringModels, localizada na raiz do repositório, contendo ficheiros/imagens com nome começado por ClassesDiagram-*.jpg, correspondentes aos diagramas de classes do software desenvolvido pelo grupo, gerado com recurso a uma ferramenta de reverse engineering (v.g. plugin Eclipse ModelGoon), a partir do código fonte do projeto.

## Tecnologias
* Ambiente de desenvolvimento: Eclipse IDE;
* Linguagens de programação: JAVA;
* APIs de integração com as várias fontes de informação/serviços: twitter4j, restfb, javax.mail;
* Plugins Eclipse: EclEmma, Modelgoon;
* Ferramenta de automação: Maven.


## ES1-2018-METI-PL-91
Grupo:
68092 - Diana Salvador
65799 - Ricardo Ferreira
73422 - Ivo Carvalho

Youtube link with a demonstration of the developed application:
https://youtu.be/CAQD7Dt0byY

