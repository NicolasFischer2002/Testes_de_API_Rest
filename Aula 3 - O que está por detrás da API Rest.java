/* 

API => Interface entre dois softwares
UI => Interface entre um software e um ser humano

Services => Camada dentro do backend da aplicação que armazena as regras
de negócio;

Repository => Camada responsável por trafegar as informações entre as regras de negócio 
e o banco de dados via query, SQL;

Graças ao uso de APIs Rest, softwares escritos em linguagens diferentes pode trabalhar juntos.
Uma API escrita em C# pode ter seus serviços consumidos por um software escrito em Delphi, C++
dentre outras linguagens de programação.


    ╔════════════════════════════╗                   ╔═══════════════════════════════════════════════════════════════╗
                        ║                   ║                                                               ║
    ║                            ║                   ║                                                               ║
    ║    Aplicação que consome   ║  Requisição Web   ║   Controller => Services => Repository => Banco de dados      ║
    ║   os serviços de uma API   ║       ═══>        ║                                                               ║
    ║   (Postman, por exemplo)   ║       <═══        ║   Controller <= Services <= Repository <= Banco de dados      ║
    ║                            ║                   ║                                                               ║
    ╚════════════════════════════╝                   ╚═══════════════════════════════════════════════════════════════╝


    Controller => Determina o que veio na requisição e qual método deverá ser chamado

    O Controller/Controlador fica disponível via http, de maneira remota, para que pessoas autenticadas e autorizadas
    possam realizar requisições e consumir os serviços prestados pela API.

    A Api pode ser remota, como no caso anterior, que será consumida via requisições web, ou pode ser local, 
    como a Api do Windows.

*/

// Exemplo de método localizado na Controller de uma API
// Este método utiliza o verbo GET. O método tem por objetivo
// apenas buscar informações no servidor. Sendo assim, o verbo
// mais apropriado dentro os demais - POST, PUT etc. - é o método
// GET. GET é considerado um método SAFE, pois não altera o estado
// do servidor, não atualiza, insere ou remove dados,
// apenas consulta.

@ApiOperation(value = "Retorna todas as viagens")
@RequestMapping(value = "/v1/viagens", method = requestMethod.GET, produces = "application/json")
@PreAuthorize("hasAnyRole('USUARIO')")
public ResponseEntity<Response<List<Viagem>>> listar(
            @RequestParam(value = "regiao", required = false) String regiao,
            @RequestHeader(name = "SeuCabecalho", required = false) String seuCabecalho) {

list<Viagem> viagens = null;

if (regiao == null){
    viagens = viagemService.listar();
} else {
    viagens = viagemService.BuscarViagensPorRegiao(regiao);
}

Response<list<Viagem>> viagensResponse = new Response<>();
viagensResponse.setData(viagens);
return ResponseEntity.status(httpStatus.OK).body(viagensResponse);

}

// O retorno é realizado em JSON, que é uma representação textual das informações.
// É um tipo de retorno reconhecido pela maioria das linguagens de programação,
// possibilitando a interoperabilidade entre softwares.
// O software requisitante recebe o JSON e o processa, seja para renderizar as 
// informações retornadas na tela para o usuário, ou para preencher um cadastro,
// em suma, para o que for necessário.