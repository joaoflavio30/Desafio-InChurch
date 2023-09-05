inChurch Movies
=============================

![Alt text](https://bitbucket.org/inradar/inchurch-android-challenge/raw/0be3880f3ef7befaae9a5e3f7d7a4c90c9287e46/images/img1.png) ![Alt text](https://bitbucket.org/inradar/inchurch-android-challenge/raw/0be3880f3ef7befaae9a5e3f7d7a4c90c9287e46/images/img2.png) ![Alt text](https://bitbucket.org/inradar/inchurch-android-challenge/raw/0be3880f3ef7befaae9a5e3f7d7a4c90c9287e46/images/img3.png)

Features
=============================
*  Descobrir os filmes mais populares, filmes em tendência, e filmes que estão por vir atualmente
*  Favoritar os filmes que voce gosta ou quer ver
*  Pesquisar por filmes

Libs
=============================
* * *
*  Usei Coroutines para lidar com tarefas assíncronas juntamente com o Flow, a quais sao totalmente compativeis, o uso do Flow foi escolhido por sua versatilidade e reatividade, o Flow foi usado nesse projeto para que os fragments possam observa-los e de acordo com o estado atual, a UI se comportar de acordo.

*  Usei Room para persistencia de dados local, com ele + Flow a Ui consegue reagir diretamente a uma mudanca no banco, fora que o Room é ótimo para diminuição do código Boilerplate, e simplifica muito a criação e gerenciamento do banco de dados.

*  Paging é uma ótima lib para lidar com alta busca de dados, através da paginação dele há o "Scroll infinito", ela integrada com a RecyclerView consegue carregar dados sob demanda o que ajuda bastante na memória e na perfomance do app.

*  Gson para conversão de requisições Json para objetos Kotlin

*  Retrofit para solicitações de rede, ela facilita muito o Rest, simplifica muito o código diminuindo o boilerplate

*  Dagger/Hilt para diminuição do Boilerplate, automatizando grande parte da geração de código necessária para injeção de dependência, o que facilita a configuração e o uso.

*  Glide para carregamento de imagem

*  Mockk para fazer stub e mockar objetos de teste, Turbine para testar Fluxos em testes unitarios
* * *

Arquitetura
=============================

Em meu projeto, adotei a arquitetura MVVM (Model-View-ViewModel) para separar a lógica de apresentação da lógica de negócios. A principal vantagem dessa abordagem é a capacidade de manter os dados na ViewModel. Isso garante que os dados permaneçam persistentes, independentemente do ciclo de vida do Fragment ou da Activity.Usei também Clean Architecture para separação de responsabilidades, Dividi o app em camadas distintas, como a camada de apresentação (UI), a camada de regra de negócios (use cases) e a camada de dados (repositórios).

* * *

Instalação
=============================
* [Obtenha sua API_KEY aqui](https://www.themoviedb.org/settings/api)
* [Escolha o tamanho das imagens](https://developer.themoviedb.org/docs/image-basics)
* coloque os tamanhos das imagens de acordo com guia, e bote sua API_KEY no arquivo local.properties