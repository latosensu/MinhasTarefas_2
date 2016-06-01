package br.ufscar.minhasTarefas

import grails.converters.JSON

class ListaTarefaController {

    def inserir() {
        String nome = params.nome
        String usuario = params.usuario
        ListaTarefa novaLista = new ListaTarefa(nome: nome, usuario: usuario)

        if (!novaLista.save()) {
            render(novaLista.errors.allErrors as JSON)
            return
        }
        render(novaLista as JSON)
    }

    def listar() {
        render(ListaTarefa.all as JSON)
    }

    def filtrar() {
        String nome = params.nome
        String usuario = params.usuario
        Boolean ativa = params.boolean('ativa')

        def listasFiltradas = ListaTarefa.withCriteria() {
            if (nome) {
                eq('nome', nome)
            }
            if (usuario) {
                eq('usuario', usuario)
            }
            if (ativa != null) {
                eq('ativa', ativa)
            }
        }

        render listasFiltradas as JSON
    }
}
