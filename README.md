Leírás

Adott egy weboldal ahol kapcsolattartókat tudunk managelni.
Minen Kapcsolattartó az alábbi adatokkal rendelkezik : Név, email, telefonszám, note, cég,létrehozás dátuma, utolsó módosítás és státusz.
A cég maga is egy entitás ( adatbázis rekord) a kapcsolattartóhoz hasolnóan egy adatbázisba lementve.
A weboldalon a kliensnek lehetősége van részletes lekérést kérni egy adott
kapcsolattartóhoz, új kapcsolattartót regisztálni illetve módosítani e mellett töröltre állítani státuszát.

A backend API-ezen interakciókat hivatott megvalósítani az alábbi endpointokon:

/contacts/{pageNumber}

- Kilistázza az adott oldalhoz tartozó contacts-kat (ABC sorrendben, egyszerre max. 10) és megjeleníti adatait: teljes név, email, telefon,cég .(GET kérést fogad)
a megvalósító metódus egy oldalszám paramétert({pagenuber}) vár ami alapján a listából kilistáz legfeljebb 10 kapcsolattartót a következő adatokkal
(Teljes név, cég név ,telefonszám és email)
(JSON választ kapunk )

/contacts/get/{id}

-Lekér és megjelenít egy contact-ot minden hozzá tartozó, megjeleníthető adattal.
Itt a metódus egy kapcsolattartóhoz tartozó ID-paramétert vár, nem létező id paraméter esetén hibaüzenettel tér vissza (GET kérést fogad). (JSON válaszban)

/contacts/create
 -Létrehoz egy új contact-ot és lementi adatbázisba ha megfelel a validációs feltételeknek ( body-ban vár egy JSON objektumot ),
a következő adatok kötelezőek :

FirstName
Lastname
Company(létező)
Email
Status

Siker esetén 200-as kóddal tér vissza, ha hibás adatot ad meg a felhasználó  vagy nem ad meg kötelező elemeket akkor hibakódot küld vissza. (POST kérésekre válaszol)

/contacts/update/{id} - már meglévő contact-hoz tartozó adatokat tudunk frissíteni
A metódus egy kapcsolattartüó entitást vár amit JSON-objektumban kap meg illetve egy ID-t ami a módosítandó entitást jelöli ,majd átállítja adatait.
A metódus itt is validálja a telefonszámot, illetve a felhasználó töröltről aktívra állíthatja státsuzát. a Módosítás dátuma új , aktuális értéket kap.
Minden , a mentésénél is megadható paramétert tudunk módosítani az adott kapcsolattartóhoz( Put-kéréseket elégít ki)

/contacts/delete/{id} -egy meglévő contact-hoz tartozó státusz állíthatjuk DELETED-re
a metódus paraméterként egy ID-t vár az URL-ből ( DELETE kéréseket elégít ki)