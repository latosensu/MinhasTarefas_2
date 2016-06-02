package br.ufscar.minhasTarefas

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListaTarefaService)
@Mock(ListaTarefa)
class ListaTarefaServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Listar listas de tarefas quando não existe nenhuma cadastrada no banco"() {
        expect: "Lista vazia"
        service.listar().empty
    }

    void "Listar listas de tarefas quando existem listas cadastradas no banco"() {
        given: "Duas listas cadastradas"
        def listaTarefa1 = new ListaTarefa().save(flush: true, validate: false)
        def listaTarefa2 = new ListaTarefa().save(flush: true, validate: false)

        when: "Lista as listas de tarefa"
        def listas = service.listar()

        then: "A lista tem tamanho 2"
        listas.size() == 2

        and: "A lista contém as duas listas cadastradas"
        listas.containsAll(listaTarefa1, listaTarefa2)
    }

    @Unroll("O filtro retorna #tamanhoEsperado listas para o usuario #usuario")
    void "Filtrar listas de tarefas pelo usuario"() {
        setup: "Existem duas listas para o usuario Spock"
        def listaTarefa1 = new ListaTarefa(usuario: "Spock").save(flush: true, validate: false)
        def listaTarefa2 = new ListaTarefa(usuario: "Spock").save(flush: true, validate: false)

        and: "Uma lista para o usuario Klingon"
        def listaTarefa3 = new ListaTarefa(usuario: "Klingon").save(flush: true, validate: false)

        when: "Filtra as listas de tarefas de um usuário"
        def listas = service.filtrar(null, usuario)

        then: "A lista tem o tamanho esperado"
        listas.size() == tamanhoEsperado

        where:
        usuario   || tamanhoEsperado
        "Spock"   || 2
        "Klingon" || 1
        "Kirk"    || 0
    }

}
