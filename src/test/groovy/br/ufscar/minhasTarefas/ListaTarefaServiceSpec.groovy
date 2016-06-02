package br.ufscar.minhasTarefas

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

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

}
