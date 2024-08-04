import models.Author;
import models.Book;
import models.Genre;
import models.Publisher;
import utils.Library;


import java.util.Arrays;
import java.util.Scanner;
/// какая-то фигня
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library databaseLibrary = new Library();
        boolean exit = false;
        while (!exit) {
            for (String s : Arrays.asList( "\nОнлайн Библиотека\n",
                    "Меню: ",
                    "1. Добавить книгу",
                    "2. Удалить книгу",
                    "3. Найти книгу по ISBN",
                    "4. Найти книгу по автору",
                    "5. Найти книгу по Издателю",
                    "6. Найти книгу по Жанру",
                    "7. Изменить книгу",
                    "8. Показать все книги",
                    "9. Добавить автора",
                    "10. Добавить Издательство",
                    "11. Добавить Жанр",
                    "12. Удаление базы данных",
                    "13. Выход",
                    "Выберите пункт меню: ")) {
                System.out.println(s);
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice) {
                case 1:
                    System.out.print("Введите ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Введите название книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите Фамилию автора: ");
                    String authorlastName = scanner.nextLine();
                    System.out.print("Введите Имя автора: ");
                    String authorFirstName = scanner.nextLine();
                    System.out.print("Введите имя Издательства: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Введите Жанр книги: ");
                    String genre = scanner.nextLine();
                    System.out.print("Введите Цену книги: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Введите колличество страниц книги: ");
                    int pages = Integer.parseInt(scanner.nextLine());
                    Author newAuthor  = new Author(authorFirstName,authorlastName);
                    Publisher newPublisher = new Publisher(publisher);
                    Genre newGenre  = new Genre(genre);
                    Book newBook = new Book(title,price,pages,isbn,newAuthor,newPublisher,newGenre);
                    if(databaseLibrary.addBook(newBook)) {
                        System.out.println("Книга успешно добавлена");
                    }
                    else {
                        System.out.println("Книга с указанным ISBN уже существует");
                    }
                    break;
                case 2:
                    System.out.println("Введите ISBN книги для удаления: ");
                    String isbnToRemove = scanner.next();
                    Book bookToRemove = databaseLibrary.findBookByIsbn(isbnToRemove);
                    if(bookToRemove != null && databaseLibrary.removeBook(bookToRemove)) {
                        System.out.println("Книга успешно удалена");
                    }
                    else {
                        System.out.println("Книга не найдена");
                    }
                    break;
                case 3:
                    System.out.println("Введите ISBN книги для поиска: ");
                    String isbnToFind = scanner.next();
                    Book bookToFind = databaseLibrary.findBookByIsbn(isbnToFind);
                    System.out.println(bookToFind != null ? bookToFind : "Книга не найдена");
                    break;
                case 4:
                    System.out.println("Введите Имя автора ");
                    String authorToFindFirstName= scanner.nextLine();
                    System.out.println("Введите Фамилию автора ");
                    String authorToFindLastName= scanner.nextLine();
                    Author authorToFind = new Author(authorToFindFirstName, authorToFindLastName);
                    for(var book : databaseLibrary.findBookByAuthor(authorToFind)) {
                        System.out.println(book);
                    }
                    break;
                case 5:
                    System.out.println("Введите Имя издателя ");
                    String publisherToFindName= scanner.nextLine();
                    Publisher publisherToFind = new Publisher(publisherToFindName);
                    for(var book : databaseLibrary.findBookByPublisher(publisherToFind)) {
                        System.out.println(book);
                    }
                    break;
                case 6:
                    System.out.println("Введите Жанр книги ");
                    String genreToFindName= scanner.nextLine();
                    Genre genreToFind = new Genre(genreToFindName);
                    for(var book : databaseLibrary.findBookByGenre(genreToFind)) {
                        System.out.println(book);
                    }
                    break;
                case 7:
                System.out.println("Введите ISBN книги для изменения: ");
                    String isbnToUpdate = scanner.nextLine();
                    Book bookToUpdate = databaseLibrary.findBookByIsbn(isbnToUpdate);
                    if(bookToUpdate != null) {
                        System.out.println("Книга: "+bookToUpdate .getTitle());
                        System.out.print("Введите новое название книги: ");
                        String newTitle = scanner.nextLine();
                        //------------------------------------------------------
                        System.out.println("Цена книги: " + bookToUpdate.getPrice());
                        System.out.print("Введите новое Цену книги: ");
                        double newPrice = Double.parseDouble(scanner.nextLine());
                        //------------------------------------------------------
                        System.out.println("Страниц книги: : " + bookToUpdate.getPages());
                        System.out.print("Введите новое колличество страниц книги: ");
                        int newPages = Integer.parseInt(scanner.nextLine());
                        //------------------------------------------------------

                        Author findAu = databaseLibrary.findAuthorId(bookToUpdate.getAuthorId());
                        System.out.println("Фамилия автора: " + findAu.getLastName());
                        System.out.print("Введите новую Фамилию автора: ");
                        String newAuthorLastName = scanner.nextLine();
                        System.out.println("Имя автора: " + findAu.getFirstName());
                        System.out.print("Введите новое Имя автора: ");
                        String newAuthorFirstName = scanner.nextLine();
                        //------------------------------------------------------
                        Publisher findPub = databaseLibrary.findPublisherId(bookToUpdate.getPublisherId());
                        System.out.println("Имя Издательства: " + findPub.getName());
                        System.out.print("Введите новое имя Издательства: ");
                        String newPublisherName = scanner.nextLine();
                        //------------------------------------------------------
                        Genre findGenre = databaseLibrary.findGenreId(bookToUpdate.getGenreId());
                        System.out.println("Жанр книги: " + findGenre.getName());
                        System.out.print("Введите новый Жанр книги: ");
                        String newGenreName = scanner.nextLine();
                        //------------------------------------------------------
                        Author newAuthorr = new Author(newAuthorFirstName, newAuthorLastName);
                        Publisher newPublisherr = new Publisher(newPublisherName);
                        Genre newGenr = new Genre(newGenreName);
                        bookToUpdate.setTitle(newTitle);
                        bookToUpdate.setPrice(newPrice);
                        bookToUpdate.setPages(newPages);
                        bookToUpdate.setAuthor(newAuthorr);
                        bookToUpdate.setPublisher(newPublisherr);
                        bookToUpdate.setGenre(newGenr);

                        if(databaseLibrary.updateBook(bookToUpdate)) {
                            System.out.println("Книга успешно изменена");
                        }
                        else {
                            System.out.println("Книга не найдена");
                        }
                    }
                    else {
                        System.out.println("Книга не найдена");
                    }
                    break;
                case 8:
                    System.out.printf("Всего найдено %d книг в библиотеке\n", databaseLibrary.listBooks().size());
                    for(var book : databaseLibrary.listBooks()) {
                        System.out.println(book);
                    }
                    break;
                case 9:
                    System.out.print("Добавить Имя Автора: ");
                    String authorToNewFirstName= scanner.nextLine();
                    System.out.print("Добавить Фамилию Автора: ");
                    String authorToNewLastName= scanner.nextLine();
                    Author newToNewAuthor = new Author(authorToNewFirstName, authorToNewLastName);
                    if(databaseLibrary.addNewAuthor(newToNewAuthor)>0) {
                        System.out.println("Автор успешно добавлен");
                    }else {
                        System.out.println("Автор уже существует");
                    }
                    break;
                case 10:
                    System.out.print("Добавить издателя: ");
                    String publisherToNewName= scanner.nextLine();
                    Publisher newToNewPublisher = new Publisher(publisherToNewName);
                    if(databaseLibrary.addNewPublisher(newToNewPublisher)>0) {
                        System.out.println("Издатель успешно добавлен");
                    }else {
                        System.out.println("Издатель уже существует");
                    }
                    break;
                case 11:
                    System.out.print("Добавить жанр: ");
                    String genreToNewName= scanner.nextLine();
                    Genre newToNewGenre = new Genre(genreToNewName);
                    if(databaseLibrary.addNewGenre(newToNewGenre)>0) {
                        System.out.println("Жанр успешно добавлен");
                    }else {
                        System.out.println("Жанр уже существует");
                    }
                    break;
                case 12:
                    System.out.print("Введите название базы данных для удаления: ");
                    //String dbName = scanner.nextLine();
                    //utils.DatabaseManager.dropDatabase(scanner.nextLine());
                    break;
                case 13:
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректная команда. Повторите ввод");
            }
        }
        scanner.close();
    }
}