package br.ufscar.minhasTarefas

import grails.converters.JSON
import grails.validation.ValidationException

class ListaTarefaController {

    def listaTarefaService

    def inserir() {
        String nome = params.nome
        String usuario = params.usuario
        ListaTarefa novaLista = null
        try {
            novaLista = listaTarefaService.inserir(nome, usuario)
        }
        catch (ValidationException validationException){
            render(validationException.errors.allErrors as JSON)
            return
        }
        catch (Exception exception){
            render([erro: exception.localizedMessage] as JSON)
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

    def remover(ListaTarefa listaTarefa) {
        if (!listaTarefa) {
            render(['erro': "Não existe a lista selecionada"]) as JSON
            return
        }
        listaTarefa.delete()
        render(['sucesso': "Lista de tarefas removida com sucesso"]) as JSON
    }
}
