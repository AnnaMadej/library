# Aplikacja umożliwiająca przechowywanie informacji na temat kolekcji książek.
## Autor: Anna Madej

omówienie projektu: https://www.youtube.com/watch?v=twlBkOLpeKo

### Funkcjonalności dostępne przez stronę www:
#### Dostępne dla wszystkich:
* wyświetlanie listy książek z użyciem paginacji:
    * wszystkie ksiązki
    * książki z danej kategorii
    * książki wyszukane według ciągu znaków znajdującego się w polu autor lub tytuł
    
* rejestracja użytkownika - dane użytkownika zapisywane są do bazy __MYSQL__ z hasłem zakodowanym przy użyciu __BCrypt__.
- logowanie użytkownika - integracja ze __Spring Security__

#### Dostępne tylko dla zalogowanych użytkowników (__Spring Security__):
- zmiana danych użytkownika
- zmiana hasła uzytkownika - hasło zakodowane przy użyciu __BCrypt__
- dodawanie książki - dane książki zapisywane są w bazie __MYSQL__
- edycja danych książki


### Funkcjonalności dostępne przez REST API:
* Żądania typu GET:
    * wyświetlanie listy wszystkich kategorii wraz z ilością książek w każdej z nich
        * /api/categories
    * wyświetlanie informacji o konkretnej kategorii wraz z ilością książek
        * /api/category/numerKategorii
    * wyświetlanie informacji o konkretnej kategorii wraz z danymi wszystkich książek
        * /api/category/{numerKategorii}/books
        

* Żądanie typu POST:
    * dodanie nowej kategorii
        * /api/category?categoryName=nazwakategorii
        

* Żądanie typu PUT:
    * zmiana nazwy kategorii
	    * /api/category/{numerKategorii}?categoryName=nowaNazwakategorii
	    

* Żądania typu DELETE:
    * usunięcie pustej kategorii
        * /api/category/categoryId
    * usunięcie niepustej kategorii z przeniesieniem jej zawartości do innej kategorii (całkiem nowej lub istniejącej)
        * /api/category/categoryId?newCategoryName=nowaNazwaKategorii
