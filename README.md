inChurch Movies
=============================

![Alt text](https://bitbucket.org/joao30/inchurch-android-challenge/raw/009d4e2706f7c6607f09930389a75578d5f9de88/images/Screenshot_20230905_012657.png) ![Alt text](https://bitbucket.org/joao30/inchurch-android-challenge/raw/48a1d46410cd348af0d59c6ac2483ea753d80e25/images/Screenshot_20230905_012755.png) ![Alt text](https://bitbucket.org/joao30/inchurch-android-challenge/raw/90ca141f9a4d782f5396290f7a429088484f9059/images/Screenshot_20230905_012939.png) ![Alt text](https://bitbucket.org/joao30/inchurch-android-challenge/raw/90ca141f9a4d782f5396290f7a429088484f9059/images/Screenshot_20230905_033127.png)

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