# Progetto PMO Labirint Legends

> Repository per il Progetto di Programmazione e Modellazione ad Oggetti.<br>
> Componenti:<br>
> Giuseppe Benedetti, 322898<br>
> Annarosa Clemente, matr. 315180<br>
> Elia Renzoni, matr. 319978<br>
> Eloi Ricci, matr. 324204<br>

## Analisi del Problema :
Specifica del Problema:<br>
<br>
L'applicazione da sviluppare è un gioco singolo utente dove quest'ultimo deve superare delle prove nel minor tempo possibile.<br>
Non vi sono limitazioni alla durata della partita, quindi l'utente potrà giocare per quanto tempo vuole, tuttavia il tempo e il numero di monete raccolte saranno le principali discriminanti nel calcolo del ranking.<br>
La prova in questione consiste nel percorrere un labirinto, con degli ostacoli, raccogliendo il maggior numero possibile di monetine. <br>
<br>
Gli ostacoli saranno i seguenti : 
<br>

| Tipo di Ostacolo  | Effetto |
|-------------------|-----------------|
| Buccia di Banana  | Il personaggio torna indietro di un certo numero di posizioni <sup>1</sup>   |
| Bomba             | Se colpita, toglie un determinato numero di monete raccolte<sup>2</sup> | 
| NPC nemico        | Se incontrato, uccide istantaneamente il personaggio e fa perdere il gioco|

<sup>1</sup> 5
<br>
<sup>2</sup> 4
<br>

```
- L'utente deve evitare gli ostacoli scegliendo i percorsi migliori. 
```
Vi sono anche i seguenti aiuti : <br>

|     Tipo di Aiuto    | Effetto |
|----------------------|---------|
| Cassa                | Se colpita, al personaggio viene aggiunta una determinata somma di monete al |
| Incantesimo immunitá | Rende immune agli ostacoli per un determinato numero di passi<sup>1</sup> | 
| Premio Monete        | Se l'utente raggiunge un determinato numero di monete gli viene sottratto del tempo a quello finale<sup>2</sup> |

<sup>1</sup> 5
<br>
<sup>2</sup> 3
<br>
<sup>3</sup> 3/4 delle monete generate

### L'utente puo' selezionare uno fra i due diversi personaggi seguenti:

| Personaggio | Caratteristiche |
|-------------|-----------------|
| Fonzie | abilità notevole nella spada tanto da riuscire a sopravvivere contro un NPC|
| Linda | abilità di correre velocissima, sembra che si sposti di due caselle |

## Funzionalitá :  
```diff
+ Predisposizione di una mappa casuale
+ Posizionamento degli ostacoli in modo casuale
+ Posizionamento degli aiuti in modo casuale
+ Consentire il movimento all'utente
+ Contare lo scorrere del tempo
+ Creazione dei personaggi
+ Creazione degli elementi della mappa
+ Creazione e gestione risultati di ogni giocatore a fine partita
+ Gestione di tutti gli elementi della mappa
```
## Challenge Principali :
```diff
+ Associare gli ostacoli e gli aiuti a dei numeri da inserire nella matrice 
+ Implementazione dell'algoritmo per la creazione e modifica di quest'ultima real time
+ Implementare la logica di movimento dell'utente 
+ Implementare l'interfaccia grafica  
+ Implementare il Controller 
+ Implementare il ranking dei risultati 
+ Fare in modo che l'incantesimo immunità possa oltrepassare gli ostacoli
+ Fare in modo che l'ostacolo banana possa fa indietreggiare il presonaggio
```
