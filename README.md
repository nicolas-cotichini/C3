![C3](C3-Centro_Commerciale_in_Centro.png)

Il progetto si rivolge ai centri abitati medi della provincia italiana dove le attività commerciali del centro soffrono la concorrenza di grossi centri commerciali situati nelle periferie.L’idea di fondo è considerare il trasporto della merce una volta acquistata una delle scomodità principali degli acquisti in centro oltre alla più difficile collocazione dei punti vendita in relazione a specifiche categorie merceologiche.
Il progetto si pone dunque come obiettivo quello di fornire un supporto per rendere l’esperienza degli acquisti in centro più facile e interessante.

## Progetto di Ingegneria del Software

---

## Iterazione 1

*__Identificazione attori primari__: Amministratore, Cliente, Commerciante, Corriere.

*__Identificazione casi d'uso e descrizione__:

  *Amministratore*  
    Gestione Personale  
    Gestione Luogo  
    Visualizza Problema  
    Rimozione Problema

  *Cliente*  
    Registrazione  
    Aggiunta Domicilio  
    Ricerca Merce  
    Ritiro Acquisto
    Seleziona Luogo Ritiro  
    Genera Ordine  

  *Commerciante*
    Registrazione Negozio
    Preparazione Acquisto
    Inserimento Dati Acquisto
    Gestione Merce 

  *Corriere*
    Ritiro Ordine
    Consegna Ordine

  *Condivisa*
    Segnalazione Problema

*__Specifica dei casi d'uso principali trovati__.
*__Stesura diagramma classi di analisi__.
*__Realizzazione diagrammi di sequenza di__:

   Consega Ordine
   Genera Ordine
   Gestione Merce
   Seleziona Luogo Ritiro

*___Realizzazione prima bozza del diagramma classi di progetto__.
*__Prima implementazione del codice__.

---

## Iterazione 2 

*__Identificazione nuovi attori__: Gestore Logistica, Magazziniere.

*__Rielaborazione casi d'uso precedenti__.
*__Rimozione casi d'uso non rilevanti__.
*__Identificazione nuovi casi d'uso__:

  *Cliente
    Controllo Stato Ordine

  *Corriere
    Inserimento Ordine nel Locker

  *Gestore Logistica
    Seleziona Corriere

  *Magazziniere
    Ritiro Ordine per Magazzino
    Consegna Ordine al Cliente

*__Classificazione casi d'uso per priorità, complessità e rilevanza__.
*__Revisione diagramma classi di analisi__.
*__Rielaborazione diagrammi di sequenza precedenti__.
*__Realizzazione nuovi diagrammi di sequenza__:

   Ritiro Ordine
   Seleziona Corriere

*__Diagramma classi di progetto approfondito ed aggiornato__.
*__Realizzazione prima bozza del modello di persistenza__.
*__Sviluppo codice__.

---

## Iterazione 3

*__Identificazione nuovo attore__: User.  
*__Rielaborazione casi d'uso precedenti e conclusione di specifiche e dettagli__.
*__Identificazione ultimi casi d'uso__:

  *User
    Login
    Registrazione come Cliente

  *Corriere
    Cambia stato Operativo

  *Condivise
    Controllo Ordine
    Visualizza dati Profilo

*__Rielaborazione diagrammi di sequenza precedenti__.
*__Realizzazione nuovi diagrammi di sequenza__:

   Crea Luogo
   Inserimento Ordine nel Locker
   Ritiro Acquisti
   Gestione Luogo

*__Revisione diagramma classi di progetto__
*__Completamento modello di persistenza__
*__Implementazione finale del codice__
*__Testing__

[C3-Frontend](https://github.com/nicolas-cotichini/C3-PWA)
---
