# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Iordache Tiberiu-Mihai, grupa 322CD

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

[Puteti sa folositi si alte biblioteci si sa editati aceasta sectiune]

## Implementare

### Entitati

Pentru partea de IO am creat clase care se mapeaza 1:1 la cele din fisierele json de input/output,
iar pentru incarcarea datelor din json in clase(si viceversa) am creat clasa Loader.

Fiecare entitate(Consumer, Distributor, Producer) are o clasa proprie mostenita din clasa
de baza Entity(pentru a implementa cu usurinta Factory).

Pentru partea de Observer, avem clasa Subject(mai multe detalii mai jos), folosita pentru 
a notifica observatorii(distributorii) atunci cand apare o modificare asupra unui producator, iar 
pentru Strategy avem 3 clase separate pentru fiecare strategie in parte atunci cand un distributor 
isi alege producatorii.

Toate aceste clase interactioneaza prin intermediul clasei Simulator, care contine functiile 
necesare rularii mecanismului.

### Flow

Dupa cum am spus mai sus, flow-ul proiectului este controlat de clasa Simulator.

In fiecare runda se vor actualiza valorile pentru distributorii/producatori sau se vor adauga
consumatori noi in functie de inputul primit, dupa care fiecare distributor isi va calcula pretul
contractului, pentru ca in continuare consumatorii sa isi aleaga contractul(daca e nevoie) si sa 
il plateasca. Odata ce contractele au fost platite de consumatori, distributorii isi vor platii 
costurile proprii. Dupa aceea se vor actualiza valorile pentru producatori (daca e cazul) si se va
salva numarul de clienti pe care i-a avut fiecare producator in acea luna.

### Elemente de design OOP

Am folosit agregarea pentru fiecare entitate:
- Fiecare consumer are un contract
- Fiecare distributor are o lista de producatori, dar si o strategie
- Fiecare producator are o lista de clienti(distributori), dar si o lista de monthlyStats

Legat de incapsulare, fiecare clasa are campurile de tip private si pot fi accesate doar cu getteri
si setteri.

Pentru partea de strategy am creat clasa abstracta EnergyChoiceStrategy care contine si metoda 
neabstracta searchNewProducers deoarece fiecare strategie care va extine aceasta clasa va avea
nevoie de aceeasi implementare pentru aceasta functie, singura diferenta dintre ele regasindu-se
in cadrul metodei chooseProducer. Metoda searchNewProducers este deasemena protected pentru a pastra
incapsularea.

### Design patterns

####Factory & Singleton  
 Clasa FactorySingleton se ocupa de instantierea entitatilor, pe baza tipului cerut. Odata cu 
introducerea producatorilor la aceasta etapa, numarul de campuri total necesar pentru constructia
fiecarei clase in parte a crescut, asa ca am decis sa fac in asa fel incat metoda createEntity sa 
primeasca un numar variabil de argumente si in functie de tipul cerut(consumer, distributor,
producer), se vor transmite mai multe sau mai putine, oferind astfel un cod mai usor de citit.
    De asemenea, cum nu avem nevoie de mai multe Factory-uri, aceasta va implementa si design-ul
de singleton pentru a se putea folosii o singura instanta de FactorySingleton.

####Strategy  
 Pentru ca fiecare distributor sa isi aleaga producatorii, acesta are acces la un obiect de tipul
EnergyChoiceStrategy ce a fost implementat ca o clasa abstracta ce ofera metoda chooseProducer 
pentru a putea fi implementa in mai multe moduri, in functie de necesitatea fiecarui distributor.
Din aceasta clasa am extins si clase GreenStrategy, PriceStrategy si QuantityStrategy ce contin 
implementarea fiecarui tip de strategie necesara simularii. Astfel, atunci cand un distributor
va cauta un producator, acestia vor fi sortati dupa tipul energiei/pret/cantitate/id in functie de
tipul de strategie si se vor alege atatia cati e nevoie pentru a furniza destula energie 
distributorului.

####Observer  
 Am abordat varianta in care observatorii sunt distributori, iar observable este clasa subiect, 
care are o lista de producatori. Astfel, atunci cand se vor actualiza valorile producatorilor,
se va creea o lista cu toti distributorii care trebuie notificati, iar acestia vor fi actualizati
in ordinea id-ului. Interfetele pentru Observer si Observable sunt custom si contin doar metodele 
necesare acestei simularii(nu am mai adaugat metode de add/remove observator in cadrul interfetei 
 observable pentru ca se vor adauga de la inceput toti producatorii).


###Dificultati intalnite, limitari, probleme

 Am intampinat probleme in cadrul implementarii strategiilor, deoarece sortam aceeasi lista de 
producatori, insa iteram prin ea in cadrul clasei Simulator. Am rezolvat prin folosirea streams
astfel incat sa obtin o lista noua cu producatorii sortati. Doar in cazul clasei GreenStrategy am
optat sa folosesc collections.sort si un arraylist nou pentru ca astfel puteam sa sortez
 producatorii dupa tipul de energie furnziat fara a implementa metoda compareTo in cadrul clasei
Producer.  

De asemenea, am ales sa folosesc aceasta implementare de observer datorita limitarii impuse de 
enuntul temei, distributorii care trebuie sa isi reaplice strategia sunt luati in ordine crescatore
a id-ului.
Fara aceasta restrictie, as fi mers pe varianta in care producatorii ar fi fost Observable, iar 
Distributorii observers, iar atunci cand un distributor isi modifica energia, se notificau imediat
toti clientii acestuia.

###Feedback

Overall tema mi-a placut foarte mult, am realizat cat de mult conteaza modularizarea codului, dar 
si scrierea acestuia astfel incat sa fie cat mai usor de actualizat/adaugat functionalitati noi pe
parcurs.  
Singurul lucru care nu mi-a placut este faptul ca testele complexe erau greu de urmarit si devenea
greu sa iti dai seama unde gresesti, dar era de asteptat la cate date de input aveau.


