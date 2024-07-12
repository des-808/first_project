import BookLib.Author;
import BookLib.Book;
import BookLib.Genre;
import BookLib.Publisher;

import java.util.Arrays;
import java.util.Scanner;
/// какая-то фигня
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library databaseLibrary = new Library();
        boolean exit = false;
        while (!exit) {
            for (String s : Arrays.asList( "\nОнлайн Библиотека\n", "Меню: ", "1. Добавить книгу", "2. Удалить книгу", "3. Найти книгу по ISBN", "4. Найти книгу по автору", "5. Изменить книгу", "6. Показать все книги","7. Удаление базы данных", "8. Выход", "Выберите пункт меню: ")) {
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
                    System.out.print("Введите полное имя автора: ");
                    String authorr = scanner.nextLine();
                    System.out.print("Введите имя Издательства: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Введите Жанр книги: ");
                    String genre = scanner.nextLine();
                    System.out.print("Введите Цену книги: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Введите колличество страниц книги: ");
                    int pages = Integer.parseInt(scanner.nextLine());
                    Author newAuthor  = new Author(authorr);
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
                    System.out.println("Введите полное имя автора: ");
                    String authorToFind = scanner.nextLine();
                    var booksByAuthor = databaseLibrary.findBookByAuthor(authorToFind);
                    for (var book : booksByAuthor) {
                        System.out.println(book);
                    }
                    break;
                case 5:
                    System.out.println("Введите ISBN книги для изменения: ");
                    String isbnToUpdate = scanner.nextLine();
                    Book bookToUpdate = databaseLibrary.findBookByIsbn(isbnToUpdate);
                    if(bookToUpdate != null) {
                        System.out.print("Введите новое название книги: ");
                        String newTitle = scanner.nextLine();
                        //System.out.print("Введите новое полное имя автора: ");
                        //author = scanner.nextLine();
                        //newAuthor.setName(scanner.nextLine());

                        bookToUpdate.setTitle(newTitle);
                        //bookToUpdate.setAuthor(newAuthor);
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
                case 6:
                    System.out.printf("Всего найдено %d книг в библиотеке\n", databaseLibrary.listBooks().size());
                    for(var book : databaseLibrary.listBooks()) {
                        System.out.println(book);
                    }
                    break;
                case 7:
                    System.out.print("Введите название базы данных для удаления: ");
                    //String dbName = scanner.nextLine();
                    DatabaseManager.dropDatabase(scanner.nextLine());
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректная команда. Повторите ввод");
            }
        }
        scanner.close();
    }
}