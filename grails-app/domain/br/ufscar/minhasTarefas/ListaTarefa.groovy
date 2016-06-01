package br.ufscar.minhasTarefas

class ListaTarefa {

    String nome
    String usuario
    Boolean ativa = true

    static constraints = {
        nome size: 5..60, unique: ['usuario']
        usuario size: 6..10
    }
}
