package as.books.domain.in

import as.books.domain.InMemoryAuthorRepository
import as.books.domain.exception.DomainObjectNotFoundException
import as.books.domain.request.AuthorAddRequest
import as.books.domain.request.AuthorSearchRequest
import spock.lang.Specification

import static as.books.domain.InMemoryAuthorRepository.*

class AuthorServiceSpec extends Specification {

    def service = ServiceFactory.createAuthorService(STUB)

    def "should get author by id" (){
        when:
        def author = service.get(WILLIAM.id())
        then:
        author == WILLIAM
    }

    def "should add new author" (){
        given:
        def repository = new InMemoryAuthorRepository(List.of())
        def service = ServiceFactory.createAuthorService(repository)
        def request = new AuthorAddRequest("John", "Smith")

        when:
        def author = service.add(request)

        then:
        def authors = repository.getAll()
        authors.size() == 1
        authors[0] == author

        author.id() != null
        author.firstName() == request.firstName()
        author.lastName() == request.lastName()
    }

    def "should throw domain not found exception when author not found by id" (){
        when:
        service.get(UUID.randomUUID())
        then:
        thrown(DomainObjectNotFoundException)
    }

    def "should get authors by search phrase"() {
        expect:
        resultList == service.getBy(new AuthorSearchRequest(searchPhrase as String))

        where:
        searchPhrase || resultList
        "dsadaf2d"   || []
        "ill"        || [WILLIAM]
        "l"          || [ROWLING, WILLIAM]
        null         || [MARTIN, ROWLING, WILLIAM]
    }
}
