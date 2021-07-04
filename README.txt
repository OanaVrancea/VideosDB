Vrancea Oana Roxana, 322 CD 

Pentru aceasta tema principala implementare este continuta de pachetul "action".
Ideea mea a fost sa construiesc clasa Action in care am pus in principal
funtiile de eliminare ale duplicatelor si functiile de sortare pentru 
diverse taskuri, deoarece au fost folosite in implementarea mai multor functii.
Am extins clasa Action in 3 clase: Command, Query si Recommendation in 
care sunt implementate functiile pentru fiecare actiune in parte. Voi detalia
continutul celor 3 clase:

1.Clasa Command contine functiile favorite, view, ratingMovie si ratingSerial.
Explicatii pentru aceste taskuri sunt detaliate sub forma de comentarii
in fisier.

2.Clasa Query contine urmatoarele functii: average, awards, filterDescription,
videoRatingMovie, videoRatingSerial, favoriteMovie, favoriteSerial, longestMovie,
longestSerial, mostViewedMovies, mostViewedSerials, numberRatings. Functionalitatea
acestora este explicata sub forma de comentarii. Acestea apeleaza functiile de 
sortare din clasa parinte Action.

3.Clasa Recommendation contine functiile standard, bestUnseen, popular, favRec 
si search, dar si functiile auxiliare returnPopularArray si 
standardConclusion. Se apeleaza functiile de sortare din Action.

Pachetul "action" contine si clasa BubblesortMethods care contine
implementarile bubblesort de sortare a unui vector pe care le-am folosit
pentru sortari.

In pachetul "actor", in clasa actor am implementat urmatoarele functii:
1.actorMovies care imi intoarce toate filmele in care a jucat un actor
2.actorSeials care imi intoarce lista de seriale in care a jucat un actor
3.actorFilmographyAverage care imi calculeaza media pentru acel actor,
tinand cont de toate filmele si serialele in care a jucat si au primit
un rating.
Aceste functii au fost folosite in pachetul "action".


In pachetul "entertainment" am adaugat functionalitati urmatoarelor clase:
1.Clasa Movie. Aici am adaugat:
-o lista de rating pentru a retine ratingurile date unui film
-functia addRating care imi adauga un rating in lista
-getAverageRatingMovie care imi calculeaza media ratingurilor primite

2.Clasa Serial.Aici am adaugat:
-functia getAverageRatingSerial care imi calculeaza media pentru un serial
-funtia getSerialDuration care imi calculeaza durata unui serial

3.Clasa Season.Aici am adugat:
-o lista de ratings care retine ratingurile date pentru sezonul respectiv
-functia addRating care imi adauga un rating la lista curenta
-functia getAverageRatingSeason care calculeaza media pentru sezonul respectiv

In pachetul "User" am adaugat:
-o structura de tip Map<String,Integer> ratingMovie care imi retine
filmele carora utilizatorul le-a atribuit un rating.
-o structura de tip Map<String,Integer> ratingSerial care imi retine
sezonul din serial caruia utilizatorul i-a atribuit un rating.
-functia addRatedMovie ce adauga titlul unui film ca si cheie,
iar ca valoare "1" pentru a marca faptul ca a fost vazut.
-funtia addRatedSerial care adauga ca si cheie serialul,
iar ca valoare numarul sezonului caruia i-a dat un rating.

Toate acestea au fost folosite in implementarile din pachetul
"action".

In main am construit liste pentru useri, filme, seriale si
actori. Dupa care am parcurs toate comezile, am verificat
de ce tip sunt, am retinut rezultatul actiunii in "message",
dupa care la final am construit un obiect si l-am adaugat in
arrayResult.





