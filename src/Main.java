import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nОнлайн Библиотека\n");
            System.out.println("Меню: ");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Удалить книгу");
            System.out.println("3. Найти книгу по ISBN");
            System.out.println("4. Найти книгу по автору");
            System.out.println("5. Изменить книгу");
            System.out.println("6. Показать все книги");
            System.out.println("7. Выход");

            System.out.println("Выберите пункт меню: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    System.out.print("Введите ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Введите название книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите полное имя автора: ");
                    String author = scanner.nextLine();

                    Book newBook = new Book(title,  author,isbn);
                    if(library.addBook(newBook)) {
                        System.out.println("Книга успешно добавлена");
                    }
                    else {
                        System.out.println("Книга с указанным ISBN уже существует");
                    }
                    break;
                case 2:
                    System.out.println("Введите ISBN книги для удаления: ");
                    String isbnToRemove = scanner.next();
                    Book bookToRemove = library.findBookByIsbn(isbnToRemove);
                    if(bookToRemove != null && library.removeBook(bookToRemove)) {
                        System.out.println("Книга успешно удалена");
                    }
                    else {
                        System.out.println("Книга не найдена");
                    }
                    break;
                case 3:
                    System.out.println("Введите ISBN книги для поиска: ");
                    String isbnToFind = scanner.next();
                    Book bookToFind = library.findBookByIsbn(isbnToFind);
                    System.out.println(bookToFind != null ? bookToFind : "Книга не найдена");
                    break;
                case 4:
                    System.out.println("Введите полное имя автора: ");
                    String authorToFind = scanner.nextLine();
                    var booksByAuthor = library.findBookByAuthor(authorToFind);
                    for (var book : booksByAuthor) {
                        System.out.println(book);
                    }
                    break;
                case 5:
                    System.out.println("Введите ISBN книги для изменения: ");
                    String isbnToUpdate = scanner.nextLine();
                    Book bookToUpdate = library.findBookByIsbn(isbnToUpdate);
                    if(bookToUpdate != null) {
                        System.out.print("Введите новое название книги: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Введите новое полное имя автора: ");
                        String newAuthor = scanner.nextLine();
                        bookToUpdate.setTitle(newTitle);
                        bookToUpdate.setAuthor(newAuthor);
                        System.out.println("Книга успешно изменена");
                    }
                    else {
                        System.out.println("Книга не найдена");
                    }
                    break;
                case 6:
                    System.out.printf("Всего найдено %d книг в библиотеке\n", library.listBooks().size());
                    for(var book : library.listBooks()) {
                        System.out.println(book);
                    }
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректная команда. Повторите ввод");
            }
        }
        scanner.close();
    }
}