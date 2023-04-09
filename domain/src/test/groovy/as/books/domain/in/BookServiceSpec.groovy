package as.books.domain.in

import as.books.domain.InMemoryAuthorRepository
import as.books.domain.InMemoryBookCategoryRepository
import as.books.domain.InMemoryBookRepository
import as.books.domain.exception.BookWithGivenIsbnExistsException
import as.books.domain.exception.DomainObjectNotFoundException
import as.books.domain.model.Author
import as.books.domain.request.BookAddRequest
import spock.lang.Specification

import static as.books.domain.InMemoryBookRepository.*

class BookServiceSpec extends Specification {
    def service = ServiceFactory.createBookService(STUB, InMemoryAuthorRepository.STUB, InMemoryBookCategoryRepository.STUB)

    def "should get book by id"() {
        when:
        def book = service.get(GAME_OF_THRONES.isbn())
        then:
        book == GAME_OF_THRONES
    }

    def "should delete book by id"() {
        given:
        def repository = new InMemoryBookRepository([
                GAME_OF_THRONES,
                HARRY_POTTER
        ])
        def service = ServiceFactory.createBookService(repository, InMemoryAuthorRepository.STUB, InMemoryBookCategoryRepository.STUB)

        when:
        service.delete(GAME_OF_THRONES.isbn())

        then:
        def books = repository.getAll()
        books.size() == 1
        books[0] == HARRY_POTTER
    }

    def "should fail when book with same isbn is created"() {
        given:
        def repository = new InMemoryBookRepository([])
        def service = ServiceFactory.createBookService(repository, InMemoryAuthorRepository.STUB, InMemoryBookCategoryRepository.STUB)
        def bookAddRequest = new BookAddRequest("ISBN-222", "Captain Nemo", "About see", InMemoryAuthorRepository.WILLIAM.id(), Set.of(InMemoryBookCategoryRepository.FANTASY.id()))

        when:
        service.add(bookAddRequest)
        service.add(bookAddRequest)

        then:
        BookWithGivenIsbnExistsException e = thrown()
        e.message == "Book with given isbn exists: " + bookAddRequest.isbn()
    }

    def "should fail author validation when adding new book"() {
        given:
        def repository = new InMemoryBookRepository([])
        def authorId = UUID.randomUUID()
        def service = ServiceFactory.createBookService(repository, new InMemoryAuthorRepository([]), InMemoryBookCategoryRepository.STUB)
        def bookAddRequest = new BookAddRequest("ISBN-222", "Captain Nemo", "About see", authorId, Set.of(InMemoryBookCategoryRepository.FANTASY.id()))

        when:
        service.add(bookAddRequest)

        then:
        DomainObjectNotFoundException e = thrown()
        e.message == "Author: " + authorId
    }

    def "should fail author category when adding new book"() {
        given:
        def repository = new InMemoryBookRepository([])
        def categoryId = UUID.randomUUID()
        def julesVerne = Author.of("Jules", "Verne")
        def authorRepository = new InMemoryAuthorRepository([julesVerne])
        def service = ServiceFactory.createBookService(repository, authorRepository, InMemoryBookCategoryRepository.STUB)
        def bookAddRequest = new BookAddRequest("ISBN-222", "Captain Nemo", "About see", julesVerne.id(), Set.of(categoryId))

        when:
        service.add(bookAddRequest)

        then:
        DomainObjectNotFoundException e = thrown()
        e.message == "BookCategories: [%s]".formatted(categoryId)
    }

    def "should add new book"() {
        given:
        def repository = new InMemoryBookRepository([])
        def julesVerne = Author.of("Jules", "Verne")
        def authorRepository = new InMemoryAuthorRepository([julesVerne])
        def service = ServiceFactory.createBookService(repository, authorRepository, InMemoryBookCategoryRepository.STUB)
        def bookAddRequest = new BookAddRequest("ISBN-222", "Captain Nemo", "About see", julesVerne.id(), Set.of(InMemoryBookCategoryRepository.FANTASY.id()))

        when:
        service.add(bookAddRequest)

        then:
        def bookOpt = repository.getById(bookAddRequest.isbn())

        bookOpt.isPresent()
        def book = bookOpt.get()

        book.isbn() == bookAddRequest.isbn()
        book.title() == bookAddRequest.title()
        book.description() == bookAddRequest.description()
        book.author().id() == bookAddRequest.authorId()
        book.categories().size() == 1
        book.categories()[0] == InMemoryBookCategoryRepository.FANTASY
    }
}
