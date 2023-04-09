package as.books.domain;

import as.books.domain.model.Book;
import as.books.domain.out.BookRepositoryPort;
import as.books.domain.request.BookSearchRequest;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class InMemoryBookRepository extends AbstractInMemoryRepository<String, Book, BookSearchRequest> implements
        BookRepositoryPort {
    private static final Comparator<Book> COMPARATOR = Comparator.comparing(Book::title);

    public static final Book GAME_OF_THRONES = new Book("ISBN-1",
                                                        "Game of thrones",
                                                        "Book with a lot of dragons",
                                                        InMemoryAuthorRepository.MARTIN,
                                                        List.of(InMemoryBookCategoryRepository.FANTASY));
    public static final Book HARRY_POTTER = new Book("ISBN-2",
                                                     "Harry potter",
                                                     "Book with a lot of magic",
                                                     InMemoryAuthorRepository.ROWLING,
                                                     List.of(InMemoryBookCategoryRepository.FANTASY));

    public static InMemoryBookRepository STUB = new InMemoryBookRepository(List.of(GAME_OF_THRONES, HARRY_POTTER));


    public InMemoryBookRepository(final List<Book> objects) {
        super(objects);
    }

    @Override
    protected Function<Book, List<String>> searchPhraseFieldsProvider() {
        return b -> List.of(b.title());
    }

    @Override
    protected Function<Book, String> idFieldProvider() {
        return Book::isbn;
    }

    @Override
    protected Comparator<Book> sortComparator() {
        return COMPARATOR;
    }
}
