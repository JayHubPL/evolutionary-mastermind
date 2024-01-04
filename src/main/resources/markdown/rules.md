# Zasady gry Mastermind
## Cel gry
Zadaniem gracza jest odgadnięcie tajnego hasła będącego ciągiem kolorów. Długość hasła oraz liczba dostępnych kolorów
zależą od wariantu gry:
            
| Wariant | Liczba kolorów | Długość hasła |
|:-------:|:--------------:|:-------------:|
| Classic |       6        |       4       |
| Deluxe  |       8        |       5       |

## Jak grać?
Aby rozpocząć grę należy wybrać wariant gry oraz zaznaczyć, czy powtórzenia kolorów w haśle są dozwolone.
Gracz wprowadza sekwencję kolorów używając górnego panelu.
Używając przycisków myszki można zmieniać kolor na wskazanej pozycji (do przodu i do tyłu).
Gdy gracz wprowadzi wszystkie kolory należy wybrać opcję **Sprawdź**.
Jeżeli gracz chce zakończyć grę lub zmienić wariant należy wybrać opcję **Zakończ**.
Sekwencja zostanie następnie wyświetlona poniżej wraz z przyznanymi pineskami.
<br>Każda próba odgadnięcia hasła jest oceniania w sposób następujący:
           
 1. Za każdy prawidłowy kolor na prawidłowej pozycji otrzymuje się czarną pineskę.
 2. Za każdy kolor znajdujący się w haśle, który nie jest na prawidłowej pozycji otrzymuje się białą pineskę.
            
**Ważne**: Dla każdego koloru w próbie odgadnięcia hasła może być przyznana maksymalnie jedna pineska, priorytet ma czarna.

Gracz zwycięża gdy odgadnie hasło, czyli otrzyma tyle czarnych pinesek, jaka jest długość hasła.
<br>Gracz przegrywa, jeżeli nie uda mu się zgadnąć hasła w limicie **12 prób**.

## Przykład rundy
Niech ukrytym hasłem będzie następująca sekwencja:

![]($example_secret.png$)

Osoba odgadująca dokonuje próby odgadnięcia hasła proponując następującą sekwencję:

![]($example_guess.png$)

W rezultacie otrzymuje w ramach oceny po jednej czarnej i białej pinesce.
<br>**Czarna** pineska została przyznana za pomarańczowy kolor na czwartej pozycji. Żaden inny kolor nie zgadza się z hasłem.
<br>**Biała** pineska została przyznana za kolor fioletowy na pozycji trzeciej, ponieważ znajduje się on w haśle, ale na innej pozycji - drugiej.
<br>Nie otrzymuje się białej pineski za kolor pomarańczowy na pozycji drugiej, ponieważ za tę pozycję w haśle już została przyznana czarna pineska.