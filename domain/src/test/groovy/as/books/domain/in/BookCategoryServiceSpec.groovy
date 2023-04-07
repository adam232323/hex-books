package as.books.domain.in

import as.books.domain.InMemoryBookCategoryRepository
import as.books.domain.request.BookCategorySearchRequest
import spock.lang.Specification

import static as.books.domain.InMemoryBookCategoryRepository.FANTASY
import static as.books.domain.InMemoryBookCategoryRepository.HORROR
import static as.books.domain.InMemoryBookCategoryRepository.THRILLER

class BookCategoryServiceSpec extends Specification {

    def "should get categories by search phrase"() {
        given:
        def service = ServiceFactory.createBookCategoryService(InMemoryBookCategoryRepository.STUB)

        expect:
        resultList == service.getBy(new BookCategorySearchRequest(searchPhrase as String))

        where:
        searchPhrase || resultList
        "dasd"       || []
        "iller"      || [THRILLER]
        "r"          || [HORROR, THRILLER]
        null         || [FANTASY, HORROR, THRILLER]
    }
}
