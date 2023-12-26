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

## Przykład
![]($example.jpg$) 